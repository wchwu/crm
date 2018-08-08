window.SYS = window.SYS || {};

// 设置全局 AJAX 默认选项。
$.ajaxSetup({
	type : "POST",
	error : function(jqXHR, textStatus, errorThrown) {
		var opts = {
			title : '错误',
			content : jqXHR.responseText + errorThrown,
			resizable : true,
			width : '70%',
			height : '60%'
		};
		top.SYS.modalDialog(opts);
		top.$.messager.progress('close');// 放置报错时该进度遮盖在页面上
		/*
		 * switch (jqXHR.status){ case(500): top.SYS.modalDialog(opts);
		 * alert("服务器系统内部错误"); break; case(401): alert("未登录"); break; case(403):
		 * alert("无权限执行此操作"); break; case(408): alert("请求超时"); break; default:
		 * alert("未知错误"); }
		 */
	}
});

/**
 * 通用错误提示 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
SYS.onLoadError = {
	onLoadError : function(XMLHttpRequest) {
		top.$.messager.progress('close');
		top.SYS.modalDialog({
			title : '错误',
			content : XMLHttpRequest.responseText,
			resizable : true,
			width : '70%',
			height : '60%'
		});
	}
};
$.extend($.fn.datagrid.defaults, SYS.onLoadError);
$.extend($.fn.treegrid.defaults, SYS.onLoadError);
$.extend($.fn.tree.defaults, SYS.onLoadError);
$.extend($.fn.combogrid.defaults, SYS.onLoadError);
$.extend($.fn.combobox.defaults, SYS.onLoadError);
$.extend($.fn.form.defaults, SYS.onLoadError);

/**
 * 扩展validatebox，添加新的验证功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	},
	neqPwd: {
		validator : function(value, param) {
			return value != $(param[0]).val();
		},
		message : '新密码不能与原始密码相同！'
	},
	maxTarget: {
		validator : function(value, param) {
			return +(value) <= +($(param[0]).val());
		},
		message : '起始值不能大于结束值！'
	},minTarget: {
		validator : function(value, param) {
			return +(value) >= +($(param[0]).val());
		},
		message : '结束值不能小于起始值！'
	}
});

/**
 * 扩展tree/combotree，使其支持平滑数据格式,指定parentField
 * 
 * @example $('#formId').tree({url: 'xxx', parentField : 'pid'})
 * @requires jQuery,EasyUI
 */
SYS.loadFilter = {
	loadFilter : function(data, parent) {
		var opt = $(this).data().tree.options;
		var idField, textField, parentField;
		if (opt.parentField) {
			idField = opt.idField || 'id';
			textField = opt.textField || 'text';
			parentField = opt.parentField || 'pid';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]]
						&& data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textField];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textField];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	}
};
$.extend($.fn.combotree.defaults, SYS.loadFilter);
$.extend($.fn.tree.defaults, SYS.loadFilter);

/**
 * combobox 自动调整高度
 * 
 * @example $('#combo').combobox('autoHeight');
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combobox.methods, {
	autoHeight : function(jq) {// combobox扩展，自动调整高度
		var $this = jq;
		var $panel = $this.data("combo").panel;
		var panelHeight = $this.data("combo").options.panelHeight;
		$panel.height("inherit");
		if ($panel.outerHeight() >= panelHeight) {
			$panel.outerHeight(panelHeight);
		}
	}
});

/**
 * 占用了 onBeforeLoad
 * 方法，来实现业务字典的默认参数设置.所以在使用combobox不能在传入了dictTypeId的情况下再定义它的onBeforeLoad方法 auth:
 * orh
 * 
 * @requires jQuery,EasyUI
 */
$.fn.combobox.defaults.onBeforeLoad = function(param) {
	var opts = $(this).data().combobox.options;

	if (opts.dictTypeId) {
		// 设置业务字典查询下拉的url地址、以及取值字段
		opts.url = SYS.contextPath + '/sys/dict/entryView.action?dictTypeId='
				+ opts.dictTypeId;
		opts.valueField = 'dictId';
		opts.textField = 'dictName';
		if (undefined != opts.defaultIndex) {
			opts.onLoadSuccess = function() {
				var data = $(this).combobox('getData');
				if (undefined != data[opts.defaultIndex]) {
					$(this).combobox('select', data[opts.defaultIndex].dictId); // 需要默认选中第一个
					$(this).combobox('autoHeight'); // 自动调整高度
				}
			};
		}
	}
};

/**
 * 解决Rownumber显示不全，来自(http://www.jeasyuicn.com/easyui-datagrid-extended-fixrownumber-method.html)
 * 设置成显示Rownumber的时候,如果Rownumber越来越大,达到三位数或者四位数的时候,Rownumber就显示不全
 * 
 * @example $("#easyui-datagrid").datagrid({ onLoadSuccess : function () {
 *          $(this).datagrid("fixRownumber"); } });
 */
