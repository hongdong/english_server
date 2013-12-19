<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 弹出框 tbsUserDlg -->
<div id="tbsUserDlg">
	<form id="tbsUserFm" method="post">
		<table>
			<tr>
				<td><label> 主键: </label></td>
    			<td >
					<input name="id" class="easyui-validatebox"  readonly="readonly" 
										/>
				</td>


				<td><label> 用户名: </label></td>
    			<td >
				<input name="username" class="easyui-validatebox" type="text"
			   					 />
				</td>

	 		</tr>
	 		<tr>
	 			<td><label>昵称：</label></td>
	 			<td>
	 				<input name="nickname" class="easyui-validatebox" type="text"/>
	 			</td>
	 			
	 			<td><label>性别：</label></td>
	 			<td>
	 				<input name="sex" class="easyui-validatebox" type="text"/>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td><label>生日日期：</label></td>
	 			<td>
	 				<input name="birthday" class="easyui-validatebox" type="text"/>
	 			</td>
	 			
	 			<td><label>个人简介：</label></td>
	 			<td>
	 				<input name="description" class="easyui-validatebox" type="text"/>
	 			</td>
	 		</tr>
	        <tr>
				<td><label> 密码: </label></td>
    			<td >
				<input name="password" class="easyui-validatebox" type="text"
			   					 />
				</td>
				
				<td><label>电话：</label></td>
	 			<td>
	 				<input name="telephone" class="easyui-validatebox" type="text"/>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td><label>电子邮箱：</label></td>
	 			<td><input name="email" class="easyui-validatebox" type="text"/></td>
	 			
	 			<td><label> 时间: </label></td>
    			<td >
				<input name="createTime"  class="easyui-datetimebox"/></td>
	 		</tr>
	        <tr>

				<td><label> ip: </label></td>
    			<td >
				<input name="ip" class="easyui-validatebox" type="text"
			   					 />
				</td>


				<td><label> 次数: </label></td>
    			<td >
				<input name="count" class="easyui-validatebox" type="text"
			   					 />
				</td>

	 		</tr>
	        <tr>

				<td><label> 锁定: </label></td>
    			<td >
				<input name="isLock" class="easyui-validatebox" type="text"
			   					 />
				</td>


				<td><label> 锁定时间: </label></td>
    			<td >
				<input name="lockTime"  class="easyui-datetimebox"
													 							/>
				</td>

	 		</tr>
	        <tr>

				<td><label> 失败次数: </label></td>
    			<td >
				<input name="failCount" class="easyui-validatebox" type="text"
			   					 />
				</td>


				<td><label> 权限类型: </label></td>
    			<td >
					<select name="isAdmin" >
								<option value="0"  > 超级管理员 </option>
								<option value="1"  > 授权管理员 </option>
					</select>
				</td>


			<tr>
				<td><label> 角色: </label></td>
				<td colspan="3"><input id="tbsUserJoinTbsRole" name="roleId"  size="57"/></td>
			</tr>
			<tr>
				<td><label>头像：</label></td>
				<td colspan="3">
					<div style="float:left;padding: 30px;">
						<input id="portraitFileUpload" type="file"/>
						<input id="portrait" name="portrait" type="hidden"/>
					</div>
					<img id="portraitImg" width="100px" height="100px" style="float: right;">
				</td>
			</tr>
			
			<script src="${demoPath}static/js/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
			<link rel="stylesheet" type="text/css" href="${demoPath}static/js/uploadify/uploadify.css">
			<script type="text/javascript">
			$(function() {
			    $("#portraitFileUpload").uploadify({
			        swf : '${demoPath}static/js/uploadify/uploadify.swf',
			        uploader : "${demoPath}admin/uploadify.html?width=100&height=100",
			        cancelImg :'${demoPath}static/js/uploadify/uploadify-cancel.png',
			        formData : {'fileBaseUrl':"/TbsUser/portrait/"},
			        debug : true,
			        fileObjName : 'file',
			        buttonText : '上传',
			        fileTypeExts : '*.jpg;*.jpge;*.gif;*.png;',
			        onUploadSuccess :function(file, data, response){  
				        var data = eval('(' + data + ')'); 
				        $("#portrait").val(data.fileName);
				        $("#portraitImg")[0].src="${demoPath}openDownloadFile.html?isOnLine=true&filePath="+data.fileName;
			        }  
			    });
			});
			</script>
		</table>
	</form>
</div>
