package cn.wchwu.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 性能监控 spring 拦截器 。在测试时需要把stopWatchHandlerInterceptor放在拦截器链的第一个，这样得到的时间才是比较准确的。
 * 
 * @author orh
 *
 */
public class StopWatchHandlerInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 我们的拦截器是单例，因此不管用户请求多少次都只有一个拦截器实现，即线程不安全，那我们应该怎么记录时间呢？
	 * 解决方案是使用ThreadLocal，它是线程绑定的变量，提供线程局部变量（一个线程一个ThreadLocal，A线程的
	 * ThreadLocal只能看到A线程的ThreadLocal，不能看到B线程的ThreadLocal）。
	 * NamedThreadLocal：Spring提供的一个命名的ThreadLocal实现。
	 */
	private NamedThreadLocal<StopWatch> stopWatchLocal = new NamedThreadLocal<StopWatch>(
			"StopWatch");
	
	private boolean usePerformance = true;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (usePerformance) {
			StopWatch stopWatch = new StopWatch(handler.toString());
			stopWatchLocal.set(stopWatch); // 2. 绑定至线程变量
			stopWatch.start(handler.toString());
		}
		return true; // 继续流程
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (usePerformance) {
			StopWatch stopWatch = stopWatchLocal.get();
			stopWatch.stop();
			String currentPath = request.getRequestURI();
			String queryString = request.getQueryString();
			queryString = queryString == null ? "" : "?" + queryString;
			System.out.println("access url path:" + currentPath + queryString
					+ " |time:" + stopWatch.getTotalTimeMillis() + " millisecond");
			stopWatchLocal.set(null);
		}
		super.afterCompletion(request, response, handler, ex);
	}
}
