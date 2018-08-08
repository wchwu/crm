package cn.wchwu.framework.spring.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源实现(参考 IBM_hoojo)
 * @author orh
 *
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getDataSourceName();
	}
	
}
