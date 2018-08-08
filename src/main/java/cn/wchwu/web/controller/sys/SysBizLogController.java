package cn.wchwu.web.controller.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.model.sys.SysBizLog;
import cn.wchwu.service.sys.SysBizLogService;
import cn.wchwu.service.sys.SysDictService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/sys/bizLog")
public class SysBizLogController {
	
	// 实体与业务字典映射关系
	static FillDictEntityModel logTypeMapping = new FillDictEntityModel(
				"SYS_BIZ_LOG_TYPE", "logType", "logTypeName");

	@Autowired
	private SysBizLogService sysBizLogService;
	
	@Autowired
	private SysDictService sysDictService;

	@RequestMapping("pageView")
	@ResponseBody
	public JSONObject pageView(Integer page, Integer rows,
			@FormModel("bizLog") SysBizLog bizLog, Date beginInsertDate, Date endInsertDate) {

		// 构建查询参数map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("likeOptLoginName", bizLog.getOptLoginName());
		map.put("likeOptRealName", bizLog.getOptRealName());
		map.put("logType", bizLog.getLogType());
		map.put("beginInsertDate", beginInsertDate);
		map.put("endInsertDate", endInsertDate);
		map.put("likeMenuName", bizLog.getMenuName());
		map.put("likeFuncName", bizLog.getFuncName());

		// 构建分页对象
		PageCond pageCond = new PageCond(page, rows);

		// 执行查询
		List<SysBizLog> list = sysBizLogService.querySysBizLog(pageCond, map);

		// 转换成jsonArr,再根据业务字典翻译字段 genderName
		JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
		FillDictEntityModel[] mappings = {logTypeMapping};

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("rows",
				sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
		rsJson.put("total", pageCond.getTotalRows());

		return rsJson;
	}

}
