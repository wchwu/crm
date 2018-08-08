package cn.wchwu.web.controller.sys;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.sys.SysMenu;
import cn.wchwu.model.sys.SysOperator;
import cn.wchwu.service.sys.SysMenuService;
import cn.wchwu.web.filter.AuthorityFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 菜单控制器
 * 
 * @author wcw.sh
 * @version 创建时间：2015年5月7日 上午11:18:57
 */
@Controller
@RequestMapping("sys/menu")
public class SysMenuController {
	// ResponseBody
	// //该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
	// 返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用
	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("userTree")
	@ResponseBody
	public JSONArray userTree(String id, String async, String qtype, HttpServletRequest request) {
		return tree(id, async, null, request);
	}

	/**
	 * 加载菜单树
	 * 
	 * @param id
	 *            异步加载时按此id去加载子项，当 async为 `y`时，才使用此字段
	 * @param async
	 *            是否异步加载; 为 'n'时则一次性加载，async为'y'时则异步加载
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping("tree")
	@ResponseBody
	public JSONArray tree(String id, String async, String qtype, HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		if (id != null) {
			async = "y";
		}
		paramMap.put("async", async); // 是否为异步加载
		paramMap.put("pid", id); // 异步加载要用到的pid
		paramMap.put("qtype", qtype); // 查询类型 如all： 查询全部、空值：则查询全部启用的

		if (!"all".equals(qtype)) {
			SysOperator operator = (SysOperator) request.getSession().getAttribute(AuthorityFilter.SESSION_KEY_OPERATOR);
			paramMap.put("operatorId", operator.getId());
		}
		List<SysMenu> rsList = sysMenuService.querySysMenu(paramMap);
		for (SysMenu menu : rsList) {
			if ("n".equals(menu.getIsleaf())) {
				menu.setState("closed");
			}
		}
		JSONArray jsonArr = JSONArray.parseArray(JSON.toJSONString(rsList));
		return jsonArr;
	}

	/**
	 * 根据菜单ID获取菜单信息
	 * 
	 * @param id
	 *            操作员唯一ID
	 * @return
	 */
	@RequestMapping("getById")
	@ResponseBody
	public JSONObject getById(@RequestParam(required = true) String id) {
		SysMenu menu = sysMenuService.getMenuById(id);
		JSONObject rsJson = new JSONObject();
		if (menu != null) {
			rsJson = JSONObject.parseObject(JSONObject.toJSONString(menu));
		}
		return rsJson;
	}

	/**
	 * 新增菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@ResponseBody
	public JSONObject add(@FormModel("menu") SysMenu menu) throws Exception {
		JSONObject rsJson = new JSONObject();

		try {
			sysMenuService.insertSysMenu(menu);
		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001") && e.getMessage().contains("SYS_FUNC_UK1")) {
				rsJson.put("success", "1");
				rsJson.put("errorMsg", "菜单链接地址已存在，系统暂不支持存在重复的链接地址,请修改链接地址。");
			} else {
				throw e;
			}
		}

		// 构建返回结果
		if (rsJson.getString("success") == null) {
			rsJson.put("success", "0");
		}
		return rsJson;
	}

	/**
	 * 删除菜单(级联删除子菜单、菜单对应的功能、子菜单对应的功能)
	 * 
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	public JSONObject del(@RequestParam(value = "id", required = true) String id) {
		// 执行删除
		sysMenuService.deleteMenu(id);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 更新菜单基本信息
	 * 
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public JSONObject update(@FormModel("menu") SysMenu menu) {
		// 执行更新
		sysMenuService.updateSysMenu(menu);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 更新菜单基本信息
	 * 
	 * @return
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public JSONObject updateMenuStatus(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "idSeq", required = true) String idSeq,
			@RequestParam(value = "status", required = true) String status) {
		sysMenuService.updateStatus(id, idSeq, status);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 拖拽菜单
	 * 
	 * @return
	 */
	@RequestMapping("move")
	@ResponseBody
	public JSONObject moveMenu(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "pid", required = true) String pid,
			@RequestParam(value = "toIdSeq", required = true) String toIdSeq, @RequestParam(value = "fromIdSeq", required = true) String fromIdSeq, Integer toMenuLevel,
			Integer fromMenuLevel, Integer rank, Integer chgParent) {
		HashMap<String, Object> paramterMap = new HashMap<String, Object>();
		paramterMap.put("id", id);
		paramterMap.put("pid", pid);
		paramterMap.put("toIdSeq", toIdSeq);
		paramterMap.put("fromIdSeq", fromIdSeq);
		paramterMap.put("toMenuLevel", toMenuLevel);
		paramterMap.put("fromMenuLevel", fromMenuLevel);
		paramterMap.put("rank", rank);
		paramterMap.put("chgParent", chgParent);

		// 移动菜单(移动菜单分两种目的：1. 更改菜单顺序 2. 更改菜单层级)
		sysMenuService.moveSysMenu(paramterMap);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

}
