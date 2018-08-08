package cn.wchwu.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSONObject;

import cn.wchwu.framework.utils.SysCacheUtil;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.framework.utils.ServletPathMatcher;
import cn.wchwu.framework.utils.WebUtil;


public class AuthorityFilter implements Filter{
	/**
	 * filter参数名
	 */
	public static final String PARAM_NAME_LOGIN_URL = "loginUrl";
	public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
	public static final String PARAM_NAME_UNAUTHORIZED_URL = "unauthorizedUrl";

	/**
	 * session 键名
	 */
	public static final String SESSION_KEY_OPERATOR = "SESSION_KEY_OPERATOR";
	public static final String SESSION_KEY_OPERA_FUNC = "SESSION_KEY_OPERA_FUNC";

	/**
	 * 配置参数
	 */
	private Set<String> excludesPattern;
	private String loginUrl;
	private String unauthorizedUrl;

	/**
	 * 公共变量
	 */
	private String contextPath;
	private static UrlPathHelper urlPathHelper = new UrlPathHelper();
	private static ServletPathMatcher pathMatcher = ServletPathMatcher
			.getInstance();
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest ;
		HttpServletResponse response = (HttpServletResponse)servletResponse ;
		HttpSession session = request.getSession() ;
		Object objOperator = session.getAttribute(SESSION_KEY_OPERATOR);

		// 资源请求地址
		String reqPath = urlPathHelper.getServletPath(request) ;
		if(reqPath.endsWith(loginUrl)){
			// 1、请求到登录页面 放行
			chain.doFilter(servletRequest, servletResponse);
		}else if(isExclusion(reqPath)){
			// 2、比如退出、首页等页面无需登录，即此处要放行 允许游客的请求
			chain.doFilter(servletRequest, servletResponse);
		}else if(objOperator != null){
			// 3、如果用户已经登录(更好的实现方式的使用cookie)

			// 3.1 验证用户请求的资源是否有权限
			if (SysCacheUtil.getAllFuncMap().containsKey(reqPath)) { // 访问地址在需控功能集合中

				@SuppressWarnings("unchecked")
				Set<String> allowPath = (Set<String>) session
						.getAttribute(SESSION_KEY_OPERA_FUNC);

				if (allowPath != null && allowPath.contains(reqPath)) {
					// path 有权限可以访问
					chain.doFilter(servletRequest, servletResponse);
				} else {
					// 没有权限
					SysFunc func = SysCacheUtil.getAllFuncMap().get(reqPath);
					String msg = "";

					if (func.getMenuName() != null) {
						msg = "你没有权限访问资源[" + reqPath + "],请联系管理员为你开放菜单["
								+ func.getMenuName() + "]下的["
								+ func.getFuncName() + "]功能。如已经开通，需重新登录生效。";
					} else {
						msg = "你没有权限访问资源[" + reqPath + "],请联系管理员为你开放["
								+ func.getFuncName() + "]功能。如已经开通，需重新登录生效。";
					}

					if (request.getHeader("x-requested-with") != null) {
						// 有 x-requested-with 则认为是ajax 请求(这个判断条件 还需要多多测试)
						response.setContentType("application/json;charset=UTF-8");
						JSONObject json = new JSONObject();
						json.put("success", "1");
						json.put("errorMsg", msg);
						response.getWriter().print(json.toJSONString());
					} else {
						// 否则跳转页面提示
						request.setAttribute("errorMsg", msg);
						request.getRequestDispatcher(unauthorizedUrl).forward(
								request, response);
					}
				}
			}else {
				// 放行
				chain.doFilter(servletRequest, servletResponse);
			}
		}else {
			request.getRequestDispatcher(loginUrl).forward(request, response);
		}
	}

	private boolean isExclusion(String requestURI) {
		if(excludesPattern == null){
			return false ;
		}
		if (contextPath != null && requestURI.startsWith(contextPath)) {
			requestURI = requestURI.substring(contextPath.length());
			if (!requestURI.startsWith("/")) {
				requestURI = "/" + requestURI;
			}
		}
		for (String pattern : excludesPattern) {
			if (pathMatcher.matches(pattern, requestURI)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		{
			String exclusions = config.getInitParameter(PARAM_NAME_EXCLUSIONS);
			if(exclusions != null && exclusions.trim().length() != 0){
				excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
			}
		}
		{
			this.loginUrl = config.getInitParameter(PARAM_NAME_LOGIN_URL) ;
		}
		{
			this.unauthorizedUrl = config.getInitParameter(PARAM_NAME_UNAUTHORIZED_URL);
		}
		this.contextPath = WebUtil.getContextPath(config.getServletContext());
	}
	
}
