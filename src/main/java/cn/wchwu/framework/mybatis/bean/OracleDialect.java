package cn.wchwu.framework.mybatis.bean;

import org.apache.log4j.Logger;

public class OracleDialect implements Dialect {

	private static Logger log = Logger.getLogger(OracleDialect.class);

	/**
	 * 生成得到总记录数SQL
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 总记录数的sql
	 */
	public String buildCountSql(String sql) {
		sql = replaceFormatSqlOrderBy(sql);
		return "select count(*) from (" + sql + ") tmp_count";
	}

	/**
	 * 生成分页sql
	 * 
	 * @param sql
	 *            查询sql
	 * @param start
	 * @param pageSize
	 * @return 分页sql
	 */
	public String buildPageSql(String sql, PageCond page) {
		// 检查和填充page对象字段

		if (page.getCurrentPage() == 1) {
			sql = "select row_.* from (" + sql + ") row_ where rownum <= "
					+ page.getLength();
		} else {
			sql = "select * from(SELECT row_.*,rownum rn FROM(" + sql
					+ ") row_ WHERE rownum <= " + page.getEnd()
					+ ") where rn >" + page.getBegin();
		}
		if (log.isDebugEnabled()) {
			log.debug("构建出的分页sql：" + sql);
		}
		return sql;
	}

	/**
	 * 去除 sql的order by 部分(由于sql灵活性很大，这里只是对一些情况进行了处理)
	 * @param sql
	 * @return
	 */
	public static String replaceFormatSqlOrderBy(String sql) {
		sql = sql.replaceAll("(\\s)+", " ");
		int index = sql.toLowerCase().lastIndexOf("order by");
		if (index > sql.toLowerCase().lastIndexOf(")")) {
			String sql1 = sql.substring(0, index);
			String sql2 = sql.substring(index);
			//增加了一个处理 去除 nulls last 部分，支持oracle的 order by xx nulls last的语法
			sql2 = sql2.replaceAll("[nN][uU][lL][lL][sS] ([lL][aA][sS][tT]|[fF][iI][rR][sS][tT])", "");
			
			sql2 = sql2
					.replaceAll(
							"[oO][rR][dD][eE][rR] [bB][yY] [\u4e00-\u9fa5a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[\u4e00-\u9fa5a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*",
							"");
			return sql1 + sql2;
		}
		return sql;
	}

}
