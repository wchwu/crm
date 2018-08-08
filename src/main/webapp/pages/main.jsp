<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>黔沪青年-管理平台</title>
<jsp:include page="/common.jsp"></jsp:include>
<script type="text/javascript">
	var mainMenuTree,
		mainTabs;

	$(function() {
		top.$.messager.progress({
			text : '资源数据加载中....'
		});
		
		//左侧菜单 初始话
		mainMenuTree = $('#mainMenu_Tree').tree({
			url : SYS.contextPath + '/sys/menu/userTree.action',
			parentField : 'pid',
			queryParams: {async: 'n'},	//非异步加载。async 为 'n'时则一次性加载，async为'y'时则异步加载
			lines : true,
			onLoadSuccess: function(node, data) {
				top.$.messager.progress('close');
			},
			onClick : function(node) {
				if (node.isleaf == 'n') {
					$('#mainMenu_Tree').tree('toggle', node.target);
				}
				if (node.url) {
					var src = node.url;
					if (SYS.startWith(node.url, '/')) {
						src = SYS.contextPath + src;
					}
					if (node.openModel == 'new') {
						window.open(src);
					} else if(node.openModel == 'dialog') {
						SYS.modalDialog({
							title : node.text,
							url : src,
							resizable : true,
							width: '70%',
							height: '60%'
						});
					}  else {
						var opts = {
							title : node.text,
							iconCls : node.iconCls,
							src: src
						};
						mainpage.mainTabs.addTab(opts);
					}
				}
			}
		});
		
		//实例化menu的onClick事件
		$('#main_tab_ctx').menu({
		    onClick:function(mmDiv){
		     	menuCtxControl(this, mmDiv.id);
		    }
		});
		
		//刷新图标刷新树菜单
		$('#navMenu_refresh').click(function() {
			mainMenuTree.tree('reload');
		});

		$('#mainLayout').layout('panel', 'center').panel({
			onResize : function(width, height) {
				SYS.setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);
			}
		});
		
		mainTabs = $('#mainTabs').tabs({
			fit : true,
			border : false,
			tools : '#mainTabs_tools',
			tabPosition: SYS.cookieGet('mainTabs_position') || 'top',
			onContextMenu : function (e, title,index) {
				e.preventDefault();
				$('#main_tab_ctx').menu('show', {
		            left: e.pageX,
		            top: e.pageY
		        }).data('tab', {'title': title, 'index': index});
			}
		});
		
	});
	
	// 根据菜单的id，自动执行某个方法 
	function menuCtxControl(menu, type) {
		var tab = $(menu).data('tab');
		closeMenuTabsByType(type, tab.title, tab.index);
	}
	
	/**
	 * 根据右键类型来关闭菜单 Tab
	 */
	function closeMenuTabsByType(type, title, index) {
		var tabs = mainTabs.tabs('tabs'), 
			t_len = tabs.length,
			tmp_opts, curIndex;
	
		while(t_len--) {
			tmp_opts = tabs[t_len].panel('options');
			
			if (tmp_opts.closable) {
				switch (type) {
					case 'mm-tabcloseall':
						mainTabs.tabs('close', tmp_opts.title);
						break;
					case 'mm-tabcloseother':
						if (tmp_opts.title != title) {
							mainTabs.tabs('close', tmp_opts.title);
						}
						break;
					case 'mm-tabcloseright':
						curIndex = mainTabs.tabs('getTabIndex', tabs[t_len]);
						if (curIndex > index) {
							mainTabs.tabs('close', curIndex);
						}
						break;
					case 'mm-tabcloseleft':
						curIndex = mainTabs.tabs('getTabIndex', tabs[t_len]);
						if (curIndex < index) {
							mainTabs.tabs('close', curIndex);
						}
						break;
				}
			}
		}
		//隐藏右键弹出的panel
		$('#main_tab_ctx').menu('hide');
		//选择当前的tab页
		if (type != 'all') {
			mainTabs.tabs('select', title);
		}
	}
	
	// 全屏切换tab
	function toggleFullScreen() {
		var top = $(mainLayout).layout('panel', 'center').panel('options').top;
		if (top > 0) {
			$(mainLayout).layout('full');
		} else {
			$(mainLayout).layout('unFull');
		}
	}
	
	//设置tab展示位置
	function setTabPosition(_position) {
		mainTabs.tabs({ tabPosition : _position});
		SYS.cookieSet('mainTabs_position', _position, {expires: 100});
	}
