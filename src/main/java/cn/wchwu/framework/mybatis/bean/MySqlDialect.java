package cn.wchwu.framework.mybatis.bean;

import org.apache.log4j.Logger;


public class MySqlDialect implements Dialect {

	private static Logger log = Logger.getLogger(MySqlDialect.class);

	@Override
	public String buildCountSql(String sql) {
		sql = replaceFormatSqlOrderBy(sql);
		return "select count(*) from (" + sql + ") tmp_count";
	}

	@Override
	public String buildPageSql(String sql, PageCond page) {
		sql = sql + " limit " + page.getBegin() + "," + page.getLength();
		log.debug("构建出的分页sql：" + sql);
		return sql;
	}
	
	public static String replaceFormatSqlOrderBy(String sql) {
		sql = sql.replaceAll("(\\s)+", " ");
		int index = sql.toLowerCase().lastIndexOf("order by");
		if (index > sql.toLowerCase().lastIndexOf(")")) {
			String sql1 = sql.substring(0, index);
			String sql2 = sql.substring(index);
			sql2 = sql2
					.replaceAll(
							"[oO][rR][dD][eE][rR] [bB][yY] [\u4e00-\u9fa5a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[\u4e00-\u9fa5a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*",
							"");
			return sql1 + sql2;
		}
		return sql;
	}
}
