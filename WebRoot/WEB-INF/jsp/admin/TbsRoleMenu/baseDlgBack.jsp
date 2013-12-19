<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 弹出框 tbsRoleMenuDlg -->
<div id="tbsRoleMenuDlg">
	<form id="tbsRoleMenuFm" method="post">
		<table>
			<tr>
				<td><label> 主键: </label></td>
				<td><input name="id" class="easyui-validatebox" /></td>

				<td><label> 角色主键: </label></td>
				<td><input name="roleId" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> 功能主键: </label></td>
				<td><input name="menuIdFun" class="easyui-validatebox" /></td>

				<td><label> 菜单主键: </label></td>
				<td><input name="menuId" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> 时间: </label></td>
				<td><input name="createTime" class="easyui-validatebox" /></td>

			</tr>

		</table>
	</form>
</div>