package cn.wchwu.web.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.wchwu.model.sys.SysOperator;
import cn.wchwu.service.sys.SysOperatorService;
import cn.wchwu.web.filter.AuthorityFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 系统登录控制器 为了方便扩展其它如LDAP验证，所以将登录单独写了一个controller
 * 
 * @author wcw.sh
 * @version 创建时间：2015年5月6日 下午1:35:07
 */
@Controller
public class SysLoginController {

	@Autowired
	private SysOperatorService sysOperatorService;

	@RequestMapping("/login")
	public ModelAndView showLoginForm(@RequestParam(required = true) String userName, @RequestParam(required = true) String password, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		SysOperator operator = sysOperatorService.findByUserName(userName);
		if (operator == null) {
			// 如果用户不存在(不明确提示用户)
			mv.addObject("errorMsg", "用户名或密码错误");
			mv.setViewName("login");
		} else {
			if (operator.getLocked()) {
				mv.addObject("errorMsg", "用户名已经被锁定,如需解锁，请联系管理员。");
			} else {
				if ("local".equals(operator.getAuthMode())) {
					// 本地密码验证
					if (DigestUtils.md5Hex(password).equals(operator.getPassword())) {
						HttpSession session = req.getSession();
						session.setAttribute(AuthorityFilter.SESSION_KEY_OPERATOR, operator);// session存储用户信息
						session.setAttribute(AuthorityFilter.SESSION_KEY_OPERA_FUNC, sysOperatorService.findAllowPath(userName)); // session存储用户可用权限

						mv.setViewName("redirect:/pages/main.jsp");
					} else {
						mv.addObject("errorMsg", "用户名或密码错误");
						mv.setViewName("login");
					}
				} else {
					// 其它密码验证方式暂未实现
					mv.addObject("errorMsg", "用户的认证方式尚未实现,需要更改用户认证方式，请联系管理员。");
				}
			}
		}
		if (mv.getModel().get("errorMsg") != null) {
			mv.addObject("userName", userName);
			mv.setViewName("login");
		}
		return mv;
	}

	/**
	 * 注销
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req) {
		req.getSession().invalidate();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/login.jsp");
		return mv;
	}

	/**
	 * 测试异常信息
	 * 
	 * @return
	 */
	@RequestMapping("test")
	public JSONObject testException() {

		return JSON.parseObject("123");
	}
}
