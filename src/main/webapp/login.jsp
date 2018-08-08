<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>黔沪青年管理系统</title>
 <style>.error{color:red;}</style>
<jsp:include page="/common.jsp"></jsp:include>
<script type="text/javascript">
	if(top.location !== self.location) {
		top.location.href = self.location.href;
	}
</script>
<link rel="stylesheet" href="<%=ctx%>/style/login.css" type="text/css">
</head>
<body>
<div class="top wapper clx" style="width:100%;">
<div class="logo"></div>
  <div class="fr"><a href="http://www.qhqn.cn/" target="_blank">黔沪青年官网</a> <span class="gray">&nbsp;&nbsp; | &nbsp;&nbsp;</span>技术支持热线：xx-xxx-xxxx</div>
  
</div>
<div class="container">
  <div class="content">
    <div class="logintitle" style="height:70px;overflow:hidden;">
		<strong class="on" rel="log1">黔沪青年管理系统</strong> &nbsp;
	</div>
	<form action="<%=ctx %>/login.action" method="post" onsubmit="return checkForm();"> 
		<ul id="log1">
			  <li style="margin-left:20px;height:36px;width:330px;">
				<label>账 号：</label>
				<input type="text" size="24" id="iptUserName" name="userName" maxlength="20" value="" style="width:177px;height:22px;" />
			  </li>
			  <li style="margin-left:20px;height:36px;width:330px;">
				<label>密 码：</label>
				<input style="width:177px;height:22px;" type="password" size="24" name="password" maxlength="50" id="iptPassword" />
			  </li>
			  <li class="pl4" style="height:30px;margin-top:10px;">
				<input style="float:left;display:inline;width:80px;margin-left:78px;line-height:20px;" type="submit" value="登 录" class="btn"> 
				<input style="float:left;display:inline;width:80px;margin-left:6px;line-height:20px;"  type="reset" value="重 置" class="btn">
			  </li>
	    </ul>
	    <div style="text-align: center; color: red; font-size: 12px;">${errorMsg}</div>
    </form>
   
  </div>
</div>
<div class="footer wapper">
	<a href="http://www.qhqn.cn/" target="_blank">关于黔沪青年</a>&nbsp;&nbsp;</br>
	<div style="margin-top:10px;">copyright Notice &copy; 2018-2099 
	共青团贵州省委驻上海工作委员会 
	沪ICP备17024538号<br>
 联系地址：上海市普陀区中山北路2668号联合大厦14楼（贵州省人民政府驻上海办事处） 
 公司服务邮箱：wx@qhqn.cn</div> 
</div>
<script type="text/javascript">
	function checkForm() {
		if (!$('#iptUserName').val()) {
			alert('请输入用户名!');
			$('#iptUserName').focus();
			return false;
		} else if(!$('#iptPassword').val()) {
			alert('请输入密码!');
			$('#iptPassword').focus();
			return false;
		}
		return true;
	}
</script>
</body>
</html>