<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
<div id="appBookPageSentencesDlg">
	<form id="appBookPageSentencesFm" method="post">
		<input id="bookPageID" name="bookPageID"  type="hidden"  />
		<table style="width: 98%">
			<tr>
				<td><label> 主键: </label></td>
    			<td >
    				<input name="id"  class="easyui-validatebox"    readonly="readonly">
				</td>
				<td><label> 排序: </label></td>
    			<td >
					<input name="sort"  class="easyui-validatebox" />
				</td> 
	 		</tr>
	 		<tr>
				<td><label> 句子: </label></td>
    			<td colspan="3" >
    					<textarea  name="sentence" class="easyui-validatebox"  style="width: 100%;height: 80px"  ></textarea> 
				</td> 
	 		</tr> 
	 		<tr>
				 
    			<td colspan="4" >
    				<div style="float: left;padding-top: 30px;">
	    				<input id="fileUpload" type="file"/>
	    				<input id="audio" name="audio" type="hidden"/>
    				</div>
    				<div style="float: right;padding-top: 30px;" ><audio style="width:240px" controls></audio> </div>
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
			        formData : {'fileBaseUrl':"/book/audio/"+bookID+"/"},
			        debug : true,
			        fileObjName : 'file',
			        buttonText : '上传音频',
			        fileTypeExts : '*.mp3;',
			        onUploadSuccess :function(file, data, response){  
				        var data = eval('(' + data + ')'); 
				        $("#audio").val(data.fileName);
				        $("audio").empty();
				        $("audio").iWish({audioSource: "${demoPath}/upload/"+data.fileName, autoPlay: false});
			        }  
			    });
			});
			</script>
		</table>
	</form>
</div>
