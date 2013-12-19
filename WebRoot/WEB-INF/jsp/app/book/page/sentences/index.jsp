<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String bookPageID = request.getParameter("bookPageID");
%>
<script type="text/javascript">
	//Add and Edit
	var bookPageID = "<%=bookPageID%>";
	function appBookPageSentencesGridAddAndEdit(title, url, type) {
		if (type == 1) { //edit
			var rows = $('#appBookPageSentencesGrid').datagrid('getSelections');
			if (rows.length != 1) {
				$.messager.alert('消息', '请钩择一行数据!', 'info');
				return;
			}
		}
		$('<div/>').dialog({
			href : '${demoPath}app/book/page/sentences/baseDlg.html',
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
					appBookPageSentencesGridSubmit(url);
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
				
				if (type == 0) {
					$('#appBookPageSentencesFm').form('clear');
					$('#bookPageID').val("<%=bookPageID%>");
				} else {
					var rows = $('#appBookPageSentencesGrid').datagrid('getSelections');
					if (rows.length == 1) {
						$('#appBookPageSentencesFm').form('load', rows[0]);
						$("audio").iWish({audioSource: "${demoPath}/upload/"+rows[0].audio, autoPlay: false});
					} else {
						$.messager.alert('消息', '请钩择一行数据!', 'info');
					}
				} 
			}
		});  
	}

	 
	
	
	//Del
	function appBookPageSentencesGridDel() {
		var rows = $('#appBookPageSentencesGrid').datagrid('getSelections');
		if (rows.length > 0) {
		    var ids = '';
			for ( var i = 0; i < rows.length; i++) {
						ids += 'ids=' + rows[i].id + '&';
			}
			ids = ids.substring(0, ids.length - 1);
			var url = '${demoPath}app/book/page/sentences/del.html?' + ids;
			$.messager.confirm('Confirm','确定要删除选择的数据吗?', function(r) {
				if (r) {
				    $.get(url, function(result){
						if (result.success){ 
							$('#appBookPageSentencesGrid').datagrid('reload');
							$('#appBookPageSentencesGrid').datagrid('clearSelections');
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
	function appBookPageSentencesGridReload() {
		$('#appBookPageSentencesGrid').datagrid('options').pageNumber=1;
		$('#appBookPageSentencesGrid').datagrid('reload',{});
	}
	
	//appBookPageSentencesGridSubmit  submit
	function appBookPageSentencesGridSubmit(url) {
	    $('#appBookPageSentencesFm').form('submit',{  
	        url: url,  
	        onSubmit: function(){  
	            return $(this).form('validate');  
	        },  
	        success: function(result){  
	            var result = eval('('+result+')');  
	            if (result.success){  
	                $('#appBookPageSentencesDlg').dialog('close');      // close the dialog 
					$('#appBookPageSentencesGrid').datagrid('reload');    // reload the data
	            } else {  
	            	$.messager.show({ title: 'Error',msg: result.msg }); 
	            }  
	        }  
	    }); 
	}
	  
	 
	//导出
	function appBookPageSentencesGridExport(){
		window.location="${demoPath}app/book/page/sentences/export.html";
	}
	
</script> 

	<!-- 中  datagrid-->
    <div data-options="region:'center',border : false"   width="500px" height="300px"   >
		<!-- datagrid toolbar -->
				<table id="appBookPageSentencesGrid"  class="easyui-datagrid"  width="500px" height="300px"   data-options="	
					url:'${demoPath}app/book/page/sentences/data.html',
					queryParams: {
						bookPageID: '<%=bookPageID%>',
					},
					frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
					columns:[ [  
		
					{field:'id',title:'主键',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}},			
		
					{field:'sentence',title:'句子',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}},  	
					{field:'sort',title:'排序',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}}  		
		
					] ],
					toolbar:'#appBookPageSentencesGridToolbar'
				"/></table>
				<!-- datagrid toolbar -->
				<div id="appBookPageSentencesGridToolbar">
					<div style="margin-bottom:5px;margin-top: 5px"  >
						<div style="float: left"> 
							<a href="javascript:void(0)" onclick="javascript:appBookPageSentencesGridAddAndEdit('添加句子','${demoPath}app/book/page/sentences/add.html',0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageSentencesGridAddAndEdit('修改句子','${demoPath}app/book/page/sentences/save.html',1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑 </a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageSentencesGridDel()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageSentencesGridReload()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageSentencesGridExport()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">导出</a>
						</div>
						<div style="float: left;padding-top: 3px;padding-left: 10px;">
							<input    class="easyui-searchbox" data-options="
								menu :'#appBookPageSentencesGridToolbarSearch',
								prompt :'模糊查询',
								searcher : function(value,name){
									var str='{searchType:1,'+name+':\''+value+'\'}';
							        var obj = eval('('+str+')');
							        $('#appBookPageSentencesGrid').datagrid('options').pageNumber=1;
									$('#appBookPageSentencesGrid').datagrid('reload',obj);
								}
							"/>
						</div>
						<div id="appBookPageSentencesGridToolbarSearch">
							<div name="id">主键</div>
							<div name="sentence">标题</div> 
						</div>
						<div style="clear: both;"></div>
					</div>
				</div>
	</div>
	
	 
		 