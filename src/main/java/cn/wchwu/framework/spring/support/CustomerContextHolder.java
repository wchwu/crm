package cn.wchwu.framework.spring.support;

public class CustomerContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSourceName(String dataSourceName) {
		contextHolder.set(dataSourceName);
	}

	public static String getDataSourceName() {
		return contextHolder.get();
	}

	public static void clearDataSourceName() {
		contextHolder.remove();
	}
}
