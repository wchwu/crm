
package cn.wchwu.web.controller.busin;

import cn.wchwu.common.Constant;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.RuleRegPo;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.model.sys.SysOperator;
import cn.wchwu.service.busin.RuleService;
import cn.wchwu.service.sys.SysDictService;
import cn.wchwu.util.StringUtil;
import cn.wchwu.web.filter.AuthorityFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public JSONObject ruleList(Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        // 构建分页对象
        PageCond pageCond = new PageCond(page, rows);
        String ruleType = request.getParameter("ruleType");
        ruleType = StringUtil.trim(ruleType);
        params.put("ruleType", ruleType);
        params.put("ruleName", request.getParameter("ruleName"));
        params.put("creator", request.getParameter("creator"));
        params.put("createTimeBegin", request.getParameter("createTimeBegin"));
        params.put("createTimeEnd", request.getParameter("createTimeEnd"));
        params.put("status", Constant.RULE_STATUS_OK);

        System.out.println("查询参数：" + JSONObject.toJSONString(params));
        // 执行查询
        List<RuleRegPo> list = ruleService.queryList(pageCond, params);

        // 转换成jsonArr,再根据业务字典翻译字段 genderName
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
        FillDictEntityModel[] mappings = { ruleTypeMapping };

        // 构建返回结果
        JSONObject rsJson = new JSONObject();
        rsJson.put("rows", sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
        rsJson.put("total", pageCond.getTotalRows());
        return rsJson;
    }

    @RequestMapping("getRuleId")
    @ResponseBody
    public JSONObject getRuleId() {
        int ruleId = ruleService.getRuleId();
        JSONObject rsJson = new JSONObject();
        rsJson.put("success", "0");
        rsJson.put("ruleId", ruleId);
        return rsJson;
    }

    @RequestMapping("getByRuleId")
    @ResponseBody
    public String getByRuleId(@RequestParam(value = "id", required = true) Integer id) {
        RuleRegPo rule = ruleService.queryById(id);
        return JSONObject.toJSONString(rule, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("savaRule")
    @ResponseBody
    public JSONObject savaRule(@ModelAttribute("rule") RuleRegPo rule, HttpServletRequest request) {
        System.out.println("提交参数：" + JSONObject.toJSONString(rule));

        SysOperator operator = (SysOperator) request.getSession().getAttribute(AuthorityFilter.SESSION_KEY_OPERATOR);

        String op = request.getParameter("op");
        String id = request.getParameter("id");
        JSONObject rsJson = new JSONObject();
        rule.setId(Integer.valueOf(id));
        rule.setStatus(Constant.RULE_STATUS_OK);
        int ret = 0;
        Date d = new Date();
        if ("add".equals(op)) {
            rule.setCreator(operator.getLoginName());
            rule.setCreateTime(d);
            rule.setUpdater(operator.getLoginName());
            rule.setUpdateTime(d);
            ret = ruleService.insertRule(rule);
        } else if ("upt".equals(op)) {
            rule.setUpdater(operator.getLoginName());
            rule.setUpdateTime(d);
            ret = ruleService.updateRule(rule);
        }
        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("delRule")
    @ResponseBody
    public JSONObject delRule(@RequestParam(value = "ids", required = true) List<Integer> ids, HttpServletRequest request) {
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(ids));
        System.out.println(jsonArr.toJSONString());
        JSONObject rsJson = new JSONObject();
        SysOperator operator = (SysOperator) request.getSession().getAttribute(AuthorityFilter.SESSION_KEY_OPERATOR);

        RuleRegPo record = null;
        int ret = 0;
        for (int i = 0; i < ids.size(); i++) {
            record = new RuleRegPo();
            record.setId(ids.get(i));
            record.setUpdater(operator.getLoginName());
            record.setUpdateTime(new Date());
            record.setStatus(Constant.RULE_STATUS_DEL);
            ret += ruleService.updateRule(record);
        }

        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }
}
