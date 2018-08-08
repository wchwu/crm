package cn.wchwu.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysBizLog;


public interface SysBizLogMapper {

	/**
	 * 分页查询业务日志
	 * @param dsName
	 * @param page
	 * @param parameter
	 * @return
	 */
	public List<SysBizLog> querySysBizLog(@Param("dsName") String dsName,
			@Param("PAGE") PageCond page, @Param(value = "map") Object parameter);

}
