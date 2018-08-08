package cn.wchwu.framework.mybatis.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

public class SQLHelper {
	private static Logger logger = Logger.getLogger(SQLHelper.class);

	/**
	 * 获取总条数
	 * 
	 * @param sql
	 * @param mappedStatement
	 * @param parameterObject
	 * @param countBoundSql
	 * @param dialect
	 * @return
	 * @throws SQLException
	 */
	public static int getCount(final String count_sql,
			final MappedStatement mappedStatement,
			final Object parameterObject, final BoundSql countBoundSql,
			Connection connection) throws SQLException {
		logger.debug("Total count SQL [{" + count_sql + "}] ");
		logger.debug("Total count Parameters: {" + parameterObject + "} ");

		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(count_sql);
			// Page SQL和Count SQL的参数是一样的，在绑定参数时可以使用一样的boundSql
			DefaultParameterHandler handler = new DefaultParameterHandler(
					mappedStatement, parameterObject, countBoundSql);
			handler.setParameters(countStmt);

			rs = countStmt.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			logger.debug("Total count: {" + count + "}");
			return count;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} finally {
				if (countStmt != null) {
					countStmt.close();
				}
			}
		}
	}
}
