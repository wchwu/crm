function isEmpty(str) {
	return (str == null || str === "" || str == "undefined" || str == "null");
}

function null2Str(str) {
	if (isEmpty(str)) {
		return "";
	}
	return str;
}

function ww4(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var h = date.getHours();
	return y + (m < 10 ? ('0' + m) : m) + (d < 10 ? ('0' + d) : d);

}
function w4(s) {
	var reg = /[\u4e00-\u9fa5]/ // 利用正则表达式分隔
	var ss = (s.split(reg));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

//---------------------------------------------date:YYYYMMDD  start--------------------------------------------------------------
function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '' + (m < 10 ? ('0' + m) : m) + '' + (d < 10 ? ('0' + d) : d);
}

function myparser(s) {
	if (!s)
		return new Date();
//	var ss = (s.split('-'));
//	var y = parseInt(ss[0], 10);
//	var m = parseInt(ss[1], 10);
//	var d = parseInt(ss[2], 10);
	var y = parseInt(s.substring(0,4), 10);
	var m = parseInt(s.substring(4,6), 10);
	var d = parseInt(s.substring(6,8), 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}
//---------------------------------------------date:YYYYMMDD  end--------------------------------------------------------------

//---------------------------------------------date:YYYYMM  start--------------------------------------------------------------
function myformatter6(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '' + (m < 10 ? ('0' + m) : m);
}

function myparser6(s) {
	if (!s)
		return new Date();
	var y = parseInt(s.substring(0,4), 10);
	var m = parseInt(s.substring(4,6), 10);
	var d = parseInt(s.substring(6,8), 10);
	if (!isNaN(y) && !isNaN(m)) {
		return new Date(y, m - 1);
	} else {
		return new Date();
	}
}
//---------------------------------------------date:YYYYMM  end--------------------------------------------------------------

function getMemberId(){
	var memberId = 0;
	$.ajax({
		type : 'GET',
		url : SYS.contextPath + '/member/getMemberId.action',
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data.success == '0'){
				memberId = data.memberId;
			}
		}
	});
	return memberId ;
}


function getRuleId(){
	var ruleId = 0;
	$.ajax({
		type : 'GET',
		url : SYS.contextPath + '/rule/getRuleId.action',
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data.success == '0'){
				ruleId = data.ruleId;
			}
		}
	});
	return ruleId ;
}