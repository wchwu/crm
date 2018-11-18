
package cn.wchwu.web.controller.busin;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.busin.RuleRegPo;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.service.busin.RuleService;
import cn.wchwu.service.sys.SysDictService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类的描述：
 * 
 * @ClassName: FileUploadController
 * @author ChaoWu.Wang
 * @date 2016年1月20日 下午6:37:37
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/rule")
public class RuleRegController {
    Logger log = LoggerFactory.getLogger(RuleRegController.class);

    @Autowired
    private RuleService ruleService;

    @Autowired
    private SysDictService sysDictService;

    static FillDictEntityModel ruleTypeMapping = new FillDictEntityModel("RULE_TYPE", "ruleType", "ruleTypeName");

    @RequestMapping("ruleList")
    @ResponseBody
    public JSONObject ruleList(Integer page, Integer rows, @FormModel("rule") RuleRegPo rule) {
        // 构建分页对象
        PageCond pageCond = new PageCond(page, rows);
        System.out.println("查询参数：" + JSONObject.toJSONString(rule));
        // 执行查询
        List<RuleRegPo> list = ruleService.queryList(pageCond, rule);

        // 转换成jsonArr,再根据业务字典翻译字段 genderName
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
        FillDictEntityModel[] mappings = { ruleTypeMapping };

        // 构建返回结果
        JSONObject rsJson = new JSONObject();
        System.out.println(JSON.toJSONString(sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings)));
        rsJson.put("rows", sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
        rsJson.put("total", pageCond.getTotalRows());
        return rsJson;
    }
}
