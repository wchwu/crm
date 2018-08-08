package cn.wchwu.framework.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import cn.wchwu.framework.mybatis.bean.Dialect;
import cn.wchwu.framework.mybatis.bean.MySqlDialect;
import cn.wchwu.framework.mybatis.bean.OracleDialect;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.mybatis.bean.SQLHelper;
import cn.wchwu.framework.spring.support.CustomerContextHolder;


/**
 * 自定义拦截器: 在执行 query、update将被拦截。<br/>
 * <ol>
 * 主要功能：
 * <li>设置数据源, 参数中带有 @param("dsName") 注解的数据源将作为数据源，如果无此注解参数，或者传值为null，将使用默认的数据源
 * defaultTargetDataSource</li>
 * <li>分页功能，当参数中有 @param("PAGE")注解时拦截器将自动执行分页，如果无此注解参数，或者此注解参数值为null时则按正常执行，不分页</li>
 * <ol>
 * 
 * @author wchwu
 *
 */
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = {
				MappedStatement.class, Object.class }) })
public class CustomInterceptor implements Interceptor {

	private final static Logger log = Logger.getLogger(CustomInterceptor.class);

	/**
	 * 分页-标识
	 */
	public final static String PAGE_PARAM_KEY = "PAGE";

	/**
	 * 数据源-标识
	 */
	public final static String DS_NAME_PARAM_KEY = "dsName";

	static int MAPPED_STATEMENT_INDEX = 0; // 索引为0的参数 为 MappedStatement
	static int PARAMETER_INDEX = 1; // 索引为1的参数为调用方法传入的 参数集合
	static int ROWBOUNDS_INDEX = 2; // 索引为2带参数为 RowBounds
	static int RESULT_HANDLER_INDEX = 3;

	private Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {

		// 获取参数
		Object[] queryArgs = invocation.getArgs();
		Object parameter = queryArgs[PARAMETER_INDEX];
		String methodName = invocation.getMethod().getName();

		PageCond page = null;
		String dsName = null;

		if (parameter instanceof HashMap) {
			HashMap paramMap = (HashMap) parameter;
			if (paramMap != null) {
				// 取分页对象
				if (paramMap.containsKey(PAGE_PARAM_KEY)) {
					page = (PageCond) paramMap.get(PAGE_PARAM_KEY);
				}
				// 取数据源名称
				if (paramMap.containsKey(DS_NAME_PARAM_KEY)) {
					dsName = (String) paramMap.get(DS_NAME_PARAM_KEY);
				}
			}
		}

		// 数据源名称不为空时，设置数据数据源。为空时将使用 spring-mybatis.xml中的 defaultTargetDataSource
		if (dsName != null) {
			if (log.isDebugEnabled()) {
				log.debug("use dataSource: " + dsName);
			}
			CustomerContextHolder.setDataSourceName(dsName);
		}

		// 不是查询、或者分页对象为空的情况下
		if (!"query".equals(methodName) || page == null) {
			return invocation.proceed();
		}

		return doPagination(invocation, queryArgs, parameter, page);

	}

	/**
	 * 处理分页
	 */
	private Object doPagination(Invocation invocation, Object[] queryArgs,
			Object parameter, PageCond page) throws SQLException,
			InvocationTargetException, IllegalAccessException {
		// 分页处理
		if (log.isDebugEnabled()) {
			log.debug("page is not null,Paging in processing! ");
		}

		Executor executor = (Executor) invocation.getTarget();

		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];

		RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];

		BoundSql boundSql = ms.getBoundSql(parameter);

		// connection 作用 1. 用于获取 数据库驱动类型以便得到对应的Dialect 2. 用于分页求和
		Connection conn = null;
		List rsList = null;
		try {
		conn = executor.getTransaction().getConnection();

		String jdbcUrl = conn.getMetaData().getURL();
		dialect = getDialect(jdbcUrl);
		StringBuffer bufferSql = new StringBuffer(boundSql.getSql());

		List<ParameterMapping> parameterMapping = boundSql
				.getParameterMappings();

		if (page.isCount()) { // 需要求总页数
			BoundSql countBoundSql = new BoundSql(ms.getConfiguration(),
					dialect.buildCountSql(boundSql.getSql()), parameterMapping,
					parameter);
			setPageCount(parameter, page, executor, ms, rowBounds, countBoundSql,
					conn);
		}

		String newSql = null;

		if (page.getTotalRows() > 0) {
			// 如果有结果，构建分页sql
			newSql = dialect.buildPageSql(bufferSql.toString(), page);
		} else {
			// 总行为0时，不执行sql查询，直接返回一个list(返回null时会报错)
			return new ArrayList();
		}

		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql,
				parameterMapping, parameter);
		MappedStatement newMs = buildNewMappedStatement(ms, newBoundSql);
		queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		rsList = (List) invocation.proceed();
		
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally {
			if (conn !=null && !conn.isClosed()) {
				conn.close();
			}
		}
		return rsList;
	}

	private void setPageCount(Object parameter, PageCond page,
			Executor executor, MappedStatement ms, RowBounds rowBounds,
			BoundSql countBoundSql, Connection conn) throws SQLException {
		Integer count = 0;
		Cache cache = ms.getCache();

		if (ms.isUseCache() && cache != null) {
			CacheKey cacheKey = executor.createCacheKey(ms, parameter,
					rowBounds, countBoundSql);
			count = (Integer) cache.getObject(cacheKey);
			if (count == null) {
				count = SQLHelper.getCount(countBoundSql.getSql(), ms,
						parameter, countBoundSql, conn);
				cache.putObject(cacheKey, count);
				log.debug("get count for database");
			} else {
				log.debug("get count for cache");
			}
		} else {
			count = SQLHelper.getCount(countBoundSql.getSql(), ms, parameter,
					countBoundSql, conn);
			log.debug("get count for database");
		}
		page.setTotalRows(count);
	}

	private MappedStatement buildNewMappedStatement(MappedStatement ms,
			BoundSql newBoundSql) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(),
				new BoundSqlSqlSource(newBoundSql), ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuffer keyProperties = new StringBuffer();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1,
					keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	// 参考 druid jdbcUtils getDriverClassName
	public static Dialect getDialect(String rawUrl) throws SQLException {

		if (rawUrl.startsWith("jdbc:mysql:")) {
			return new MySqlDialect();
		} else if (rawUrl.startsWith("jdbc:oracle:") //
				|| rawUrl.startsWith("JDBC:oracle:")) {
			return new OracleDialect();
		} else {
			throw new SQLException("Not to support the JDBC drive: " + rawUrl);
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
}