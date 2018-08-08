package cn.wchwu.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.common.DBUtil;
import cn.wchwu.dao.sys.SysFuncMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.model.sys.SysMenu;


/**
 * 系统功能服务类
 * @author orh
 *
 */
@Service("sysFuncService")
public class SysFuncService {

	@Autowired
	private DBUtil<SysFunc> dbUtil;

	@Autowired
	private SysFuncMapper sysFuncMapper;

	/**
	 * 分页查询 系统功能
	 * 
	 * @param page
	 * @param parameter
	 * @return
	 */
	public List<SysFunc> queryFunc(PageCond page, Object parameter) {
		return sysFuncMapper.queryFunc("default", page, parameter);
	}

	/**
	 * 根据条件返回一条
	 */
	public SysFunc selectOne(Map<String,Object> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		paramMap.put("ensureOne", "y");	//确保只查询一条数据，指定rownumber = 1
		
		List<SysFunc> list = queryFunc(null, paramMap);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 单个新增系统功能
	 */
	public int insertFunc(SysFunc func) {
		return dbUtil.insert("default",
				"cn.wchwu.dao.sys.SysFuncMapper.insertFunc", func);
	}

	/**
	 * 根据菜单自动生成的功能
	 */
	public int insertAutoFuncForMenu(SysMenu menu) {
		SysFunc func = new SysFunc();
		func.setStatus("0"); // 功能默认为启用
		func.setSysFlag("0"); // 标识功能为系统自动生成
		func.setLogStatus("1");	//默认不启用日志
		func.setLogType("query");
		func.setFuncName("访问菜单");
		func.setFuncUrlPath(menu.getUrl());
		func.setMemo("访问菜单功能，系统自动生成。");

		// 插入自动生成的功能
		func.setMenuId(menu.getId());
		return insertFunc(func);
	}

	/**
	 * 单个更新
	 */
	public int updateFunc(SysFunc func) {
		return dbUtil.update("default",
				"cn.wchwu.dao.sys.SysFuncMapper.updateFunc", func);
	}

	/**
	 * 单个删除
	 */
	public int deleteFunc(String id) {
		return dbUtil.delete("default",
				"cn.wchwu.dao.sys.SysFuncMapper.deleteFunc", id);
	}

	/**
	 * 批量删除
	 */
	public void deleteFuncBatch(List<String> ids) {
		for (String id : ids) {
			dbUtil.delete("default",
					"cn.wchwu.dao.sys.SysFuncMapper.deleteFunc", id);
		}
	}

	/**
	 * 根据角色代码查询角色功能树（使用菜单实体作为载体）
	 * 
	 * @param roleCode
	 *            角色代码
	 * @return
	 */
	public List<SysMenu> queryMenuFuncTree() {
		return sysFuncMapper.queryMenuFuncTree();
	}

	/**
	 * 保存功能树
	 * 
	 * @param ids
	 *            功能号集合
	 * @param roleCode
	 *            角色代码
	 */
	public void saveRoleFunc(List<String> ids, String roleCode) {
		//清除历史关系
		deleteRoleFunc(roleCode);
		
		if (ids != null) {
			//参数赋值
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleCode", roleCode);
			
			//遍历赋值变参，循环执行删除
			for (String id : ids) {
				paramMap.put("funcId", id);
				 dbUtil.insert("default",
							"cn.wchwu.dao.sys.SysFuncMapper.insertRoleFunc", paramMap);
			}
		}
	}
	
	/**
	 * 查询功能树
	 * @param roleCode
	 * @return
	 */
	public int deleteRoleFunc(String roleCode) {
		return dbUtil.delete("default", "cn.wchwu.dao.sys.SysFuncMapper.deleteRoleFunc", roleCode);
	}

	/**
	 * 根据角色代码，查询角色对应的功能代码集合
	 * @param roleCode 角色代码
	 * @return
	 */
	public List<String> queryFuncIdByRoleCode(String roleCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleCode", roleCode);
		return dbUtil.selectListString("default", "cn.wchwu.dao.sys.SysFuncMapper.queryFuncIdByRoleCode", paramMap);
	}

	/**
	 * 根据 功能id查询功能信息
	 * @param id 功能唯一ID
	 * @return
	 */
	public SysFunc getById(String id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		
		return selectOne(paramMap);
	}
	
}
