package cn.wchwu.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.model.sys.SysMenu;


public interface SysFuncMapper {

	/**
	 * 
	 * @param dsName
	 *            数据源名称
	 * @param page
	 *            分页对象
	 * @param parameter
	 *            查询参数
	 * @return
	 */
	public List<SysFunc> queryFunc(@Param("dsName") String dsName,
			@Param("PAGE") PageCond page, @Param(value = "map") Object parameter);

	/**
	 * 根据角色代码查询角色功能树（使用菜单实体作为查询结果载体）
	 * @param roleCode 角色代码
	 * @return
	 */
	public List<SysMenu> queryMenuFuncTree();
}
