(function($) {
	window.mainpage = window.mainpage || {};
	window.mainpage.mainTabs = mainpage.mainTabs ||{};
	window.mainpage.mainLayout = mainpage.mainLayout ||{};

	var mainLayout = "#mainLayout", mainTabs = "#mainTabs", navTabs = "#navTabs", mainMenuTree = "#mainMenuTree", favoMenuTree = "#favoMenu_Tree";
	
	/**
	 * 主Tabs增加Tab
	 */
	mainpage.mainTabs.addTab = function(opts) {
		// 默认参数设置
		var title = opts.title,
			tools = opts.tools || [],
			iconCls = opts.iconCls || '',
			closable = (opts.closable != undefined) ? opts.closable : true,
			refreshable = (opts.refreshable != undefined) ? opts.refreshable : true,
			content = opts.src ? SYS.strFormat('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', opts.src) : content;
		
		// 如果tab已经存在，则选中当前tab
		if ($(mainTabs).tabs('exists', title)) {
			return $(mainTabs).tabs('select', title)
		}
		
		//iframe才可以刷新
		if (refreshable) {
			var refreshTool = {
				iconCls:'icon-mini-refresh',    
		        handler:function(){    
		            mainpage.mainTabs.refreshTab(opts.title);
		        }    
			};
			tools.push(refreshTool);
		}
		
		//主Tab面添加Tab
		$(mainTabs).tabs('add', {
			title : title,
			closable : closable || true,
			iconCls : iconCls,
			content : content,
			tools : tools,
			border : false,
			fit : true
		});
		
	};
	
	/**
	 * 主Tabs刷新Tab
	 * @param which 'which'参数可以是选项卡面板的标题或者索引。不填写which将刷新当前选中的 tab。
	 */
	mainpage.mainTabs.refreshTab = function(which) {
		var mainTab = $("#mainTabs");
		var tab ;
		if (which) {
			tab = mainTab.tabs('getTab', which);
		} else {
			tab = mainTab.tabs('getSelected');
		}
		mainTab.tabs('update', {
			tab: tab,
			options : {content: tab.content}
		});
	};
	
	/**
	 * 主Tabs关闭Tab
	 * @param which 'which'参数可以是选项卡面板的标题或者索引。不填写which将关闭当前选中的 tab。
	 */
	mainpage.mainTabs.closeTab = function(which) {
		var mainTab = $("#mainTabs");
		var tab ;
		if (which) {
			tab = mainTab.tabs('getTab', which);
		} else {
			tab = mainTab.tabs('getSelected');
		}
		tab.tabs('close', tab.title);
	};
})(jQuery);