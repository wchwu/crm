package cn.wchwu.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import cn.wchwu.framework.utils.SpringUtil;
import cn.wchwu.framework.utils.SysCacheUtil;
import cn.wchwu.framework.utils.WebUtil;
import cn.wchwu.model.sys.SysBizLog;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.model.sys.SysOperator;
import cn.wchwu.service.sys.SysBizLogService;
import cn.wchwu.web.filter.AuthorityFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 业务日志处理, 处理方式：拦截 controller的方法进行业务日志处理
 * 
 * @author wcw
 *
 */
public class BizLogInterceptor extends HandlerInterceptorAdapter {
	private static String preUrl = "/logout.action";	//需要在action执行前拦截执行日志的
	private static String loginUrl = "/login.action";
	
	private static UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private SysBizLogService bizlogService = SpringUtil
			.getBean(SysBizLogService.class);
	
	private Map<String, SysFunc> funcMap = SysCacheUtil.getAllFuncMap();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String reqPath = urlPathHelper.getServletPath(request);
		// 缓存中取功能 相关信息
		if (preUrl.equals(reqPath) && funcMap.containsKey(reqPath)) {
			SysFunc func = funcMap.get(reqPath);
			if ("0".equals(func.getLogStatus())) {
				saveBizLog(request, reqPath, func);
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(final HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 资源请求地址
		String reqPath = urlPathHelper.getServletPath(request);

		// 缓存中取功能 相关信息
		if (!preUrl.equals(reqPath) && funcMap.containsKey(reqPath)) {
			SysFunc func = funcMap.get(reqPath);
			if ("0".equals(func.getLogStatus())) {
				saveBizLog(request, reqPath, func);
			}
		}
		super.afterCompletion(request, response, handler, ex);
	}

	private void saveBizLog(final HttpServletRequest request,
			final String reqPath, final SysFunc func) {
		SysOperator operator = (SysOperator) request.getSession().getAttribute(
				AuthorityFilter.SESSION_KEY_OPERATOR);
		Map<String, String[]> paramMap = request.getParameterMap();
		
		//还无session的处理
		String loginName = null;
		String realName = null;
		String memo = null;
		if (operator != null) {
			loginName = operator.getLoginName();
			realName = operator.getRealName();
		} else {
			loginName = request.getParameter("userName");
		}
		
		if (loginUrl.equals(reqPath)) {
			memo = operator == null ? "登录失败" : "登陆成功";
		}
		
		//密码字段的处理
		String reqParamStr = JSON.toJSONString(paramMap);
		JSONObject reqJson = JSONObject.parseObject(reqParamStr);
		if (reqJson.getString("password") != null) {
			reqJson.put("password", "******");
			reqParamStr = reqJson.toJSONString();
		}
		
		SysBizLog bizLog = new SysBizLog();
		bizLog.setClientIp(WebUtil.getRemoteAddr(request));
		bizLog.setFuncName(func.getFuncName());
		bizLog.setLogType(func.getLogType());
		bizLog.setMemo(memo);
		bizLog.setMenuName(func.getMenuName());
		bizLog.setOptLoginName(loginName);
		bizLog.setOptRealName(realName);

		bizLog.setReqParams(reqParamStr);
		bizLog.setReqPath(reqPath);

		System.out.println("请求参数：" + reqParamStr);
		bizlogService.log(bizLog);
	}

}
