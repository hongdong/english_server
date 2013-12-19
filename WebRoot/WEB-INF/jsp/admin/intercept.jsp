<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="path.jsp"%>
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<!-- easyui-->
<script type="text/javascript" src="${demoPath}static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${demoPath}static/js/easyui/jquery.easyui.min.js"></script>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${demoPath}static/js/easyui/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${demoPath}static/js/easyui/themes/icon.css" />
</head>
<body>
	<div class="easyui-dialog" 
		data-options="
			modal : true,
			title : '未授权',
			top : '15%',
			left : '30%',
			width : 400,
			cache: false,
			resizable:true,
			buttons : [ {
				text : '登录',
				iconCls : 'icon-ok',
				handler : function() {
				   window.location.href='${demoPath}admin/login.html';
				   $(this).closest('.window-body').dialog('destroy');
				}
			}, {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
				    $(this).closest('.window-body').dialog('destroy');
				}
			} ]
		">
	    <div style="padding: 10px;">无访问权限 -[ ${url} ]-请联系管理员或登录!</div>
	</div>
</body>
</html>