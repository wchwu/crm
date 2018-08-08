<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="net.sf.ehcache.CacheManager"%>
<%String ctx = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>缓存详情</title>
<jsp:include page="/common.jsp"></jsp:include>
<style type="text/css">
    .detail {
        word-break: break-all;
    }
</style>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<div style="text-align: center;">
			<a id="btn" href="<%=ctx %>/sys/cache/view.action" class="easyui-linkbutton">返回</a>  
	    	<a id="btnClear" class="easyui-linkbutton">清空</a>
		</div>

	    <table class="table" style="width:100%">
	        <tbody>
		        <tr >
		            <th colspan="2" style="text-align: left;">
		                ${cacheName} 键列表
		            </th>
		        </tr>
	        <c:forEach items="${keys}" var="key">
	            <tr>
	                <td style="width: 40%">${key}</td>
	                <td data-key="${key}">
	                    <a class="btn-details" href="javascript:void(0);">查看详细</a>
	                    <a class="btn-delete" href="javascript:void(0);">删除</a>
	                </td>
	            </tr>
	        </c:forEach>
	
	        </tbody>
	    </table>
    <br/><br/>
</div>
</body>
<script type="text/javascript">
$(function() {
    $("#searchText").keyup(function(event) {
        /* if(event.keyCode == 13) {
            window.location.href = "?searchText=" + this.value + "&BackURL=<es:BackURL/>";
        } */
    });

    $(".btn-details").click(function() {
        var td = $(this).closest("td");
        var key = td.data("key");
        var url = SYS.contextPath + "/sys/cache/cacheKeyDetail.action?cacheName=${cacheName}&key=" + key;
        var detail = td.find(".detail");
        if(detail.length) {
            detail.remove();
            return;
        }
        
        $.getJSON(url, function(data) {
            detail = $('<div class="detail"></div>');

            var detailInfo = "";
            detailInfo += "命中次数:" + data.hitCount;
            detailInfo +=" | ";
            detailInfo += "大小:" + data.size;
            detailInfo +=" | ";
            detailInfo += "最后创建/更新时间:" + data.latestOfCreationAndUpdateTime;
            detailInfo +=" | ";
            detailInfo += ",最后访问时间:" + data.lastAccessTime;
            detailInfo +=" | ";
            detailInfo += "过期时间:" + data.expirationTime;
            detailInfo +=" | ";
            detailInfo += "timeToIdle(秒):" + data.timeToIdle;
            detailInfo +=" | ";
            detailInfo += "timeToLive(秒):" + data.timeToLive;
            detailInfo +=" | ";
            detailInfo += "version:" + data.version;

            detailInfo +="<br/><br/>";
            detailInfo +="值:" + data.objectValue;

            detail.append(detailInfo);
            td.append(detail);

        });
    });

    $(".btn-delete").click(function() {
            var td = $(this).closest("td");
            var key = td.data("key");
            var url = SYS.contextPath + "/sys/cache/delForCacheKey.action?cacheName=${cacheName}&key=" + key;
            $.get(url, function(data) {
                td.closest("tr").remove();
            });

    });

    $("#btnClear").click(function() {
    	top.$.messager.confirm('确认', '确认清空[${cacheName}]的整个缓存吗？', function(r) {
    		if (r) {
        		$.ajax({
    		       url: SYS.contextPath + "/sys/cache/clearCache.action?cacheName=${cacheName}",
    		       dataType: "json",
    		       success: function(data, textStatus, jqXHR) {
    					if (data.success == "0") {
    						$.messager.show({title: "提示", msg: "操作成功" });
    						window.location.reload();
    					} else {
    						top.$.messager.alert('提示', data.errorMsg, 'error');
    					}
    				}
    		    });
        	}
    	});
    	
    });
});
</script>
</html>