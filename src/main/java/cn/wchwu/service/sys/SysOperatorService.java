package cn.wchwu.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.common.DBUtil;
import cn.wchwu.dao.sys.SysOperatorMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.SysOperator;


/**
 * 系统操作员管理服务
 * 
 * @author orh
 *
 */
@Service("sysOperatorService")
public class SysOperatorService {
	@Autowired
	private DBUtil<SysOperator> dbUtil;

	@Autowired
	private SysOperatorMapper sysOperatorMapper;

	/**
	 * 分页查询系统操作员
	 * 
	 * @param page
	 *            分页对象 传空时查询全部
	 * @param parameter
	 *            查询条件
	 * @return
	 */
	public List<SysOperator> querySysOperator(PageCond page, Object parameter) {
		return sysOperatorMapper.querySysOperator("default", page, parameter);
	}
	
	/**
	 * 查询单个人员
	 * @param paramMap
	 * @return
	 */
	public SysOperator selectOne(Map<String,Object> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		paramMap.put("ensureOne", "y");	//确保只查询一条数据，指定rownumber = 1
		
		List<SysOperator> list = querySysOperator(null, paramMap);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 按照Id查询
	 * @param operatorId
	 * @return
	 */
	public SysOperator getById(long operatorId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", operatorId);
		return selectOne(paramMap);
	}

	/**
	 * 新增操作员
	 * 
	 * @param sysOperator
	 * @return
	 */
	public int insertSysOperator(SysOperator sysOperator) {
		return dbUtil.insert("default",
				"cn.wchwu.dao.sys.SysOperatorMapper.insertSysOperator",
				sysOperator);
	}

	/**
	 * 删除操作员(更新状态为已删除)
	 * 
	 * @param sysOperator
	 * @return
	 */
	public int delSysOperator(long id) {
		return dbUtil.update("default",
				"cn.wchwu.dao.sys.SysOperatorMapper.delSysOperator", id);
	}

	/**
	 * 批量删除操作员(更新状态为已删除)
	 * 
	 * @param sysOperator
	 * @return
	 */
	public void delSysOperatorBatch(List<Long> ids) {
		if (ids != null) {
			for (long id : ids) {
				delSysOperator(id);
			}
		}
	}

	/**
	 * 更新操作员
	 * 
	 * @param sysOperator
	 * @return
	 */
	public int updateSysOperator(SysOperator sysOperator) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", sysOperator.getId());
		List<SysOperator> list = querySysOperator(null, paramMap);
		if (list != null) {
			SysOperator old = list.get(0);
			//密码有修改的情况下，加密后再存储
			if (!old.getPassword().equals(sysOperator.getPassword())) {
				sysOperator.setPassword(DigestUtils.md5Hex(sysOperator.getPassword()));
			}
		}
		//BeanUtils
		return dbUtil.update("default",
				"cn.wchwu.dao.sys.SysOperatorMapper.updateSysOperator", sysOperator);
	}

	/**
	 * 根据登录名获取用户角色集合
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String userName) {
		return sysOperatorMapper.findRoles(userName);
	}

	/**
	 * 根据登录名获取用户可访问的path
	 * @param username
	 * @return
	 */
	public Set<String> findAllowPath(String userName) {
		return sysOperatorMapper.findAllowPath(userName);
	}

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public SysOperator findByUserName(String userName) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("loginName", userName);
		return selectOne(paramMap);
	}
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public SysOperator findByUserNameAndPwd(String userName, String password) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("loginName", userName);
		paramMap.put("password", password);
		return selectOne(paramMap);
	}
}
