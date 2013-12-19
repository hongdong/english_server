<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	//Add and Edit
	function appBookGridAddAndEdit(title, url, type) {
		if (type == 1) { //edit
			var rows = $('#appBookGrid').datagrid('getSelections');
			if (rows.length != 1) {
				$.messager.alert('消息', '请钩择一行数据!', 'info');
				return;
			}
		}
		$('<div/>').dialog({
			href : '${demoPath}app/book/baseDlg.html',
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
					appBookGridSubmit(url);
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
					$('#appBookFm').form('clear');
					$('#appBookJoinAppCategory').combogrid({
						url : '${demoPath}app/category/data.html?t='+ Math.random(),
						panelWidth : 460,
						textField : 'name',
						multiple:false,
						columns:[ [  
				                {field:'id',title:'主键',width:100},
				                {field:'name',title:'类型名称',width:100} 
				        ] ]
					});
				} else {
					var rows = $('#appBookGrid').datagrid('getSelections');
					if (rows.length == 1) {
						$('#appBookFm').form('load', rows[0]);
						$('#appBookJoinAppCategory').combogrid({
							url : '${demoPath}app/category/data.html',
							panelWidth : 460,
							textField : 'name',
							multiple:false,
							columns:[ [  
						            {field:'id',title:'主键',width:100},
						            {field:'name',title:'类型名称',width:100}
						    ] ]
						});
						 $("#coverImg")[0].src="${demoPath}openDownloadFile.html?isOnLine=true&filePath="+rows[0].cover;
					} else {
						$.messager.alert('消息', '请钩择一行数据!', 'info');
					}
				}
			}
		});  
	}
	
	//Del
	function appBookGridDel() {
		var rows = $('#appBookGrid').datagrid('getSelections');
		if (rows.length > 0) {
		    var ids = '';
			for ( var i = 0; i < rows.length; i++) {
						ids += 'ids=' + rows[i].id + '&';
			}
			ids = ids.substring(0, ids.length - 1);
			var url = '${demoPath}app/book/del.html?' + ids;
			$.messager.confirm('Confirm','确定要删除选择的数据吗?', function(r) {
				if (r) {
				    $.get(url, function(result){
						if (result.success){ 
							$('#appBookGrid').datagrid('reload');
							$('#appBookGrid').datagrid('clearSelections');
							$('#appBookPageGrid').datagrid('reload');
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

	//Pack
	function appBookPack() {
		var rows = $('#appBookGrid').datagrid('getSelections');
		if (rows.length > 0) {
		    var ids = '';
			for ( var i = 0; i < rows.length; i++) {
						ids += 'ids=' + rows[i].id + '&';
			}
			ids = ids.substring(0, ids.length - 1);
			var url = '${demoPath}app/book/packBook.html?' + ids;
			$.messager.confirm('Confirm','确定要打包选择的数据吗?', function(r) {
				if (r) {
					$('#appBookGrid').datagrid('loading');
				    $.get(url, function(result){
						if (result.success){ 
							$('#appBookGrid').datagrid('reload');
							$('#appBookGrid').datagrid('clearSelections');
						} else {  
						    $.messager.show({ title: 'Error', msg : result.msg }); 
						    $('#appBookGrid').datagrid('reload');
						} 
				    }, 'json');
				}
			});
		} else {
			$.messager.alert('消息', '请选择要打包的数据!','info');
		}
	}
	
	//Reload
	function appBookGridReload() {
		$('#appBookGrid').datagrid('options').pageNumber=1;
		$('#appBookGrid').datagrid('reload',{});
	}
	
	//appBookGridSubmit  submit
	function appBookGridSubmit(url) {
	    $('#appBookFm').form('submit',{  
	        url: url,  
	        onSubmit: function(){  
	            return $(this).form('validate');  
	        },  
	        success: function(result){  
	            var result = eval('('+result+')');  
	            if (result.success){  
	                $('#appBookDlg').dialog('close');      // close the dialog 
					$('#appBookGrid').datagrid('reload');    // reload the data
	            } else {  
	            	$.messager.show({ title: 'Error',msg: result.msg }); 
	            }  
	        }  
	    }); 
	}
	  
	 
	//导出
	function appBookGridExport(){
		window.location="${demoPath}app/book/export.html";
	}
	
</script> 

	<!-- 中  datagrid-->
    <div data-options="region:'center',border : false"  >
		<!-- datagrid toolbar -->
				<table id="appBookGrid"  class="easyui-datagrid" style="height: 300px"   data-options="	
					url:'${demoPath}app/book/data.html',
					frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
					singleSelect:true,
					columns:[ [  
		
					{field:'id',title:'主键',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}},			
		
					{field:'name',title:'书籍名称',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}}, 		 
					{field:'createTime',title:'创建时间',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}},
					{field:'cover',title:'封面',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
						if(value==''){
					    	value='';
					    }else
					    {
					     var  src = '${demoPath}openDownloadFile.html?isOnLine=true&filePath='+value;
					   	 value='<img  height=100px  src='+src+' ></img>';
					    } 
					    return value;
					}},
					{field:'version',title:'版本号',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
					    return value;
					}}	 		
					] ],
					onSelect : function(rowIndex, rowData){
					
						 var currTab = $('#booktab').tabs('getTab', '书籍页面');
						 if(currTab==null)
						 {
							  $('#booktab').tabs('add', {
								border : false,
								title : '书籍页面',
								href : '${demoPath}app/book/page/index.html?bookID='+rowData.id,
								closable : true
								});
						 }
						 else
						 { 
						 
			              $('#booktab').tabs('update', { //修改
			                  tab: currTab,
			                  options: {
			                       href : '${demoPath}app/book/page/index.html?bookID='+rowData.id
			                  }
			              });
			              
						}	
						
						 
					},
					toolbar:'#appBookGridToolbar'
				"/></table>
				<!-- datagrid toolbar -->
				<div id="appBookGridToolbar">
					<div style="margin-bottom:5px;margin-top: 5px"  >
						<div style="float: left">
							<c:forEach items="${buttons}" var="button">
						         ${button}
						    </c:forEach>
							<a href="javascript:void(0)" onclick="javascript:appBookGridAddAndEdit('添加  appBook','${demoPath}app/book/add.html',0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
							<a href="javascript:void(0)" onclick="javascript:appBookGridAddAndEdit('修改  appBook','${demoPath}app/book/save.html',1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑 </a>  
							<a href="javascript:void(0)" onclick="javascript:appBookGridDel()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
							<a href="javascript:void(0)" onclick="javascript:appBookGridReload()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
							<a href="javascript:void(0)" onclick="javascript:appBookGridExport()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">导出</a>
							<a href="javascript:void(0)" onclick="javascript:appBookPack()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">打包</a>
						</div>
						<div style="float: left;padding-top: 3px;padding-left: 10px;">
							<input    class="easyui-searchbox" data-options="
								menu :'#appBookGridToolbarSearch',
								prompt :'模糊查询',
								searcher : function(value,name){
									var str='{searchType:1,'+name+':\''+value+'\'}';
							        var obj = eval('('+str+')');
							        $('#appBookGrid').datagrid('options').pageNumber=1;
									$('#appBookGrid').datagrid('reload',obj);
								}
							"/>
						</div>
						<div id="appBookGridToolbarSearch">
							<div name="id">主键</div>
							<div name="name">书籍名称</div> 
						</div>
						<div style="clear: both;"></div>
					</div>
				</div>
	</div>
	
	<div id="mainPanle" region="south" split="true"
		style="background: #eee; overflow-y: hidden; height: 300px">
		<div id="booktab" class="easyui-tabs" fit="true" border="false">
			 
		</div>
	</div>
		 