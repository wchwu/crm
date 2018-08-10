<%@page import="cn.wchwu.framework.utils.DateUtil"%>
<%@page import="org.apache.commons.lang3.time.DateUtils"%>
<%@page import="cn.wchwu.web.filter.AuthorityFilter"%>
<%@page import="cn.wchwu.model.sys.SysOperator"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	SysOperator operator = (SysOperator) session
			.getAttribute(AuthorityFilter.SESSION_KEY_OPERATOR);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作员录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<div data-options="region:'center',fit:true,border:false">
			<table style="width: 100%;">
				<tr>
					<td><fieldset>
							<legend>用户信息</legend>
							<table class="table" style="width: 100%;">
								<tr>
									<th>用户ID</th>
									<td><%=operator.getId()%></td>
									<th>登录名</th>
									<td><%=operator.getLoginName()%></td>
								</tr>
								<tr>
									<th>姓名</th>
									<td><%=operator.getRealName()%></td>
									<th>性别</th>
									<td>
										<%
											if (operator.getGender() != null
													&& operator.getGender().equals("0")) {
												out.print("男");
											} else {
												out.print("女");
											}
										%>
									</td>
								</tr>
								<tr>
									<th>联系电话</th>
									<td><%=operator.getTel()%></td>
									<th>邮箱地址</th>
									<td><%=operator.getEmail()%></td>
								</tr>
								<tr>
									<th>地址</th>
									<td colspan="3"><%=operator.getAddress()%></td>
								</tr>
								<tr>
									<th>创建时间</th>
									<td><%=DateUtil.dateToString(operator.getCreateTime())%></td>
									<th>修改时间</th>
									<td><%=DateUtil.dateToString(operator.getUpdateTime())%></td>
								</tr>
								<tr>
									<th colspan="4" style="text-align: center;"><a href="#"
										class="easyui-linkbutton" onclick="javascript:closeDialog();">关&nbsp;闭
									</a></th>
								</tr>
							</table>
						</fieldset></td>
				</tr>
				<%-- <tr>
				<td>
					<fieldset>
						<legend>权限信息</legend>
						<table class="table" style="width: 100%;">
							<thead>
								<tr>
									<th>角色</th>
									<th>机构</th>
									<th>权限</th>
								</tr>
							</thead>
							<tr>
								<td valign="top">
									<%
										out.println("<ul>");
										for (Syrole role : roles) {
											out.println(StringUtil.formateString("<li title='{1}'>{0}</li>", role.getName(), role.getDescription()));
										}
										out.println("</ul>");
									%>
								</td>
								<td valign="top">
									<%
										out.println("<ul>");
										for (Syorganization organization : organizations) {
											out.println(StringUtil.formateString("<li>{0}</li>", organization.getName()));
										}
										out.println("</ul>");
									%>
								</td>
								<td valign="top"><ul id="resources"></ul></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr> --%>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	function closeDialog() {
		var $dialog = parent.$('#dialog_' + window.name);
		if ($dialog) {
			$dialog.dialog('close');
		} else {
			window.close();
		}
	}
</script>
</html>