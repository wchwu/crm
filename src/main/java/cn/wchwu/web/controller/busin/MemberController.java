package cn.wchwu.web.controller.busin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.busin.MemberPo;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.service.busin.MemberService;
import cn.wchwu.service.sys.SysDictService;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:36:48
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SysDictService sysDictService;

    // 业务字典翻译
    static FillDictEntityModel genderNameMapping = new FillDictEntityModel("SYS_GENDER", "gender", "genderName");
    static FillDictEntityModel genderMapping = new FillDictEntityModel("SYS_GENDER", "gender", "genderName");

    @RequestMapping("memberList")
    @ResponseBody
    public JSONObject memberList(Integer page, Integer rows, @FormModel("member") MemberPo member) {

        // 构建查询参数map
        Map<String, Object> map = new HashMap<String, Object>();
        // MemberPo record = new MemberPo();
        // map.put("likeLoginName", operator.getLoginName());
        // map.put("realName", operator.getRealName());
        // map.put("email", operator.getEmail());
        // map.put("status", operator.getStatus());
        // map.put("roleCode", roleCode);
        // map.put("notInRoleCode", notInRoleCode);

        // 构建分页对象
        PageCond pageCond = new PageCond(page, rows);

        // 执行查询
        List<MemberPo> list = memberService.queryList(pageCond, member);

        // 转换成jsonArr,再根据业务字典翻译字段 genderName
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
        // FillDictEntityModel[] mappings = { genderMapping, statusMapping,
        // authModeMapping };
        FillDictEntityModel[] mappings = { genderMapping };

        // 构建返回结果
        JSONObject rsJson = new JSONObject();
        rsJson.put("rows", sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
        rsJson.put("total", pageCond.getTotalRows());

        return rsJson;
    }

}
