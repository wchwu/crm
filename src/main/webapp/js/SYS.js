window.SYS = window.SYS || {};

/**
 * 日期对象转换为指定格式的字符串
 * @param f 日期格式,格式定义如下 yyyy-MM-dd HH:mm:ss
 * @param date Date日期对象, 如果缺省，则为当前时间
 *
 * YYYY/yyyy/YY/yy 表示年份  
 * MM/M 月份  
 * W/w 星期  
 * dd/DD/d/D 日期  
 * hh/HH/h/H 时间  
 * mm/m 分钟  
 * ss/SS/s/S 秒  
 * @return string 指定格式的时间字符串
 */
SYS.dateToStr = function(formatStr, date) {
	//设置默认值
	formatStr = arguments[0] || "yyyy-MM-dd HH:mm:ss";
	date = arguments[1] || new Date();
	
	var str = formatStr,
		Week = [ '日', '一', '二', '三', '四', '五', '六' ],
		fullYear = date.getFullYear(),
		year = date.getYear(),
		month = date.getMonth(),
		day = date.getDay(),
		days = date.getDate(),
		hours = date.getHours(),
		minutes = date.getMinutes(),
		second = date.getSeconds();
	
	str = str.replace(/yyyy|YYYY/, fullYear);
	str = str.replace(/yy|YY/,
			(year % 100) > 9 ? (year % 100).toString()
					: '0' + (year % 100));
	str = str.replace(/MM/, month >= 9 ? (month + 1) : '0'
			+ (month + 1));
	str = str.replace(/M/g, month);
	str = str.replace(/w|W/g, Week[day]);

	str = str.replace(/dd|DD/, days > 9 ? days.toString()
			: '0' + days);
	str = str.replace(/d|D/g, days);

	str = str.replace(/hh|HH/, hours > 9 ? hours.toString()
			: '0' + hours);
	str = str.replace(/h|H/g, hours);
	str = str.replace(/mm/, minutes > 9 ? minutes
			.toString() : '0' + minutes);
	str = str.replace(/m/g, minutes);

	str = str.replace(/ss|SS/, second > 9 ? second
			.toString() : '0' + second);
	str = str.replace(/s|S/g, second);

	return str;
}

SYS.dateStr8To10Str = function(dateStr8, spFlag) {
	spFlag = spFlag || '-';
	return dateStr8.substr(0,4) + spFlag + dateStr8.substr(4,2) + spFlag + dateStr8.substr(6,2);
};

/**
 * 字符串格式化输出
 * 
 * @author orh.sh
 * @example document.write(SYS.strFormat('<b>{0}</b>,<i>{1}<i>',hi:
 *          {0},'poxi','到此一游'));
 */
SYS.strFormat = function(template) {
	var args = arguments;
	return template.replace(/\{(\d+)\}/g, function($, $1) {
		return args[++$1];
	});
};

/**
 * 字符串格式化输出,json对象
 * 
 * @author orh.sh
 * @example var json = [{text: '亚马逊',url: 'z.cn'}]; document.write(jsonFormat('<a
 *          href="${url}">${text}</a>'));
 */
SYS.jsonFormat = function(template, json) {
	return template.replace(/\$\{(.+?)\}/g, function($, $1) {
		return json[$1];
	});
};

/**
 * cookie 设值
 * @param key cookie 键
 * @param value cookie值
 * @param options 配置选项
 * @returns
 */
SYS.cookieSet = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [ encodeURIComponent(key), '=', options.raw ? String(value) : encodeURIComponent(String(value)), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

/**
 * cookie 取值
 * @param key cookie键
 */
SYS.cookieGet = function(key) {
	if (document.cookie.length > 0){	//有存cookie
		c_start = document.cookie.indexOf(key + "=")
		if (c_start != -1) {	//cookie有存在该值
			c_start = c_start + key.length + 1 
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1){
				c_end = document.cookie.length
				return unescape(document.cookie.substring(c_start, c_end))
			}
		} 
	}
	return undefined;
};

/**
 * 更换主题
 * @requires jQuery,EasyUI
 */
SYS.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	SYS.cookieSet('easyuiTheme', themeName, {
		expires : 7
	});
};
/**
 * 返回字符串是否以特定字符串什么开始
 * 
 * @author orh.sh
 * @param {String}
 *            str 原字符串
 * @param {String}
 *            pattern 特定字符串
 * @returns {Boolean}
 */
SYS.startWith = function(str, pattern) {
	var reg = new RegExp("^" + pattern);
	return reg.test(str);
};

/**
 * 返回字符串是否以特定字符串什结尾
 * 
 * @author orh.sh
 * @param {String}
 *            str 原字符串
 * @param {String}
 *            pattern 特定字符串
 * @returns {Boolean}
 */
SYS.endWith = function(str, pattern) {
	var reg = new RegExp(pattern + "$");
	return reg.test(str);
};

/**
 * 解析URLs (来自http://www.jeasyuicn.com/using-dom-analysis-urls.html)
 */
SYS.parseURL = function(url) {
	var a = document.createElement('a');
	a.href = url;
	return {
		source : url,
		protocol : a.protocol.replace(':', ''),
		host : a.hostname,
		port : a.port,
		query : a.search,
		params : (function() {
			var ret = {}, seg = a.search.replace(/^\?/, '').split('&'), len = seg.length, i = 0, s;
			for (; i < len; i++) {
				if (!seg[i]) {
					continue;
				}
				s = seg[i].split('=');
				ret[s[0]] = s[1];
			}
			return ret;
		})(),
		file : (a.pathname.match(/\/([^\/?#]+)$/i) || [ , '' ])[1],
		hash : a.hash.replace('#', ''),
		path : a.pathname.replace(/^([^\/])/, '/$1'),
		relative : (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [ , '' ])[1],
		segments : a.pathname.replace(/^\//, '').split('/')
	};
};

/**
 * 将form表单元素的值序列化成对象
 * 
 * @example SYS.serializeObject($('#formId'))
 */
SYS.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (this['value'] != undefined && this['value'].length > 0) { // 如果表单项的值非空，才进行序列化操作
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		}
	});
	return o;
};

/**
 * 设置iframe高度
 */
SYS.setIframeHeight = function(iframe, height) {
	iframe.height = height;
};

/**
 * iframe自适应高度
 */
SYS.autoIframeHeight = function(iframe) {
	iframe.style.height = iframe.contentWindow.document.body.scrollHeight
			+ "px";
};
/**
 * 创建一个模式化的dialog:可在iframe页方便的关闭dialog和刷新父页面元素
 * 
 * @requires jQuery,EasyUI
 * 
 */
SYS.modalDialog = function(options) {
	var cTime = new Date().getTime(), dialogId = 'dialog_' + cTime, frameId = 'dialog_frame_'
			+ cTime, dialogDiv = '<div id="' + dialogId + '"></div>';
	var opts = $.extend({
		title : ' ',
		width : 640,
		height : 480,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true; // 强制此dialog为模式化，无视传递过来的modal参数
	if (options.url) {
		opts.content = '<iframe name="'
				+ cTime
				+ '" id="'
				+ frameId
				+ '" src="'
				+ options.url
				+ '" allowTransparency="true" style="overflow:auto" scrolling="auto" width="100%" height="98%" frameBorder="0" ></iframe>';
	}
	return $(dialogDiv).dialog(opts);
};
