package cn.wchwu.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.common.DBUtil;
import cn.wchwu.dao.sys.SysRoleMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysRole;


@Service("sysRoleService")
public class SysRoleService {

	@Autowired
	private DBUtil<SysRole> dbUtil;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	/**
	 * 分页查询 角色
	 * @param page
	 * @param parameter
	 * @return
	 */
	public List<SysRole> queryRole(PageCond page, Object parameter) {
		return sysRoleMapper.queryRole("default", page, parameter);
	}
	
	/**
	 * 单个新增角色
	 */
	public void insertRole(SysRole role) {
		dbUtil.insert("default", "cn.wchwu.dao.sys.SysRoleMapper.insertRole", role);
	}
	
	/**
	 * 批量新增
	 */
	public void insertRoleBatch(List<SysRole> list) {
		if (list != null) {
			for (SysRole d : list) {
				insertRole(d);
			}
		}
	}
	
	/**
	 * 单个更新
	 */
	public void updateRole(SysRole role) {
		dbUtil.update("default", "cn.wchwu.dao.sys.SysRoleMapper.updateRole", role);
	}

	/**
	 * 批量更新
	 */
	public void updateRoleBatch(List<SysRole> list) {
		if (list != null) {
			for (SysRole d : list) {
				updateRole(d);
			}
		}
	}
	
	/**
	 * 单个删除
	 * 
	 * @param parameter
	 */
	public void deleteRole(SysRole role) {
		dbUtil.delete("default", "cn.wchwu.dao.sys.SysRoleMapper.deleteRole", role);
	}

	/**
	 * 单个删除
	 * 
	 * @param list
	 */
	public void deleteRoleBatch(List<SysRole> list) {
		if (list != null) {
			for (SysRole d : list) {
				deleteRole(d);
			}
		}
	}
	
	/**
	 * 批量修改角色
	 * @param deletedList
	 *            要删除 的集合
	 * @param insertedList
	 *            要插入的集合
	 * @param updatedList
	 *            要更新的集合
	 */
	public void saveRoleBatch(List<SysRole> deletedList,
			List<SysRole> insertedList, List<SysRole> updatedList) {
		insertRoleBatch(insertedList);
		updateRoleBatch(updatedList);
		deleteRoleBatch(deletedList);
	}

	/**
	 * 新增 角色人员关系数据
	 * @param roleCode
	 * @param ids
	 */
	public void insertRoleOperator(String roleCode, int[] ids) {
		if (ids != null) {
			//参数赋值
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleCode", roleCode);
			
			//遍历赋值变参，循环执行删除
			for (int id : ids) {
				paramMap.put("operatorId", id);
				 dbUtil.insert("default",
							"cn.wchwu.dao.sys.SysRoleMapper.insertRoleOperator", paramMap);
			}
		}
		
	}
	
	/**
	 * 新增 角色人员关系数据
	 * @param roleCode
	 * @param ids
	 */
	public void deleteRoleOperator(String roleCode, int[] ids) {
		if (ids != null) {
			//参数赋值
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleCode", roleCode);
			
			//遍历赋值变参，循环执行删除
			for (Integer id : ids) {
				paramMap.put("operatorId", id);
				dbUtil.delete("default",
						"cn.wchwu.dao.sys.SysRoleMapper.deleteRoleOperator", paramMap);
			}
		}
		
	}
}
