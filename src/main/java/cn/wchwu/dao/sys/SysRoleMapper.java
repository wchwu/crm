package cn.wchwu.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysRole;


public interface SysRoleMapper {
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
	public List<SysRole> queryRole(@Param("dsName") String dsName,
			@Param("PAGE") PageCond page, @Param(value = "map") Object parameter);
}
