package cn.wchwu.web.controller.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wchwu.framework.utils.PrettyMemoryUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/sys/cache")
public class CacheManagerController {

	@Autowired
    private CacheManager ehcacheManager;
	
	@RequestMapping("view")
	public String view(HttpServletRequest request) {
		request.setAttribute("cacheManager", ehcacheManager);
		/*String[] cacheNames = ehcacheManager.getCacheNames();
		
		for(String cacheName : cacheNames) {
			Cache cache = ehcacheManager.getCache(cacheName);
			
			long hits = cache.getStatistics().getCacheHits();	//命中次数
			long misses = cache.getStatistics().getCacheMisses();	//失效次数
			long totalCount = hits + misses;
			totalCount = totalCount > 0 ? totalCount : 1;
			
			long cacheHitPercent = hits * 1 / totalCount;	//总命中率
			long objectCount = cache.getStatistics().getObjectCount();	//缓存总对象数
			long searchesPerSecond = cache.getStatistics().getSearchesPerSecond();	//最后一秒查询完成的执行数
			long averageSearchTime = cache.getStatistics().getAverageSearchTime();	//最后一次采样的平均执行时间（毫秒）
			float averageGetTime = cache.getStatistics().getAverageGetTime();	//平均获取时间
			
		}

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("rows",  null);*/

		return "/pages/sys/cacheMain";
	}
	
	@RequestMapping("detailView")
	public ModelAndView detailView(
			@RequestParam(required=true) String cacheName,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/pages/sys/cacheDetail");
		
		List keys = ehcacheManager.getCache(cacheName).getKeys();
		
		mv.addObject("cacheName", cacheName);
		mv.addObject("keys", keys);
		return mv;
	}
	
	@RequestMapping("cacheKeyDetail")
	@ResponseBody
	public JSONObject cacheKeyDetail(
			@RequestParam(required=true) String cacheName,
			@RequestParam(required=true) Integer key,
			HttpServletRequest request) {
		Cache cache = ehcacheManager.getCache(cacheName);
		
		/*
		 List keys = cache.getKeys(); 
		 for (Object k : keys) {
			System.out.println(cache.get(k));
			System.out.println(k);
			System.out.println(k.equals(key));   测试这里相等否？
		}*/
		Element element = cache.get(key);
		
		String dataPattern = "yyyy-MM-dd HH:mm:ss";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("objectValue", element.getObjectValue().toString());
        data.put("size", PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
        data.put("hitCount", element.getHitCount());

        Date latestOfCreationAndUpdateTime = new Date(element.getLatestOfCreationAndUpdateTime());
        data.put("latestOfCreationAndUpdateTime", DateFormatUtils.format(latestOfCreationAndUpdateTime, dataPattern));
        Date lastAccessTime = new Date(element.getLastAccessTime());
        data.put("lastAccessTime", DateFormatUtils.format(lastAccessTime, dataPattern));
        if(element.getExpirationTime() == Long.MAX_VALUE) {
            data.put("expirationTime", "不过期");
        } else {
            Date expirationTime = new Date(element.getExpirationTime());
            data.put("expirationTime", DateFormatUtils.format(expirationTime, dataPattern));
        }

        data.put("timeToIdle", element.getTimeToIdle());
        data.put("timeToLive", element.getTimeToLive());
        data.put("version", element.getVersion());
		
        return JSON.parseObject(JSON.toJSONString(data)) ;
	}
	
	@RequestMapping("delForCacheKey")
    @ResponseBody
    public JSONObject delete(
    		@RequestParam(required=true) String cacheName,
    		@RequestParam(required=true) Integer key) {

        Cache cache = ehcacheManager.getCache(cacheName);
        cache.remove(key);

        JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
        return rsJson;

    }
	
	@RequestMapping("clearCache")
	@ResponseBody
	public JSONObject clearCache(
			@RequestParam(required=true) String cacheName) {
		Cache cache = ehcacheManager.getCache(cacheName);
        cache.clearStatistics();
        cache.removeAll();
		
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
		
	}
}