package cn.wchwu.dao.sys;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysOperator;


public interface SysOperatorMapper {
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
	public List<SysOperator> querySysOperator(@Param("dsName") String dsName,
			@Param("PAGE") PageCond page, @Param(value = "map") Object parameter);

	/**
	 * 根据登录名获取用户角色集合
	 * @return
	 */
	public Set<String> findRoles(@Param(value = "loginName") String loginName);

	/**
	 * 根据登录名获取用户权限集合
	 * @return
	 */
	public Set<String> findAllowPath(
			@Param(value = "loginName") String loginName);
}
