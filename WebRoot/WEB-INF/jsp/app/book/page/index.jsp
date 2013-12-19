<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String bookID = request.getParameter("bookID");
%>
<script type="text/javascript">
	//Add and Edit
	var bookID = "<%=bookID%>";
	function appBookPageGridAddAndEdit(title, url, type) {
		if (type == 1) { //edit
			var rows = $('#appBookPageGrid').datagrid('getSelections');
			if (rows.length != 1) {
				$.messager.alert('消息', '请钩择一行数据!', 'info');
				return;
			}
		}
		$('<div/>').dialog({
			href : '${demoPath}app/book/page/baseDlg.html',
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
					appBookPageGridSubmit(url);
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
					$('#appBookPageFm').form('clear');
					$('#bookID').val("<%=bookID%>");
				} else {
					var rows = $('#appBookPageGrid').datagrid('getSelections');
					if (rows.length == 1) {
						$('#appBookPageFm').form('load', rows[0]);
					} else {
						$.messager.alert('消息', '请钩择一行数据!', 'info');
					}
				} 
			}
		});  
	}

	function appBookPageManage(){

		var rows = $('#appBookPageGrid').datagrid('getSelections');
		if (rows.length != 1) {
			$.messager.alert('消息', '请钩择一行数据!', 'info');
			return;
		}
		$('<div/>').dialog({
			href : '${demoPath}app/book/page/pageManage.html?bookPageID='+rows[0].id+'&type='+rows[0].type,
			modal : true,
			title : "页面内容编辑",
			top : '15%',
			left : '30%', 
			width : 700,
			height : 500,
			resizable:true,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					 
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
				 
			}
		});  
		
	}
	
	
	//Del
	function appBookPageGridDel() {
		var rows = $('#appBookPageGrid').datagrid('getSelections');
		if (rows.length > 0) {
		    var ids = '';
			for ( var i = 0; i < rows.length; i++) {
						ids += 'ids=' + rows[i].id + '&';
			}
			ids = ids.substring(0, ids.length - 1);
			var url = '${demoPath}app/book/page/del.html?' + ids;
			$.messager.confirm('Confirm','确定要删除选择的数据吗?', function(r) {
				if (r) {
				    $.get(url, function(result){
						if (result.success){ 
							$('#appBookPageGrid').datagrid('reload');
							$('#appBookPageGrid').datagrid('clearSelections');
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
	function appBookPageGridReload() {
		$('#appBookPageGrid').datagrid('options').pageNumber=1;
		$('#appBookPageGrid').datagrid('reload',{});
	}
	
	//appBookPageGridSubmit  submit
	function appBookPageGridSubmit(url) {
	    $('#appBookPageFm').form('submit',{  
	        url: url,  
	        onSubmit: function(){  
	            return $(this).form('validate');  
	        },  
	        success: function(result){  
	            var result = eval('('+result+')');  
	            if (result.success){  
	                $('#appBookPageDlg').dialog('close');      // close the dialog 
					$('#appBookPageGrid').datagrid('reload');    // reload the data
	            } else {  
	            	$.messager.show({ title: 'Error',msg: result.msg }); 
	            }  
	        }  
	    }); 
	}
	  
	 
	//导出
	function appBookPageGridExport(){
		window.location="${demoPath}app/book/page/export.html";
	}
	
</script> 

	<!-- 中  datagrid-->
    <div data-options="region:'center',border : false"     >
		<!-- datagrid toolbar -->
				<table id="appBookPageGrid"  class="easyui-datagrid"   data-options="	
					url:'${demoPath}app/book/page/data.html',
					queryParams: {
						bookID: '<%=bookID%>',
					},
					frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
					columns:[ [  
		
					{field:'id',title:'主键',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}},			
		
					{field:'title',title:'标题',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}}, 
					{field:'type',title:'类型',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    if(value=='0'){
			    			value='跟读类';
					    } 
					    return value;
					}}, 
					{field:'img',title:'封面',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
						if(value==''){
					    	value='';
					    }else
					    {
					     var  src = '${demoPath}openDownloadFile.html?isOnLine=true&filePath='+value;
					   	 value='<img  height=100px  src='+src+' ></img>';
					    } 
					    return value;
					}},	
					{field:'sort',title:'排序',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}}  		
		
					] ],
					toolbar:'#appBookPageGridToolbar'
				"/></table>
				<!-- datagrid toolbar -->
				<div id="appBookPageGridToolbar">
					<div style="margin-bottom:5px;margin-top: 5px"  >
						<div style="float: left"> 
							<a href="javascript:void(0)" onclick="javascript:appBookPageGridAddAndEdit('添加书籍页面','${demoPath}app/book/page/add.html',0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageGridAddAndEdit('修改书籍页面','${demoPath}app/book/page/save.html',1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑 </a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageManage()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">页面内容 </a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageGridDel()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageGridReload()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPageGridExport()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">导出</a>
						</div>
						<div style="float: left;padding-top: 3px;padding-left: 10px;">
							<input    class="easyui-searchbox" data-options="
								menu :'#appBookPageGridToolbarSearch',
								prompt :'模糊查询',
								searcher : function(value,name){
									var str='{searchType:1,'+name+':\''+value+'\'}';
							        var obj = eval('('+str+')');
							        $('#appBookPageGrid').datagrid('options').pageNumber=1;
									$('#appBookPageGrid').datagrid('reload',obj);
								}
							"/>
						</div>
						<div id="appBookPageGridToolbarSearch">
							<div name="id">主键</div>
							<div name="title">标题</div> 
						</div>
						<div style="clear: both;"></div>
					</div>
				</div>
	</div>
	
	 
		 