package cn.wchwu.web.controller.sys;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.model.sys.SysMenu;
import cn.wchwu.service.sys.SysDictService;
import cn.wchwu.service.sys.SysFuncService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/sys/func")
public class SysFuncController {
	
	/**
	 *  业务字典翻译
	 */
	private static FillDictEntityModel statusMapping = new FillDictEntityModel(
			"SYS_YESORNO", "status", "statusName");
	
	private static FillDictEntityModel logStatusMapping = new FillDictEntityModel(
			"SYS_YESORNO", "logStatus", "logStatusName");
	
	private static FillDictEntityModel sysFlagMapping = new FillDictEntityModel(
			"SYS_YESORNO", "sysFlag", "sysFlagName");
	
	@Autowired
	private SysFuncService sysFuncService;
	
	@Autowired
	private SysDictService sysDictService;
	
	/**
	 * 功能列表分页查询
	 * @param page 页码
	 * @param rows 一页行数
	 * @param menuId 菜单ID
	 * @return
	 */
	@RequestMapping("pageView")
	@ResponseBody
	public JSONObject pageView(Integer page, Integer rows, @FormModel("func") SysFunc func) {
		PageCond pageCond = new PageCond(page, rows); 
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("menuId", func.getMenuId());
		map.put("likeFuncName", func.getFuncName());
		map.put("status", func.getStatus());
		map.put("sysFlag", func.getSysFlag());
		map.put("likeFuncUrlPath", func.getFuncUrlPath());
		
		
		//查询数据库
		List<SysFunc> list = sysFuncService.queryFunc(pageCond, map);
		//转为json Arr
		JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
		//定义业务字典翻译
		FillDictEntityModel[] mappings = {statusMapping, logStatusMapping, sysFlagMapping};
		
		JSONObject rsJson = new JSONObject();
		//调用业务字典方法进行翻译
		rsJson.put("rows", sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
		rsJson.put("total", pageCond.getTotalRows());
		return rsJson;
	}
	
	/**
	 * 角色管理-------根据角色查询菜单-功能树
	 * @param roleCode 角色代码
	 * @return
	 */
	@RequestMapping("treeView")
	@ResponseBody
	public JSONArray treeView(@RequestParam(required=true) String roleCode) {
		//查询所有功能-树
		List<SysMenu> rsList = sysFuncService.queryMenuFuncTree();
		//查询当前角色含有的功能
		List<String> funcIdList = sysFuncService.queryFuncIdByRoleCode(roleCode);
		
		//根据当前角色的功能，设置树节点的选择状态
		for(SysMenu m : rsList) {
			if (funcIdList.contains(m.getId())) {
				m.setChecked(true);
			} else {
				m.setChecked(false);
			}
		}
		JSONArray jsonArr = JSON.parseArray(JSON.toJSONString(rsList));
		return jsonArr;
	}
	
	/**
	 * 角色管理-----------保存角色功能关系
	 * @return
	 */
	@RequestMapping("saveFuncTree")
	@ResponseBody
	public JSONObject saveFuncTree(@RequestParam(value="ids", required = true) List<String> ids,
			@RequestParam(value="roleCode", required = true) String roleCode) {
		sysFuncService.saveRoleFunc(ids, roleCode);
		
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}
	
	/**
	 * 根据功能ID获取功能员信息
	 * 
	 * @param id
	 *            功能唯一ID
	 * @return
	 */
	@RequestMapping("getById")
	@ResponseBody
	public JSONObject getById(@RequestParam(required = true) String id) {
		// 按id查询
		SysFunc func = sysFuncService.getById(id);

		JSONObject rsJson = new JSONObject();
		if (func != null) {
			rsJson = JSONObject
					.parseObject(JSONObject.toJSONString(func));
		}
		// 构建返回结果
		return rsJson;
	}
	
	/**
	 * 新增系统功能
	 * 
	 * @return
	 */
	@RequestMapping("addFunc")
	@ResponseBody
	public JSONObject addFunc(@FormModel("func") SysFunc func) {
		// 执行插入
		sysFuncService.insertFunc(func);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	
	/**
	 * 删除系统功能
	 * 
	 * @return
	 */
	@RequestMapping("delFunc")
	@ResponseBody
	public JSONObject delFunc(
			@RequestParam(value = "ids", required = true) List<String> ids) {
		// 执行删除
		sysFuncService.deleteFuncBatch(ids);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}
	
	/**
	 * 更新系统功能
	 * 
	 * @return
	 */
	@RequestMapping("updateFunc")
	@ResponseBody
	public JSONObject updateFunc(@FormModel("func") SysFunc func) {
		// 执行更新
		sysFuncService.updateFunc(func);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

}
