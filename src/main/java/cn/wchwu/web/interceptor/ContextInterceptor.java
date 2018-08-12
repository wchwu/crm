package cn.wchwu.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tk.mybatis.mapper.util.StringUtil;

/**
 * @description:上下文拦截器
 * @reason:获取上下文路径
 * @author Chaowu.Wang
 * @date 2018年3月15日 下午7:38:26
 * @since JDK 1.6
 */
@Component
public class ContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String ctx = (String) session.getAttribute("ctx");
        if (StringUtil.isEmpty(ctx)) {
            session.setAttribute("ctx", request.getContextPath());
        }
        String version = (String) session.getAttribute("version");
        if (StringUtil.isEmpty(version)) {
            session.setAttribute("version", "4.0");
        }
        request.setAttribute("serverDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return true;
    }

}
