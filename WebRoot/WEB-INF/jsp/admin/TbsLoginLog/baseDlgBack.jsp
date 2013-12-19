<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 弹出框 tbsLoginLogDlg -->
<div id="tbsLoginLogDlg">
	<form id="tbsLoginLogFm" method="post">
		<table>
			<tr>
				<td><label> 主键: </label></td>
				<td><input name="id" class="easyui-validatebox" /></td>

				<td><label> 用户名: </label></td>
				<td><input name="username" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> 密码: </label></td>
				<td><input name="password" class="easyui-validatebox" /></td>

				<td><label> 时间: </label></td>
				<td><input name="createTime" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> IP: </label></td>
				<td><input name="ip" class="easyui-validatebox" /></td>

				<td><label> 设备: </label></td>
				<td><input name="userAgent" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> 状态: </label></td>
				<td><input name="status" class="easyui-validatebox" /></td>

				<td><label> 消息: </label></td>
				<td><input name="msg" class="easyui-validatebox" /></td>

			</tr>

		</table>
	</form>
</div>