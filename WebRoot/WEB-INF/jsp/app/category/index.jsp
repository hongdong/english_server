<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	//Add and Edit
	function appCategoryGridAddAndEdit(title, url, type) {
		if (type == 1) { //edit
			var rows = $('#appCategoryGrid').datagrid('getSelections');
			if (rows.length != 1) {
				$.messager.alert('消息', '请钩择一行数据!', 'info');
				return;
			}
		}
		$('<div/>').dialog({
			href : '${demoPath}app/category/baseDlg.html',
			modal : true,
			title : title,
			top : '15%',
			left : '30%', 
			width : 600,
			resizable:true,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					appCategoryGridSubmit(url);
					$(this).closest('.window-body').dialog('destroy');
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$(this).closest('.window-body').dialog('destroy');
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				if (type != 0) {
					var rows = $('#appCategoryGrid').datagrid('getSelections');
					if (rows.length == 1) {
						$('#appCategoryFm').form('load', rows[0]);
					} else {
						$.messager.alert('消息', '请钩择一行数据!', 'info');
					}
				} 
			}
		});  
	}
	
	//Del
	function appCategoryGridDel() {
		var rows = $('#appCategoryGrid').datagrid('getSelections');
		if (rows.length > 0) {
		    var ids = '';
			for ( var i = 0; i < rows.length; i++) {
						ids += 'ids=' + rows[i].id + '&';
			}
			ids = ids.substring(0, ids.length - 1);
			var url = '${demoPath}app/category/del.html?' + ids;
			$.messager.confirm('Confirm','确定要删除选择的数据吗?', function(r) {
				if (r) {
				    $.get(url, function(result){
						if (result.success){ 
							$('#appCategoryGrid').datagrid('reload');
							$('#appCategoryGrid').datagrid('clearSelections');
						} else {  
						    $.messager.show({ title: 'Error', msg : result.msg }); 
						} 
				    }, 'json');
				}
			});
		} else {
			$.messager.alert('消息', '请选择要删除的数据!','info');
		}
	}
	
	//Reload
	function appCategoryGridReload() {
		$('#appCategoryGrid').datagrid('options').pageNumber=1;
		$('#appCategoryGrid').datagrid('reload',{});
	}
	
	//appCategoryGridSubmit  submit
	function appCategoryGridSubmit(url) {
	    $('#appCategoryFm').form('submit',{  
	        url: url,  
	        onSubmit: function(){  
	            return $(this).form('validate');  
	        },  
	        success: function(result){  
	            var result = eval('('+result+')');  
	            if (result.success){  
	                $('#appCategoryDlg').dialog('close');      // close the dialog 
					$('#appCategoryGrid').datagrid('reload');    // reload the data
	            } else {  
	            	$.messager.show({ title: 'Error',msg: result.msg }); 
	            }  
	        }  
	    }); 
	}
	  
	 
	//导出
	function appCategoryGridExport(){
		window.location="${demoPath}app/category/export.html";
	}
	
</script>
    

	<!-- 中  datagrid-->
    <div data-options="region:'center',border : false">
		<!-- datagrid toolbar -->
		<table id="appCategoryGrid"  class="easyui-datagrid"  data-options="	
			url:'${demoPath}app/category/data.html',
			frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
			columns:[ [  

			{field:'id',title:'主键',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'name',title:'类型名称',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}} 		
			] ],
			toolbar:'#appCategoryGridToolbar'
		"/>
		
		<!-- datagrid toolbar -->
		<div id="appCategoryGridToolbar">
			<div style="margin-bottom:5px;margin-top: 5px">
				<div style="float: left">
					<c:forEach items="${buttons}" var="button">
				         ${button}
				    </c:forEach>
					<!--  
					<a href="javascript:void(0)" onclick="javascript:appCategoryGridAddAndEdit('添加  appCategory','${demoPath}app/category/add.html',0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
					<a href="javascript:void(0)" onclick="javascript:appCategoryGridAddAndEdit('修改  appCategory','${demoPath}app/category/save.html',1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑 </a>  
					<a href="javascript:void(0)" onclick="javascript:appCategoryGridDel()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
					<a href="javascript:void(0)" onclick="javascript:appCategoryGridReload()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
					<a href="javascript:void(0)" onclick="javascript:appCategoryGridExport()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">导出</a>
					-->
				</div>
				<!-- appCategoryGridToolbarSearch -->
				<div style="float: left;padding-top: 3px;padding-left: 10px;">
					<input class="easyui-searchbox" data-options="
						menu :'#appCategoryGridToolbarSearch',
						prompt :'模糊查询',
						searcher : function(value,name){
							var str='{searchType:1,'+name+':\''+value+'\'}';
					        var obj = eval('('+str+')');
					        $('#appCategoryGrid').datagrid('options').pageNumber=1;
							$('#appCategoryGrid').datagrid('reload',obj);
						}
					"/>
					<div id="appCategoryGridToolbarSearch">
						<div name="id">主键</div>
						<div name="name">类型名称</div> 
					</div>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
<!--  <div>-->