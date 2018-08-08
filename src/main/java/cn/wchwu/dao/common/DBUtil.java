package cn.wchwu.dao.common;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wchwu.framework.spring.support.CustomerContextHolder;
import cn.wchwu.framework.spring.support.DynamicSqlSessionDaoSupport;
@Service("databaseUtil")
public class DBUtil<T> extends DynamicSqlSessionDaoSupport{
	private final static Logger log = Logger.getLogger(DBUtil.class);

	// TODO 1. proc execute : normal ,has in paramter, has out paramter
	// TODO 1. select map

	@SuppressWarnings("hiding")
	public <T> T getMapper(Class<T> type) {
		return this.getSqlSession().getMapper(type);
	}
	/**
	 * 查询单个对象
	 * @param dsName 数据源名称
	 * @param statement 命名sqlId
	 * @param parameter 参数
	 * @return 单个映射的对象
	 */
	public T selectOne(String dsName, String statement, Object parameter) {
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().selectOne(statement, parameter);
	}
	/**
	 * 查询对象列表
	 * @param dsName 数据源名称
	 * @param statement 命名sqlId
	 * @param parameter 参数
	 * @return 映射的对象列表
	 */
	public List<T> selectList(String dsName, String statement, Object parameter) {
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().selectList(statement, parameter) ;
	}
	/**
	 * 查询一列
	 * @param dsName 数据源名称
	 * @param statement 命名sqlId
	 * @param parameter 参数
	 * @return
	 */
	public List<String> selectListString(String dsName, String statement, Object parameter){
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().selectList(statement, parameter) ;
	}
	/**
	 * 
	 * @param dsName 数据源名称
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public List<HashMap<String, Object>> selectListMap(String dsName,String statement, Object parameter) {
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().selectList(statement, parameter);
	}
	/**
	 * 执行插入
	 * @param dsName 数据源名称
	 * @param nameSqlId 命名sql ID
	 * @param parameter 参数
	 * @return 影响行数
	 */
	public int insert(String dsName, String nameSqlId, Object parameter) {
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().insert(nameSqlId, parameter);
	}
	/**
	 * 更新数据
	 * @param dsName 数据源名称
	 * @param nameSqlId
	 * @param parameter
	 * @return
	 */
	public int update(String dsName, String nameSqlId, Object parameter){
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().update(nameSqlId, parameter) ;
	}
	
	/**
	 * 删除记录
	 * @param dsName
	 * @param nameSqlId
	 * @param parameter
	 * @return  参数集合
	 */
	public int delete(String dsName, String nameSqlId, Object parameter){
		CustomerContextHolder.setDataSourceName(dsName);
		return this.getSqlSession().delete(nameSqlId, parameter) ;
	}
	
}
