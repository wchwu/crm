package cn.wchwu.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.common.DBUtil;
import cn.wchwu.dao.sys.SysBizLogMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysBizLog;


/**
 * 业务日志服务类
 * 
 * @author orh
 *
 */
@Service("sysBizLogService")
public class SysBizLogService {

	@Autowired
	private DBUtil<SysBizLog> dbUtil;

	@Autowired
	private SysBizLogMapper sysBizLogMapper;

	/**
	 * 分页查询业务日志信息
	 * @param page
	 * @param parameter
	 * @return
	 */
	public List<SysBizLog> querySysBizLog(PageCond page, Object parameter) {
		return sysBizLogMapper.querySysBizLog("default", page, parameter);
	}
	
	/**
	 * 记录业务日志信息
	 * @param bizLog
	 * @return
	 */
	public void log(final SysBizLog bizLog) {
		dbUtil.insert("default", "cn.wchwu.dao.sys.SysBizLogMapper.insertBizLog", bizLog);
	}

}
