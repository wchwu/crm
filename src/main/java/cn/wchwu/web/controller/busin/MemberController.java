package cn.wchwu.web.controller.busin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.busin.MemberPo;
import cn.wchwu.model.busin.vo.MemberVo;
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
    static FillDictEntityModel genderMapping = new FillDictEntityModel("SYS_GENDER", "gender", "genderName");
    static FillDictEntityModel nationMapping = new FillDictEntityModel("NATION", "nation", "nationName");
    static FillDictEntityModel marriageMapping = new FillDictEntityModel("MARRIAGE", "marriage", "marriageName");
    static FillDictEntityModel polityMapping = new FillDictEntityModel("POLITY", "polity", "polityName");
    static FillDictEntityModel statusMapping = new FillDictEntityModel("STATUS", "status", "statusName");
    static FillDictEntityModel deptMapping = new FillDictEntityModel("DEPT", "dept", "deptName");

    @RequestMapping("memberList")
    @ResponseBody
    public JSONObject memberList(Integer page, Integer rows, @FormModel("member") MemberPo member) {
        // 构建分页对象
        PageCond pageCond = new PageCond(page, rows);
        System.out.println("查询参数：" + JSONObject.toJSONString(member));
        // 执行查询
        List<MemberPo> list = memberService.queryList(pageCond, member);

        // 转换成jsonArr,再根据业务字典翻译字段 genderName
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
        FillDictEntityModel[] mappings = { genderMapping, nationMapping, marriageMapping, polityMapping, statusMapping };

        // 构建返回结果
        JSONObject rsJson = new JSONObject();
        System.out.println(JSON.toJSONString(sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings)));
        rsJson.put("rows", sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
        rsJson.put("total", pageCond.getTotalRows());
        return rsJson;
    }

    @RequestMapping("updateMember")
    @ResponseBody
    public JSONObject updateMember(@ModelAttribute("member") MemberPo member) {
        System.out.println("提交参数：" + JSONObject.toJSONString(member));
        JSONObject rsJson = new JSONObject();
        int ret = memberService.update(member);
        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("addMember")
    @ResponseBody
    public JSONObject addMember(@ModelAttribute("member") MemberPo member) {
        System.out.println("提交参数：" + JSONObject.toJSONString(member));
        JSONObject rsJson = new JSONObject();
        int ret = memberService.save(member);
        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("getMemberById")
    @ResponseBody
    public String getMemberById(@RequestParam(value = "id", required = true) Integer id) {
        MemberVo memberVo = memberService.getMemberById(id);
        return JSONObject.toJSONString(memberVo, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("del")
    @ResponseBody
    public JSONObject memberDelete(@RequestParam(value = "ids", required = true) List<Integer> ids) {

        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(ids));

        System.out.println(jsonArr.toJSONString());
        // 构建返回结果
        JSONObject rsJson = new JSONObject();

        return rsJson;
    }

}
