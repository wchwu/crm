<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div id="div_menu_layout" class="easyui-layout" data-options="fit:true, border:false">
		<%-- 左侧菜单树 --%>
		<div data-options="region:'west',title: '菜单管理(右键编辑、支持拖拽)',width:250,border:false,split:true,collapsible: true"
			>
			<ul id="menu_tree" style="padding-top: 2px; padding-bottom: 2px;" >	
			</ul>
		</div>

		<%--右侧 --%>
		<div data-options="region:'center',border:false,title:'功能列表',fit:true" style="overflow: hidden;">
			<iframe id="ifm_funcList" src="<%=request.getContextPath()%>/pages/sys/funcMain.jsp" allowTransparency="true" style="overflow:auto" width="100%" height="98%" frameBorder="0" ></iframe>
		</div>
		
	</div>
	<!-- 右键菜单 -->
	<div id="div_node_ctx" class="easyui-menu" style="width:160px;">
			<div id="addChildMenu">增加子菜单</div>
			<div class="menu-sep"></div>
			<div id="addTopMenu" >增加顶级菜单</div>
			<div class="menu-sep"></div>
			<div id="disableMenu" >禁用菜单</div>
			<div id="enableMenu" >启用菜单</div>
			<div class="menu-sep"></div>
			<div id="editMenu" >修改菜单(双击可编辑)</div>
			<div id="removeMenu" >删除</div>
	</div>
	
	<script type="text/javascript">
	//var timeTreeClickFn = null;	//为解决菜单单击、双击冲突
	
	$('#menu_tree').tree({
		url : SYS.contextPath + '/sys/menu/tree.action',
		queryParams: {qtype: 'all'},	//查询全部菜单，不管有没有被禁用
		parentField : 'pid',
		lines : true,
		dnd:true,
		
		// 为tree绑定事件-------------------------------阿弥陀佛分割线-------------------------------------------
	    /* onClick: function(node) {
	        clearTimeout(timeTreeClickFn);
	        timeTreeClickFn = setTimeout(function() {
	            //alert('显示右侧功能');
	        },
	        300);
	    }, */
	    onDblClick: function(node) {
	        //clearTimeout(timeTreeClickFn);	//取消上次延时未执行的单击方法
	        editMenu(node);	//编辑菜单
	    },
	    onContextMenu: function(e, node) {
	        e.preventDefault();
	        $(this).tree('select', node.target);
	        $('#div_node_ctx').menu('show', {
	            left: e.pageX,
	            top: e.pageY
	        }).data('node', node);
	    },
	    onLoadSuccess: function(node, data) {
	       $('#menu_tree').tree('expandAll');
	    },
	    // 树节点拖拽有关-------------------------------优雅的分割线-------------------------------------------
	    onBeforeDrop: function(target, sNode, point) {
	    	
	        var $dom = $('#menu_tree'),
	        	tNode = $dom.tree('getNode', target),
	        	msgTmp = (point == 'append') ? '下面' : ((point == 'top') ? '之前' : '之后'),
	        	tipMsg = '您确认要将【' + sNode.text + '】移动到【' + tNode.text + '】' + msgTmp + '吗?',	//修改的确认提示消息
	        	tRank = $.isNumeric(tNode.rank) ? tNode.rank : 10,
	        	sRank = $.isNumeric(sNode.rank) ? tNode.rank : 10,
	        	data = {
        			id: sNode.id,
        			rank: sRank,	//新的 序号默认为本身序号，top和bottom时才根据具体修改其rank;
        			chgParent: 1,	//是否变更了父菜单，默认1 未变更, 0为变更(为1时 后面的参数都不具意义)
	                pid: null,	//新的父id默认为空
	                toIdSeq: '.',	//目标位置父节点的idSeq，默认为'.',因为当没有父节点时此传此串和后台拼接
	                toMenuLevel: 0,		//目标位置父节点的菜单等级
	                fromIdSeq: '.',		//源位置父节点的idSeq
	                fromMenuLevel: 0	//源位置父节点的菜单等级
	        	},	//要提交的ajax数据
	        	sParentNode, 
	        	tParentNode;
	        
        	 if (point == 'append') {
 	            //节点层级变化,变化约束规则：
 	            if (tNode.isleaf == 'y') {
 	                $.messager.alert("提示", "不允许移动节点到叶子菜单!");
 	                return false;
 	            }
 	            if (tNode.status == '1') {
 	                $.messager.alert("提示", "不允许移动节点到已经禁用的菜单中!");
 	                return false;
 	            }
 	           data.chgParent = 0;
 	        } else {
 	        	data.chgParent = (sNode.pid == tNode.pid) ? 1 : 0;
 	        	data.rank = (point == 'top') ? (tRank - 1) : (tRank +1);
 	        }
        	 
        	if (data.chgParent == 0) {
        		sParentNode = $dom.tree('getParent', sNode.target);	//源位置的父节
        		tParentNode = (point == 'append') ? tNode : $dom.tree('getParent', target);	//目标位置的父节点
        		//1.求pid
        		data.pid = ((point == 'append') ? tNode.id : tNode.pid) || null;
        		
        		//2.求toIdSeq、toMenuLevel 目标位置的父节点信息
        		data.toIdSeq =  tParentNode ? tParentNode.idSeq : data.toIdSeq;
        		data.toMenuLevel = tParentNode ? tParentNode.menuLevel : data.toMenuLevel;
        		//3.求fromIdSeq、fromMenuLevel 源位置的父节点信息
        		data.fromIdSeq = sParentNode ? sParentNode.idSeq : data.fromIdSeq;
        		data.fromMenuLevel = sParentNode ? sParentNode.menuLevel : data.fromMenuLevel;
        	}
        	
	     	if (window.confirm(tipMsg)) {	//这里只能用confirm这种提示，因为$.messager.confirm是非中断执行代码，会继续往后执行
	     		return moveMenu(data);
	     	} else {
	     		return false;
	     	}
	    }  
	});
	
	function moveMenu(data) {
		$.ajax({
		       url: SYS.contextPath + "/sys/menu/move.action",
		       data: data,
		       async: false, //同步请求
		       dataType: "json",
		       success: function(rs, textStatus, jqXHR) {
					if (rs.success == "0") {
						$('#menu_tree').tree('reload');
						$.messager.show({title: "提示", msg: "操作成功" });
						return true;
					} else {
						top.$.messager.alert('提示', rs.errorMsg, 'error');
						return false;
					}
				}
	    });
		return false;
	}
	
	
	//实例化menu的onClick事件
	$('#div_node_ctx').menu({
	    onClick:function(item){
	      menuCtxControl(this, item.id);
	    }
	});
	
	// 根据菜单的id，自动执行某个方法 
	function menuCtxControl(menu, type) {
		var node = $(menu).data('node');
		window[type](node);	//根据id自动执行id对应的方法，省去了一些if的判断
	}
	
	//增加顶级菜单
	function addTopMenu(node) {
		showMenuDialog();
	}
	
	//增加子菜单
	function addChildMenu(node) {
		if (node.isleaf == 'y') {
			$.messager.alert("提示", "不允许给叶子节点添加子菜单");
		} else {
			var paramStr = '?pid=' + node.id;
			showMenuDialog(paramStr);
		}
	}
	
	//修改菜单
	function editMenu(node) {
		var paramStr = '?id=' + node.id;
		showMenuDialog(paramStr);
	} 
	
	//禁用菜单(包括子菜单)
	function disableMenu(node) {
		updateStatus({
			id: node.id,
			idSeq: node.idSeq,
			status: "1"
		});
	}
	
	//启用菜单(包括子菜单)
	function enableMenu(node) {
		updateStatus({
			id: node.id,
			idSeq: node.idSeq,
			status: "0"
		});
	}
	
	//更新菜单状态
	function updateStatus(data) {
		$.ajax({
		       url: SYS.contextPath + "/sys/menu/updateStatus.action",
		       data: data,
		       dataType: "json",
		       success: function(rs, textStatus, jqXHR) {
					if (rs.success == "0") {
						$.messager.show({title: "提示", msg: "操作成功" });
					} else {
						top.$.messager.alert('提示', rs.errorMsg, 'error');
					}
				}
	    });
	}
	//修改菜单
	function removeMenu(node) {
		$.messager.confirm('确认', '您确认要删除选中的菜单？\n注意：删除菜单将删除该菜单下所有子菜单', function(r) {
			if (r) {
				$.ajax({
				       url: SYS.contextPath + "/sys/menu/del.action",
				       data: {id : node.id},
				       dataType: "json",
				       traditional: true,
				       success: function(rs, textStatus, jqXHR) {
							if (rs.success == "0") {
								$.messager.show({title: "提示", msg: "操作成功" });
								$('#menu_tree').tree('remove', node.target);
							} else {
								top.$.messager.alert('提示', rs.errorMsg, 'error');
							}
						}
			    });
				
			}
		});
		
	} 
	
	//显示菜单编辑页，工具方法，供新增顶级菜单、新增子菜单、编辑菜单使用
	function showMenuDialog(paramStr) {
		paramStr = paramStr || '';
		top.SYS.modalDialog({
			title : '新增菜单',
			url : SYS.contextPath + '/pages/sys/menuInput.jsp' + paramStr,
			resizable : true,
			width: '70%',
			height: '50%',
			ctlDom: $('#menu_tree')
		});
	}
	
	/*
	$(function() {
	 	$('#div_menu_layout').layout('panel', 'center').panel({
			onResize : function(width, height) {
				$('#ifm_funcList').attr('width', width*0.8);
			}
		});
		
		var twidth = $('#ifm_funcList').layout('panel', 'center').panel('options').width;
		$('#ifm_funcList').attr('width', twidth*0.8); 
	});
	*/
	</script>
</body>
</html>