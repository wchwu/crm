<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>积分流水查询</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
<div class="easyui-layout" data-options="fit:true, border:false">  
			<div data-options="region:'north',title:'查询', border:false" style="height: 170px; background: #F4F4F4;">
				<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
					<table class="table" style="width: 100%;">
						<tbody>
							<tr>
								<th>开始时间</th>
								<td>
									<input class="Wdate " id="begintime" name="begintime" />
								</td>
								<th>结束时间</th>
								<td><input class="Wdate " id="endtime" name="endtime" /></td>
							</tr>
							<tr>
								<th>积分类型</th>
								<td>
									<select name="scoretype" style="width: 200px;" id="scoretype" class="easyui-combobox" data-options="dictTypeId: 'SC_BUSI_TYPE'">
									</select>
								</td>
								<th>积分来源</th>
								<td>
									<select id="scoresource" name="scoresource"  style="width: 200px;" class="easyui-combobox" >
									</select>
								</td>
							</tr>
							<tr>
								<th>客户姓名</th>
								<td>
									<input class="asyui-validatebox validatebox-text" id="custname" name="custname" />
								</td>
								<th>证件号码</th>
								<td><input class="asyui-validatebox validatebox-text" id="certno" name="certno" /></td>
							</tr>
							<tr>
								<th>客户号</th>
								<td>
									<input class="asyui-validatebox validatebox-text" id="custid" name="custid" />
								</td>
								<th></th>
								<td></td>
							</tr>
							<tr>
								<th colspan="4" style="text-align: center;">
									<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-score').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
									<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
								</th>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			
			<!-- 积分活动列表 -->
			<div data-options="region:'center',split:false">
				<table id="tb-score"></table>  
			</div>
		</div> 

<div id="setscore"></div>  


<script type="text/javascript">
	
	$("#begintime").focus(function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'});
	});
	$("#endtime").focus(function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\')}'});
	});
	//当前页面变量定义
	var dg_score,
		dg_score_entry;
	$(function() {
		dg_score = $('#tb-score').datagrid({    
			    url: SYS.contextPath + '/score/scoreQuery.action',  
			    iconCls: 'icon-save',
			    view: SYS.datagrid.view,
			    emptyMsg: '没有数据',
			    fit: true,
			    border:false,
			    autoRowHeight: true,
			    fitColumns: true,
			    rownumbers: true,
			    singleSelect:true,
			    toolbar: '#toolbar_dict',
			    idField: 'dictTypeId',
			    columns:[[    
			                {field:'custid', title:'客户编号'},
					        {field:'custname', title:'客户姓名', width:'10%'}, 
					        {field:'certtypename', title:'证件类型', width:'10%'}, 
					        {field:'certno', title:'证件号码', width:'15%'},
					        {field:'sex', title:'性别', width:'5%',formatter: function(value,row){ return value == 1 ?'男':'女'; }},
					        {field:'businame', title:'积分来源', width:'10%'},
					        {field:'scval', title:'变化情况', width:'15%',formatter: function(value,row){ return row.scinout == 1 ?'+'+value:'-'+value; }},
					        {field:'confdate', title:'操作时间', width:'15%'},
					        {field:'operName', title:'操作人员', width:'15%'},
					        {field:'memo', title:'备注', width:'15%'},
			    ]],
			    pagination: true,
		});
		$("#scoretype").combobox({
			onSelect:function(_prama){
				$("#scoresource").combobox({
					mode: 'remote',
					valueField:'id',
					textField:'name',
					loader:function(prama,success,error){
			           
			            if (_prama.dictId.length < 1){return false}
			            $.ajax({
			                url: SYS.contextPath + '/score/queryBusiCode.action',
			                dataType: 'json',
			                data: {
			                    busitype : _prama.dictId
			                },
			                success: function(data){
			                    var items = $.map(data.rows, function(item){
			                        return {
			                            id: item.busicode,
			                            name: item.businame
			                        };
			                    });
			                    success(items);
			                },
			                error: function(){
			                    error.apply(this, arguments);
			                }
			            });
			        }
				});
				
			}
		});
		$("#scoresource").combobox({
			mode: 'remote',
			valueField:'id',
			textField:'name',
			loader:function(prama,success,error){
	            $.ajax({
	                url: SYS.contextPath + '/score/queryBusiCode.action',
	                dataType: 'json',
	                success: function(data){
	                    var items = $.map(data.rows, function(item){
	                        return {
	                            id: item.busicode,
	                            name: item.businame
	                        };
	                    });
	                    success(items);
	                },
	                error: function(){
	                    error.apply(this, arguments);
	                }
	            });
	        }
		});
	});
</script>
</body>
</html>