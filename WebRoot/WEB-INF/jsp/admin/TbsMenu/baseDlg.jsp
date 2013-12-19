<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 弹出框 tbsMenuDlg -->
<div id="tbsMenuDlg">
	<form id="tbsMenuFm" method="post">
		<table>
			<tr>
				<td><label> 唯一: </label></td>
    			<td >
					<input name="id" class="easyui-validatebox"  readonly="readonly" 
										/>
				</td>


				<td><label> 父节点: </label></td>
    			<td >
				<input name="parentId" class="easyui-validatebox" type="text"
			   	 id="tbsMenuComboxTree" 				 />
				</td>

	 		</tr>
	        <tr>

				<td><label> 名称: </label></td>
    			<td >
				<input name="name" class="easyui-validatebox" type="text"
			   					 />
				</td>


				<td><label> 菜单类型: </label></td>
    			<td >
					<select name="isMenu" 
				
					>
													<option value="0"  > 菜单 </option>
													<option value="1"  > 功能 </option>
										</select>
				</td>

	 		</tr>
	        <tr>

				<td><label> 加载方式: </label></td>
    			<td >
					<select name="type" 
				
					>
													<option value="0"  > href </option>
													<option value="1"  > 树形 </option>
													<option value="2"  > iframe </option>
										</select>
				</td>


				<td><label> 排序: </label></td>
    			<td >
				<input name="sortNumber" class="easyui-validatebox" type="text"
			   					 />
				</td>

	 		</tr>
	        <tr>

				<td><label> 地址: </label></td>
    			<td >
				<input name="url" class="easyui-validatebox" type="text"
			   					 />
				</td>


				<td><label> 按钮: </label></td>
    			<td >
				<input name="button" class="easyui-validatebox" type="text"
			   					 />
				</td>

	 		</tr>
	        <tr>

				<td><label> 状态: </label></td>
    			<td >
					<select name="status" 
				
					>
													<option value="0"  > 启用 </option>
													<option value="1"  > 禁用 </option>
										</select>
				</td>


				<td><label> 时间: </label></td>
    			<td >
				<input name="createTime"  class="easyui-datetimebox"
													 							/>
				</td>


		</table>
	</form>
</div>
