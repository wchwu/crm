<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--头部页面 --%>
<%
	String contextPath = request.getContextPath();
%>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;">
	<%
			//out.print("欢迎您，管理员");
	%>
</div>
<div style="position: absolute; left: 0px; top: 0px;">
	<img src="<%=contextPath %>/style/images/logo.png" />
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> 
</div>

<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-rainbow'">
		<span>更换皮肤</span>
		<div style="width:120px;">  
			<div onclick="SYS.changeTheme('default');" title="default">default</div>
			<div onclick="SYS.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
			<div class="menu-sep"></div>
			<div onclick="SYS.changeTheme('ui-pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
			<div onclick="SYS.changeTheme('ui-sunny');" title="sunny">sunny</div>
			<div class="menu-sep"></div>
			<div onclick="SYS.changeTheme('metro-green');" title="metro-green">metro-green</div>
			<div onclick="SYS.changeTheme('metro-orange');" title="metro-orange">metro-orange</div>
			<div onclick="SYS.changeTheme('metro-red');" title="metro-red">metro-red</div>
		</div>
	</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="toModifyPassword();">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>
<script type="text/javascript" charset="utf-8">
	var logoutFun = function() {
			location.replace(SYS.contextPath + '/logout.action');
	};
	
	var showMyInfoFun = function() {
		var dialog = top.SYS.modalDialog({
			title : '我的信息',
			url : SYS.contextPath + '/pages/sys/operatorInfo.jsp',
			resizable : true,
			width: '65%',
			height: '55%'
		});
	};
	
	function toModifyPassword() {
		top.SYS.modalDialog({
			title : '修改密码',
			url : SYS.contextPath + '/pages/sys/modifyPassword.jsp',
			resizable : true,
			width: 540,
			height: 240
		});
	}
</script>