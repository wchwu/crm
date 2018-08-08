<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
	function showErrors() {
		var err_el = document.getElementById('errors');
		if (err_el.style.display == 'none') {
			err_el.style.display = '';
		} else {
			err_el.style.display = 'none';
		}
	}
</script>
</head>
<body>
	${exception.message }，您可以
	<a href="javascript:showErrors();">查看详情</a>
	<div style="display: none; color: red;" id="errors">
		<c:forEach items="${exception.stackTrace }" var="e">
	${e }<br />
		</c:forEach>
	</div>

</body>
</html>