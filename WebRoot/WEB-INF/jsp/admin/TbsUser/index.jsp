<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	//Add and Edit
	function tbsUserGridAddAndEdit(title, url, type) {
		if (type == 1) { //edit
			var rows = $('#tbsUserGrid').datagrid('getSelections');
			if (rows.length != 1) {
				$.messager.alert('消息', '请钩择一行数据!', 'info');
				return;
			}
		}
		$('<div/>').dialog({
			href : '${demoPath}admin/TbsUser/baseDlg.html',
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
					tbsUserGridSubmit(url);
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
					$('#tbsUserFm').form('clear');
					$('#tbsUserJoinTbsRole').combogrid({
						url : '${demoPath}admin/TbsRole/data.html?t='+ Math.random(),
						panelWidth : 460,
						textField : 'name',
						frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
						columns:[ [  
				                {field:'id',title:'主键',width:100},
				                {field:'name',title:'角色',width:100},
				                {field:'text',title:'权限名称',width:100},
				                {field:'createTime',title:'创建时间',width:130}
				        ] ]
					});
				} else {
					var rows = $('#tbsUserGrid').datagrid('getSelections');
					if (rows.length == 1) {
						$.getJSON('${demoPath}admin/TbsUser/password.html?type=1&password='+rows[0].password,function(data){
							$('#tbsUserFm').form('load', rows[0]);
							$('#tbsUserFm').form('load',{password:data.msg});
						});
						$('#tbsUserJoinTbsRole').combogrid({
							url : '${demoPath}admin/TbsRole/data.html?userId='+rows[0].id,
							panelWidth : 460,
							textField : 'name',
							frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
							columns:[ [  
						            {field:'id',title:'主键',width:100},
						            {field:'name',title:'角色',width:100},
						            {field:'text',title:'权限名称',width:100},
						            {field:'createTime',title:'创建时间',width:130}
						    ] ],
						    onLoadSuccess:function(data){
						    	$.getJSON('${demoPath}admin/TbsRoleUser/data.html?userId='+rows[0].id,function(data){
						    		var roleUserRows=data.rows;
						    		var g = $('#tbsUserJoinTbsRole').combogrid('grid');	// get datagrid object
									var roleRows= g.datagrid('getRows');
							    	if(roleRows.length>0 && roleUserRows.length>0){
							    		for(var i=0;i<roleRows.length;i++){
							    			for(var r=0;r<roleUserRows.length;r++){
								    			 if(roleRows[i].id==roleUserRows[r].roleId){
								    				 g.datagrid('checkRow',i);
								    			 }
								    		}
								    	}
							    	}
						    	});
						    }
						});

					} else {
						$.messager.alert('消息', '请钩择一行数据!', 'info');
					}
				}
			}
		});
	}
	
	//Del
	function tbsUserGridDel() {
		var rows = $('#tbsUserGrid').datagrid('getSelections');
		if (rows.length > 0) {
		    var ids = '';
			for ( var i = 0; i < rows.length; i++) {
						ids += 'ids=' + rows[i].id + '&';
			}
			ids = ids.substring(0, ids.length - 1);
			var url = '${demoPath}admin/TbsUser/del.html?' + ids;
			$.messager.confirm('Confirm','确定要删除选择的数据吗?', function(r) {
				if (r) {
				    $.get(url, function(result){
						if (result.success){ 
							$('#tbsUserGrid').datagrid('reload');
							$('#tbsUserGrid').datagrid('clearSelections');
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
	function tbsUserGridReload() {
		$('#tbsUserGrid').datagrid('options').pageNumber=1;
		$('#tbsUserGrid').datagrid('reload',{});
	}
	
	//tbsUserGridSubmit  submit
	function tbsUserGridSubmit(url) {
	    $('#tbsUserFm').form('submit',{  
	        url: url,  
	        onSubmit: function(){  
	            return $(this).form('validate');  
	        },  
	        success: function(result){  
	            var result = eval('('+result+')');  
	            if (result.success){  
	                $('#tbsUserDlg').dialog('close');      // close the dialog 
					$('#tbsUserGrid').datagrid('reload');    // reload the user data
	            } else {  
	            	$.messager.show({ title: 'Error',msg: result.msg }); 
	            }  
	        }  
	    });
	}
	
	//高级搜索 del row
	function tbsUserSearchRemove(curr) {
		if ($(curr).closest('table').find('tr').size() > 2) {
			$(curr).closest('tr').remove();
		} else {
			alert('该行不允许删除');
		}
	}
	
	//高级查询
	function tbsUserSearch() {
		$('<div/>').dialog({
			href : '${demoPath}admin/TbsUser/searchDlg.html',
			modal : true,
			title : '高级查询',
			top : 120,
			width : 480,
			buttons : [ {
				text : '增加一行',
				iconCls : 'icon-add',
				handler : function() {
					var currObj = $(this).closest('.panel').find('table');
					currObj.find('tr:last').clone().appendTo(currObj);
					//currObj.find('tr:last td *[disabled]').removeAttr("disabled");
				}
			}, {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					$('#tbsUserGrid').datagrid('options').pageNumber=1;
					$('#tbsUserGrid').datagrid('reload',serializeObjectEx($('#tbsUserSearchFm')));
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
			}
		});
	}
	
	//导出
	function tbsUserGridExport(){
		window.location="${demoPath}admin/TbsUser/export.html";
		//var panel=$('#tbcTempGrid').datagrid('getPanel');
		//var options=panel.panel('options');
		//alert(options.method);
		//console.dir(options);
	}
	
	//导入
	function tbsUserGridImport(){
		$('<div/>').dialog({
			href : '${demoPath}admin/TbsUser/importDlg.html',
			modal : true,
			title : '导入',
			top : '15%',
			left : '30%',
			width : 600,
			height: 300,
			resizable:true,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					//$(this).closest('.window-body').dialog('destroy');
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
	
</script>
    

	<!-- 中  datagrid-->
    <div data-options="region:'center',border : false">
		<!-- datagrid toolbar -->
		<table id="tbsUserGrid"  class="easyui-datagrid"  data-options="	
			url:'${demoPath}admin/TbsUser/data.html',
			frozenColumns : [ [ {field : 'ck',checkbox : true}] ],
			columns:[ [  

			{field:'id',title:'主键',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'username',title:'用户名',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			
			{field:'nickname',title:'昵称',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			
			{field:'sex',title:'性别',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			
			{field:'birthday',title:'生日日期',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			
			{field:'description',title:'个人简介',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'password',title:'密码',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			
			{field:'telephone',title:'电话',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			
			{field:'email',title:'邮箱',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'createTime',title:'时间',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'ip',title:'ip',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'count',title:'次数',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'isLock',title:'锁定',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'lockTime',title:'锁定时间',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'failCount',title:'失败次数',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},			

			{field:'portrait',title:'头像',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
			    return value;
			}},
			{field:'isAdmin',title:'权限类型',hidden:false,width:'135',halign:'center',align:'center',sortable:'true', formatter: function(value,row,index){
				if(value=='0'){
			    	value='超级管理员';
			    }
				if(value=='1'){
			    	value='授权管理员';
			    }
			    return value;
			}}			
			] ],
			
			toolbar:'#tbsUserGridToolbar'
		"/>
		
		<!-- datagrid toolbar -->
		<div id="tbsUserGridToolbar">
			<div style="margin-bottom:5px">
				<c:forEach items="${buttons}" var="button">
			         ${button}
			    </c:forEach>
				<%-- 
				<a href="javascript:void(0)" onclick="javascript:tbsUserGridAddAndEdit('添加  tbsUser','${demoPath}admin/TbsUser/add.html',0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
				<a href="javascript:void(0)" onclick="javascript:tbsUserGridAddAndEdit('修改  tbsUser','${demoPath}admin/TbsUser/save.html',1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑 </a>  
				<a href="javascript:void(0)" onclick="javascript:tbsUserGridDel()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
				<a href="javascript:void(0)" onclick="javascript:tbsUserGridReload()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
				<a href="javascript:void(0)" onclick="javascript:tbsUserGridExport()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">导出</a>
				<a href="javascript:void(0)" onclick="javascript:tbsUserGridImport()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">导入</a>
				
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-undo'">后退</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'">前进</a>
				--%>
				<!-- tbsUserGridToolbarSearch -->
				<input class="easyui-searchbox" data-options="
					menu :'#tbsUserGridToolbarSearch',
					prompt :'模糊查询',
					searcher : function(value,name){
						var str='{searchType:1,'+name+':\''+value+'\'}';
				        var obj = eval('('+str+')');
				        $('#tbsUserGrid').datagrid('options').pageNumber=1;
						$('#tbsUserGrid').datagrid('reload',obj);
					}
				"/>
				<div id="tbsUserGridToolbarSearch">
					<div name="id">主键</div>
					<div name="username">用户名</div>
					<div name="nickname">昵称</div>
					<div name="sex">性别</div>
					<div name="birthday">生日日期</div>
					<div name="description">个人简介</div>
					<div name="password">密码</div>
					<div name="telephone">电话</div>
					<div name="email">邮箱</div>
					<div name="createTime">时间</div>
					<div name="ip">ip</div>
					<div name="count">次数</div>
					<div name="isLock">锁定</div>
					<div name="lockTime">锁定时间</div>
					<div name="failCount">失败次数</div>
					<div name="isAdmin">权限类型</div>
					<div name="portrait">头像</div>
				</div>
				<a href="javascript:void(0)" onclick="javascript:tbsUserSearch()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">高级查询</a>
			</div>
		</div>
	</div>
<!--  <div>-->