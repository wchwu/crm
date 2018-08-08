package cn.wchwu.framework.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.caches.ehcache.EhcacheCache;

import cn.wchwu.model.sys.SysDictEntry;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.service.sys.SysDictService;
import cn.wchwu.service.sys.SysFuncService;

/**
 * 系统 基本功能的缓存处理类
 * 
 * @author wchwu
 *
 */
public class SysCacheUtil {
	private static final String COMMON_CACHE_NAME = "cn.aresoft.dao.sys.SysMenuMapper"; // 系统用户管理、菜单管理、功能管理等都是引用的一个缓存
	private static final String DICT_CACHE_NAME = "cn.aresoft.dao.sys.SysDictMapper"; // 业务字典使用单独的缓存name

	private static final String ALL_FUNC_MAP_KEY = "ALL_FUNC_MAP_KEY"; // 系统功能map缓存的key

	public static final String DICT_MAP_CACHE_KEY = "DICT_MAP_CACHE_KEY"; // 业务字典map缓存的key

	public static EhcacheCache commonCache = new EhcacheCache(COMMON_CACHE_NAME);
	public static EhcacheCache dictCache = new EhcacheCache(DICT_CACHE_NAME);

	/**
	 * 获取业务字典Map
	 * 
	 * @return
	 */
	public static Map<String, Map<String, String>> getDictMap() {
		// 得到mybatis的缓存
		Object value = dictCache.getObject(DICT_MAP_CACHE_KEY);

		// 缓存为空时，重新加载一次业务字典
		if (value == null) {
			reloadDict();
			return (Map<String, Map<String, String>>) dictCache
					.getObject(DICT_MAP_CACHE_KEY);
		}
		return (Map<String, Map<String, String>>) value;
	}

	/**
	 * 从缓存中取 功能map
	 * 
	 * @return
	 */
	public static Map<String, SysFunc> getAllFuncMap() {
		// 得到mybatis的缓存
		Object value = commonCache.getObject(ALL_FUNC_MAP_KEY);

		// 缓存为空时，重新加载一次业务字典
		if (value == null) {
			reloadFuncMap();
			return (Map<String, SysFunc>) commonCache
					.getObject(ALL_FUNC_MAP_KEY);
		}
		return (Map<String, SysFunc>) value;
	}

	/**
	 * 查询数据库，重新加载业务字典
	 * 
	 * @return
	 */
	public static boolean reloadDict() {
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, String> entryMap = null;

		// 从spring容器中获取对象
		SysDictService sysDictService = SpringUtil
				.getBean(SysDictService.class);

		// 查出所有字典项
		List<SysDictEntry> typeList = sysDictService.querySysDictEntry(null,
				null);

		// 遍历放入map缓存
		for (SysDictEntry entry : typeList) {
			if (dictMap.containsKey(entry.getDictTypeId())) {
				dictMap.get(entry.getDictTypeId()).put(entry.getDictId(),
						entry.getDictName());
			} else {
				entryMap = new HashMap<String, String>();
				entryMap.put(entry.getDictId(), entry.getDictName());
				dictMap.put(entry.getDictTypeId(), entryMap);
			}
		}

		// 放入 业务字典Cache中
		dictCache.putObject(DICT_MAP_CACHE_KEY, dictMap);
		return true;
	}

	/**
	 * 从数据库重新加载功能map
	 */
	public static void reloadFuncMap() {
		Map<String, SysFunc> funcMap = new HashMap<String, SysFunc>();

		// 从spring容器中获取对象
		SysFuncService sysFuncService = SpringUtil
				.getBean(SysFuncService.class);

		// 查出所有功能项
		List<SysFunc> list = sysFuncService.queryFunc(null, null);

		// 遍历放入map缓存
		for (SysFunc func : list) {
			funcMap.put(func.getFuncUrlPath(), func);
		}
		// 放入 业务字典Cache中
		commonCache.putObject(ALL_FUNC_MAP_KEY, funcMap);
	}

}
