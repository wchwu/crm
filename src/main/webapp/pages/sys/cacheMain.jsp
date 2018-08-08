<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="net.sf.ehcache.CacheManager"%>
<%@ page import="net.sf.ehcache.Statistics"%>
<%@ page import="java.util.Arrays"%>
<%String ctx = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>缓存管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
			<table class="table" style="width: 100%;">
				<tbody>
					<%
						CacheManager cacheManager = (CacheManager) request
								.getAttribute("cacheManager");
						String[] cacheNames = cacheManager.getCacheNames();
						int length = cacheNames.length;

						for (int i = 0; i < length; i++) {
							String cacheName = cacheNames[i];
							pageContext.setAttribute("cacheName", cacheName);
							pageContext.setAttribute("cache",
									cacheManager.getCache(cacheName));
							if (cacheName.equals("cn.aresoft.dao.sys.SysDictMapper")) {
								pageContext.setAttribute("memo", "(业务字典缓存)");
							} else if (cacheName.equals("cn.aresoft.dao.sys.SysMenuMapper")) {
								pageContext.setAttribute("memo", "(菜单资源权限缓存)");
							}
							
					%>

					<c:set var="totalCount"
						value="${cache.statistics.cacheHits + cache.statistics.cacheMisses}" />
					<c:set var="totalCount" value="${totalCount > 0 ? totalCount : 1}" />
					<c:set var="cacheHitPercent"
						value="${cache.statistics.cacheHits * 1.0 / (totalCount)}" />

					<tr>
						<th colspan="2" style="text-align: left;">${cacheName}${memo }
							<span style="float: right"> <a
								href="<%=ctx %>/sys/cache/detailView.action?cacheName=${cacheName}">
									详细 </a>
						</span>
						</th>
					</tr>

					<tr>
						<td>总命中率:</td>
						<td>${cacheHitPercent}</td>
					</tr>
					<tr>
						<td>命中次数:</td>
						<td>${cache.statistics.cacheHits}</td>
					</tr>
					<tr>
						<td>失效次数:</td>
						<td>${cache.statistics.cacheMisses}</td>
					</tr>
					<tr>
						<td>缓存总对象数:</td>
						<td>${cache.statistics.objectCount}</td>
					</tr>
					<tr>
						<td>最后一秒查询完成的执行数:</td>
						<td>${cache.statistics.searchesPerSecond}</td>
					</tr>
					<tr>
						<td>最后一次采样的平均执行时间:</td>
						<td>${cache.statistics.averageSearchTime}毫秒</td>
					</tr>
					<tr>
						<td>平均获取时间:</td>
						<td>${cache.statistics.averageGetTime}毫秒</td>
					</tr>
					<%
						}
					%>


				</tbody>
			</table>
			<br /> <br />
		</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>