$.extend($.fn.datagrid.methods, {
	fixRownumber : function(jq) {
		return jq.each(function() {
			var panel = $(this).datagrid("getPanel");
			// 获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			// 由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			// 默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				// 多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel)
						.width(width + 5);
				// 修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				// 一些清理工作
				clone.remove();
				clone = null;
			} else {
				// 还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel)
						.removeAttr("style");
			}
		});
	}
});

/**
 * Easyui Layout Center 全屏方法扩展
 * (来自http://www.jeasyuicn.com/extended-easyui-layout-center-full-screen-method.html)
 * 
 * @example $("").layout("unFull"); $("").layout("full");
 */
$.extend($.fn.layout.methods, {
	full : function(jq) {
		return jq.each(function() {
			var layout = $(this);
			var center = layout.layout('panel', 'center');
			center.panel('maximize');
			center.parent().css('z-index', 10);

			$(window).on('resize.full', function() {
				layout.layout('unFull').layout('resize');
			});
		});
	},
	unFull : function(jq) {
		return jq.each(function() {
			var center = $(this).layout('panel', 'center');
			center.parent().css('z-index', 'inherit');
			center.panel('restore');
			$(window).off('resize.full');
		});
	}
});

/**
 * 编辑单个单元格，来自demo/datagrid/celledting.html
 */
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

/**
 * 扩展jQuery easyui datagrid增加动态改变列编辑的类型 来自：http://www.jeasyuicn.com/post-83.html
 * 注：两个方法，第二个参数都可以传递数组
 * 
 * @example $("#gridId").datagrid('addEditor', { field : 'password', editor : {
 *          type : 'validatebox', options : { required : true } } });
 * @example $("#gridid").datagrid('removeEditor', 'password');
 * 
 */
$.extend($.fn.datagrid.methods, {
	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor;
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	}
});

SYS.combo = SYS.combo || {};
SYS.combo.initClear = function(target) {
	var jq = $(target);
	var opts = jq.data('combo').options;
	var combo = jq.data('combo').combo;
	var arrow = combo.find('span.combo-arrow');

	var clear = arrow.siblings("span.combo-clear");
	if (clear.size() == 0) {
		// 创建清除按钮。
		clear = $('<span class="combo-clear" style="height: 20px;"></span>');

		// 清除按钮添加悬停效果。
		clear.unbind("mouseenter.combo mouseleave.combo").bind(
				"mouseenter.combo mouseleave.combo",
				function(event) {
					var isEnter = event.type == "mouseenter";
					clear[isEnter ? 'addClass' : 'removeClass']
							("combo-clear-hover");
				});
		// 清除按钮添加点击事件，清除当前选中值及隐藏选择面板。
		clear.unbind("click.combo").bind("click.combo", function() {
			jq.combo("setValue", "").combo("setText", "");
			jq.combo('hidePanel');
		});
		arrow.before(clear);
	}
	;
	var input = combo.find("input.combo-text");
	input.outerWidth(input.outerWidth() - clear.outerWidth());

	opts.initClear = true;// 已进行清除按钮初始化。
};

SYS.combo.oldResize = $.fn.combo.methods.resize;

//扩展easyui combo添加清除当前值。
$.extend($.fn.combo.methods, {
	initClear : function(jq) {
		return jq.each(function() {
			initClear(this);
		});
	},
	resize : function(jq) {
		// 调用默认combo resize方法。
		var returnValue = SYS.combo.oldResize.apply(this, arguments);
		var opts = jq.data("combo").options;
		if (opts.initClear) {
			jq.combo("initClear", jq);
		}
		return returnValue;
	}
});

/**
 * datagrid返回记录为0时显示“没有记录”
 * 来自(http://www.jeasyuicn.com/datagrid-returns-for-a-record-0-time-display-no-record.html)
 * 
 * @example $('#dg').datagrid({ view: myview, emptyMsg: 'no records found' });
 */
SYS.datagrid = SYS.datagrid || {};
SYS.datagrid.view = $.extend({}, $.fn.datagrid.defaults.view, {
	onAfterRender : function(target) {
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
		var opts = $(target).datagrid('options');
		var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		vc.children('div.datagrid-empty').remove();
		if (!$(target).datagrid('getRows').length) {
			var d = $('<div class="datagrid-empty"></div>').html(
					opts.emptyMsg || 'no records').appendTo(vc);
			d.css({
				position : 'absolute',
				left : 0,
				top : 30,
				width : '100%',
				textAlign : 'center'
			});
		}
	}
});
