<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%--项目基本信息赋值 --%>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<%String contextPath = request.getContextPath();%>

<%--定义版本信息：在自己扩展的js、css样式后面跟上版本参数就可以避免客户端缓存不刷新的问题了。 这里是统一的版本没有针对每一个文件做版本 --%>
<%
	//String version = "3.0";
	String version = (String)request.getSession().getAttribute("version");

%>

<%-- cookie 中获取jquery easyui 最近使用的主题 --%>
<%
Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
if (null != cookies) {
	for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	}
}
String easyuiTheme = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
if (cookieMap.containsKey("easyuiTheme")) {
	Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
	easyuiTheme = cookie.getValue();
}
%>

<%-- 将项目基本信息赋值给 js变量 --%>
<script type="text/javascript">
(function() {
	window.SYS = window.SYS || {};
	SYS.contextPath = '<%=contextPath%>';
	SYS.basePath = '<%=basePath%>';
	SYS.version = '<%=version%>';
})();

</script>
 
<script type="text/javascript" src="<%=contextPath%>/js/My97DatePicker/WdatePicker.js" ></script>
 
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<%-- 根据请求加载不同的jquery,IE8及以前的版本 引入 jquery1.xxx 版本，否则引入jquery 2.xxx --%>
<%
/* 
String User_Agent = request.getHeader("User-Agent");
if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1 && (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)) {
	out.println("<script src='" + contextPath + "/js/jquery-1.11.1.min.js' type='text/javascript'></script>");
} else {
	out.println("<script src='" + contextPath + "/js/jquery-2.1.1.min.js' type='text/javascript'></script>");
} */
out.println("<script src='" + contextPath + "/js/jquery-1.11.1.min.js' type='text/javascript'></script>");
%>
<%-- 引入EasyUI、easyui扩展、以及国际化文件、主题、相关图标样式 --%>
<script type="text/javascript" src="<%=contextPath%>/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/jquery-easyui-portal/jquery.portal.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link id="easyuiTheme" rel="stylesheet" href="<%=contextPath%>/js/easyui/themes/<%=easyuiTheme%>/easyui.css?version=<%=version%>" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/js/easyui/themes/icon.css?version=<%=version%>" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/js/jquery-easyui-portal/portal.css?version=<%=version%>" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/style/icon-ext.css?version=<%=version%>" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/style/style.css?version=<%=version%>" type="text/css">

<link rel="icon" href="<%=contextPath%>/style/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=contextPath%>/style/images/favicon.ico" type="image/x-icon"/>

<%-- 自定义扩展JS文件引入 --%>
<script type="text/javascript" src="<%=contextPath%>/js/easyui-my97.js?version=<%=version%>"></script>
<script type="text/javascript" src="<%=contextPath%>/js/SYS.js?version=<%=version%>"></script>
<script type="text/javascript" src="<%=contextPath%>/js/easyui-ext.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/main.js?version=<%=version%>"></script>