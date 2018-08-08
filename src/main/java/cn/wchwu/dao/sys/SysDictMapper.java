package cn.wchwu.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysDictEntry;
import cn.wchwu.model.sys.SysDictType;


/**
 * 系统业务字典
 * @author orh
 *
 */
public interface SysDictMapper {

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
	public List<SysDictEntry> querySysDictEntry(@Param("dsName") String dsName,
			@Param("PAGE") PageCond page, @Param(value = "p") Object parameter);
	
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
	public List<SysDictType> querySysDictType(@Param("dsName") String dsName,
			@Param("PAGE") PageCond page, @Param("map") Object parameter);
}
