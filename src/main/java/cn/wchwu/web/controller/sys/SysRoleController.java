package cn.wchwu.web.controller.sys;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysRole;
import cn.wchwu.service.sys.SysRoleService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 角色管理
 * 
 * @author orh
 *
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            一页条数
	 * @return
	 */
	@RequestMapping("view")
	@ResponseBody
	public JSONObject view(Integer page, Integer rows, String roleCode,
			String roleName) {
		PageCond pageCond = new PageCond(page, rows);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", roleCode);
		map.put("roleName", roleName);

		List<SysRole> list = sysRoleService.queryRole(pageCond, map);

		JSONObject rsJson = new JSONObject();
		rsJson.put("rows", list);
		rsJson.put("total", pageCond.getTotalRows());
		return rsJson;
	}

	/**
	 * 批量保存 角色
	 * 
	 * @param deleted
	 *            要删除的对象
	 * @param inserted
	 *            要新增的集合
	 * @param updated
	 *            要更新的集合
	 * @return
	 */
	@RequestMapping(value = "saveBatch")
	@ResponseBody
	public JSONObject saveBatch(String deleted, String inserted, String updated) {
		List<SysRole> deletedList = null;
		List<SysRole> insertedList = null;
		List<SysRole> updatedList = null;
		if (deleted != null) {
			deletedList = JSON.parseArray(deleted, SysRole.class);
		}
		if (inserted != null) {
			insertedList = JSON.parseArray(inserted, SysRole.class);
		}
		if (updated != null) {
			updatedList = JSON.parseArray(updated, SysRole.class);
		}

		sysRoleService.saveRoleBatch(deletedList, insertedList, updatedList);

		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 新增 角色-人员关系
	 * 
	 * @return
	 */
	@RequestMapping("insertRoleOperator")
	@ResponseBody
	public JSONObject insertRoleOperator(
			@RequestParam(required = true) String roleCode,
			@RequestParam(required = true) int[] ids) {

		sysRoleService.insertRoleOperator(roleCode, ids);

		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 新增 角色-人员关系
	 * 
	 * @return
	 */
	@RequestMapping("deleteRoleOperator")
	@ResponseBody
	public JSONObject deleteRoleOperator(
			@RequestParam(required = true) String roleCode,
			@RequestParam(required = true) int[] ids) {

		sysRoleService.deleteRoleOperator(roleCode, ids);

		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}
}
