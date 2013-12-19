<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	//导入
	$("#tbsMenuImport").uploadify({
	    //'height' : '18',
	    //'width' : '50',
	    'swf' : '${demoPath}static/js/uploadify/uploadify.swf',
	    'uploader' : '${demoPath}admin/TbsMenu/import.html;jsessionid=<%=session.getId()%>',
		'cancelImg' : '${demoPath}static/js/uploadify/uploadify-cancel.png',
		'debug' : false,
		'fileObjName' : 'file',
		'buttonClass' :null,
		'buttonText' : '导入',
		'buttonImage' : null,
		'fileTypeDesc' : 'xls|xlsx  Files',
	    'fileTypeExts' : '*.xls; *.xlsx',
		onUploadSuccess : function(file, data, response) {
			//alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
			//$("#tempFileName").val(file.name);
			//$("#"+idName).val(data);
			//  alert("上传成功");
		}
	});
</script>
<!-- 弹出框 tbcTempImportDlg -->
<div id="tbsMenuImportDlg">
	<a href="javascript:void(0)" id="tbsMenuImport">导入</a>
</div>