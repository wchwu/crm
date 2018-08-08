package cn.wchwu.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录拦截器 。推荐能使用servlet规范中的过滤器Filter实现的功能就用Filter实现，因为HandlerInteceptor只有在Spring
 * Web MVC环境下才能使用，因此Filter是最通用的、最先应该使用的。如登录这种拦截器最好使用Filter来实现。
 * 
 * @author orh
 * 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String loginUrl = "/login.jsp";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 1、请求到登录页面 放行
		if (request.getServletPath().startsWith(loginUrl)) {
			return true;
		}

		// 2、TODO 比如退出、首页等页面无需登录，即此处要放行 允许游客的请求

		// 3、如果用户已经登录 放行
		if (request.getSession().getAttribute("username") != null) {
			// 更好的实现方式的使用cookie
			return true;
		}

		// 4、非法请求 即这些请求需要登录后才能访问
		// 重定向到登录页面
		response.sendRedirect(request.getContextPath() + loginUrl);
		return false;
	}
}
