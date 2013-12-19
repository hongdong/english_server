<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
<div id="appBookDlg">
	<form id="appBookFm" method="post">
		<table>
			<tr>
				<td><label> 主键: </label></td>
    			<td >
					<input name="id"  class="easyui-validatebox"    readonly="readonly">
				</td>
				<td><label> 书籍名称: </label></td>
    			<td >
				<input name="name" class="easyui-validatebox" type="text"  />
				</td>
	 		</tr>
	 		<tr>
				<td><label> 类型: </label></td>
    			<td >
				 	<input id="appBookJoinAppCategory" name="category" class="easyui-validatebox" type="text" />
				</td>
				<td><label> 时间: </label></td>
    			<td >
				<input name="createTime"  class="easyui-datetimebox" />
				</td>
	 		</tr>
	 		<tr>
				<td><label> 书籍描述: </label></td>
    			<td colspan="3" >
    				<textarea  name="description" class="easyui-validatebox"  style="width: 100%;height: 80px"  ></textarea> 
				</td>
	 		</tr>
	 		<tr>
				<td><label> 封面: </label></td>
    			<td colspan="3" >
    				<div style="float: left;padding-top: 30px;">
	    				<input id="fileUpload" type="file"/>
	    				<input id="cover" name="cover" type="hidden"/>
    				</div>
    				<img id="coverImg" width="100px" height="100px" style="float: right" ></img>
				</td>
	 		</tr>
	 		
	 		<script src="${demoPath}static/js/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
			<link rel="stylesheet" type="text/css" href="${demoPath}static/js/uploadify/uploadify.css">
			<script type="text/javascript">
			$(function() {
			    $("#fileUpload").uploadify({
			        height : 30,
			        width : 120,
			        swf : '${demoPath}static/js/uploadify/uploadify.swf',
			        uploader : "${demoPath}admin/uploadify.html?width=100&height=100",
			        cancelImg :'${demoPath}static/js/uploadify/uploadify-cancel.png',
			        formData : {'fileBaseUrl':"/book/cover/"},
			        debug : true,
			        fileObjName : 'file',
			        buttonText : '上传',
			        fileTypeExts : '*.jpg;*.jpge;*.gif;*.png;',
			        onUploadSuccess :function(file, data, response){  
				        var data = eval('(' + data + ')'); 
				        $("#cover").val(data.fileName);
				        $("#coverImg")[0].src="${demoPath}openDownloadFile.html?isOnLine=true&filePath="+data.fileName;
			        }  
			    });
			});
			</script>
	 		
		</table>
	</form>
</div>
