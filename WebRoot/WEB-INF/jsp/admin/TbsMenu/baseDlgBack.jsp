<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 弹出框 tbsMenuDlg -->
<div id="tbsMenuDlg">
	<form id="tbsMenuFm" method="post">
		<table>
			<tr>
				<td><label> 唯一: </label></td>
				<td><input name="id" class="easyui-validatebox" /></td>

				<td><label> 父节点: </label></td>
				<td><input id="tbsMenuComboxTree" name="parentId" /></td>
				
	 		</tr>
	        <tr>
				<td><label> 名称: </label></td>
				<td><input name="name" class="easyui-validatebox" /></td>

				<td><label> 菜单类型: </label></td>
				<td>
					<select name="isMenu">
							<option value="0" selected="selected">菜单</option>
							<option value="2">功能</option>
					</select>
				</td>
				
	 		</tr>
	        <tr>
				<td><label> 加载方式: </label></td>
				<td>
					<select name="type">
							<option value="0" selected="selected">href加载</option>
							<option value="2" >iframe加载</option>
							<option value="1" >树加载</option>
					</select>
				</td>
				<td><label> 排序: </label></td>
				<td><input name="sortNumber" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> 地址: </label></td>
				<td><input name="url" class="easyui-validatebox" /></td>

				<td><label> 按钮: </label></td>
				<td><input name="button" class="easyui-validatebox" /></td>

	 		</tr>
	        <tr>
				<td><label> 状态: </label></td>
				<td><input name="status" class="easyui-validatebox" /></td>

				<td><label> 时间: </label></td>
				<td><input name="createTime" class="easyui-validatebox" /></td>

			</tr>

		</table>
	</form>
</div>