<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
<div id="appBookPageDlg">
	<form id="appBookPageFm" method="post">
		<input id="bookID" name="bookID"  type="hidden"  />
		<table>
			<tr>
				<td><label> 主键: </label></td>
    			<td >
    				<input name="id"  class="easyui-validatebox"    readonly="readonly">
				</td>
				<td><label> 名称: </label></td>
    			<td >
				<input name="title" class="easyui-validatebox" type="text"  />
				</td>
	 		</tr>
	 		<tr>
				<td><label> 类型: </label></td>
    			<td >
				 	 <select>
				 	 	<option value="0" selected="selected" >跟读类</option>
				 	 </select>
				</td>
				<td><label> 排序: </label></td>
    			<td >
					<input name="sort"  class="easyui-validatebox" />
				</td> 
	 		</tr> 
	 		<tr>
				 
    			<td colspan="4" >
    				<div style="float: left;padding-top: 30px;">
	    				<input id="fileUpload" type="file"/>
	    				<input id="img" name="img" type="hidden"/>
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
			        uploader : "${demoPath}admin/uploadify.html;jsessionid=<%=session.getId()%>",
			        cancelImg :'${demoPath}static/js/uploadify/uploadify-cancel.png',
			        formData : {'fileBaseUrl':"/book/cover/"+bookID+"/"},
			        debug : true,
			        fileObjName : 'file',
			        buttonText : '上传',
			        fileTypeExts : '*.jpg;*.jpge;*.gif;*.png;',
			        onUploadSuccess :function(file, data, response){  
				        var data = eval('(' + data + ')'); 
				        $("#img").val(data.fileName);
				        $("#coverImg")[0].src="${demoPath}openDownloadFile.html?isOnLine=true&filePath="+data.fileName;
			        }  
			    });
			});
			</script>
	 		
		</table>
	</form>
</div>
