package cn.wchwu.framework.mybatis.bean;

public interface Dialect {
	/**
	 * 生成得到总记录数SQL
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 总记录数的sql
	 */
	public String buildCountSql(String sql);

	/**
	 * 生成分页sql
	 * 
	 * @param sql
	 *            查询sql
	 * @param start
	 * @param pageSize
	 * @return 分页sql
	 */
	public String buildPageSql(String sql, PageCond page);

}
