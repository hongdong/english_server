<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	$("#${id}").markItUp(mySettings); //mySettings
	$('#${id}btn').bind('click', function() {
		$('#${id}myForm').form('submit', {
			success : function(data) {
				var result=eval('('+data+')');
				if (result.success)
					$.messager.alert('info', result.msg, 'info');
				else
					$.messager.alert('info', result.msg, 'error');
			}
		});
	});
</script>
<style>
.markItUp{
   width: 98%;
}
</style>
<div style="padding: 3px;">
	<a id="${id}btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> 
	<form id="${id}myForm" action="${demoPath}admin/saveFile.html?path=${path}" method="post">
		<textarea id="${id}" style="width: 96%" rows="20" name="textarea">${str}</textarea>
	</form>
</div>