</script>
</head>
<body id="mainLayout" class="easyui-layout">
	<!-- tab右键菜单 -->
	<div id="main_tab_ctx" class="easyui-menu" style="width:150px;">
		<div id="mm-tabcloseall" >关闭全部</div>
		<div id="mm-tabcloseother">关闭其他标签页</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">关闭右侧标签页</div>
		<div id="mm-tabcloseleft">关闭左侧标签页</div>
	</div>

	<!-- 北部，头部页面 -->
	<div data-options="region:'north',href:'<%=contextPath%>/pages/north.jsp'" style="height: 70px; overflow: hidden;" class="logo"></div>
	
	<!-- 西部，菜单页 -->
	<div data-options="region: 'west', title: '菜单导航栏', iconCls: 'icon-standard-map', split: true, minWidth: 250, maxWidth: 500" style="width: 250px; padding: 1px;">
            
            <!-- 菜单页用到的工具条 -->
            <div id="navTabs_tools" class="tabs-tool">
                <table>
                    <tr>
                        <td><a id="navMenu_refresh" class="easyui-linkbutton easyui-tooltip" title="刷新该选项卡及其导航菜单" data-options="plain: true, iconCls: 'ext-icon-arrow_refresh'"></a></td>
                    </tr>
                </table>
            </div>
            
            <!-- MainTab页用到的工具条 -->
            <div id="mainTabs_tools" class="tabs-tool">
               <!--  data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'" -->
            	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_center_tabmbMenu'">选项卡设置</a>
            </div>
            <div id="layout_center_tabmbMenu" style="width: 100px; display: none;">
				<div data-options="iconCls:'ext-icon-cust_arrow-inout'" onclick="toggleFullScreen();">全屏切换选项卡</div>
				<div data-options="iconCls:'ext-icon-arrow_up'" onclick="setTabPosition('top');">选项卡停靠在上面</div>
				<div data-options="iconCls:'ext-icon-arrow_left'" onclick="setTabPosition('left');">选项卡停靠在左侧</div>
				<div data-options="iconCls:'ext-icon-arrow_down'" onclick="setTabPosition('bottom');">选项卡停靠在底部</div>
				<div data-options="iconCls:'ext-icon-arrow_right'" onclick="setTabPosition('right');">选项卡停靠在右侧</div>
			</div>
            
            <!-- 导航菜单与个人收藏 -->
            <div id="navTabs" class="easyui-tabs" data-options="fit: true, border: true, tools: '#navTabs_tools'">
                <div data-options="title: '导航菜单', iconCls: 'ext-icon-application_view_tile', refreshable: false, selected: true">
                    <ul id="mainMenu_Tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
                </div><!-- 
                <div data-options="title: '个人收藏', iconCls: 'ext-icon-cust_favon', refreshable: true">
                 	<ul id="favoMenu_Tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
                </div> -->
            </div>
            
    </div>
	
	<!-- 中间，主页 -->
	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="mainTabs">
			<div title="首页" data-options="iconCls:'ext-icon-heart'" style="margin: 2px; padding: 2px;" >
				<!-- <div style="font-size: 35pt;font-family: margin-top: 50px;" align="center">欢迎使用黔沪青年管理平台</div> -->
				<DIV style="FONT-SIZE: 35pt; FILTER: shadow(color=#AF0530); WIDTH: 100%; COLOR: #f90b46; LINE-HEIGHT: 150%; FONT-FAMILY: 隶书 ;margin-top: 100px;" align="center"><B>欢迎使用黔沪青年管理平台</B></DIV> 
				<!-- <div class="easyui-panel" title=""     
				        style="width:500px;height:150px;padding:10px;background:#fafafa;"   
				        data-options="closable:true, border:false,  
				                collapsible:true,maximizable:true">   
				    <p>panel content.</p>   
				    <p>panel content.</p>   
				</div> -->
			</div>
		</div>
	</div>
</body>
</html>
