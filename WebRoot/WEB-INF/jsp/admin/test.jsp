<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>this is myjsp templates</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<!-- easyui  start -->
<script type="text/javascript" src="/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/static/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="/static/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/static/js/easyui/themes/icon.css" />
<!-- easyui end -->
<script type="text/javascript">
	window.onload = function() {
		/*navigator.appCodeName;	//返回浏览器的代码名。	4	1	9
		navigator.appMinorVersion;	//返回浏览器的次级版本。	4	No	No
		navigator.appName;	//返回浏览器的名称。	4	1	9
		navigator.appVersion	//返回浏览器的平台和版本信息。	4	1	9
		navigator.browserLanguage	//返回当前浏览器的语言。	4	No	9
		navigator.cookieEnabled	//返回指明浏览器中是否启用 cookie 的布尔值。	4	1	9
		navigator.cpuClass	//返回浏览器系统的 CPU 等级。	4	No	No
		navigator.onLine	//返回指明系统是否处于脱机模式的布尔值。	4	No	No
		navigator.platform	//返回运行浏览器的操作系统平台。	4	1	9
		navigator.systemLanguage	//返回 OS 使用的默认语言。	4	No	No
		navigator.userAgent	//返回由客户机发送服务器的 user-agent 头部的值。	4	1	9
		navigator.userLanguage	//返回 OS 的自然语言设置。	4	No	9*/
	}
	//http://www.codefans.net/jscss/code/368.shtml
	//var str='<a href=&quot;#&quot; onclick=&quot;javascript:tbsMenuGridAddAndEdit(&apos;修改&apos;,&apos;/admin/TbsMenu/save.html&apos;,1)&quot; class=&quot;easyui-linkbutton&quot; data-options=&quot;plain:true,iconCls:&apos;icon-edit&apos;&quot;>编辑 </a>';
	//alert(typeof(str));
	//alert(str.replace(/\</g, '&lt;').replace(/\>/g, '&gt;'));
	//alert(str.replace(new RegExp('<','g'), '&lt;'));
</script>
</head>
<body>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table width="435" border="1" cellspacing="3" cellpadding="0">
			<tr>
				<td>浏览器类型</td>
				<td><script>
					document.write(navigator.appName)
				</script></td>
			</tr>
			<tr>
				<td>浏览器版本</td>
				<td><script>
					document.write(navigator.appVersion)
				</script></td>
			</tr>
			<tr>
				<td>浏览器语言</td>
				<td><script>
					document.write(navigator.browserLanguage)
				</script></td>
			</tr>
			<tr>
				<td>CPU类型</td>
				<td><script>
					document.write(navigator.cpuClass)
				</script></td>
			</tr>
			<tr>
				<td>操作系统</td>
				<td><script>
					document.write(navigator.platform)
				</script></td>
			</tr>
			<tr>
				<td>系统语言</td>
				<td><script>
					document.write(navigator.systemLanguage)
				</script></td>
			</tr>
			<tr>
				<td>用户语言;</td>
				<td><script>
					document.write(navigator.userLanguage)
				</script></td>
			</tr>
			<tr>
				<td>在线情况</td>
				<td><script>
					document.write(navigator.onLine)
				</script></td>
			</tr>
			<tr>
				<td>屏幕分辨率</td>
				<td><script>
					document.write(window.screen.width + "x" + window.screen.height)
				</script>
				</td>
			</tr>
			<tr>
				<td>颜色</td>
				<td><script>
					document.write(window.screen.colorDepth + "位")
				</script>
				</td>
			</tr>
			<tr>
				<td>字体平滑</td>
				<td><script>
					document.write(window.screen.fontSmoothingEnabled)
				</script>
				</td>
			</tr>
			<tr>
				<td>appMinorVersion</td>
				<td><script>
					document.write(navigator.appMinorVersion)
				</script></td>
			</tr>
			<tr>
				<td>appCodeName</td>
				<td><script>
					document.write(navigator.appCodeName)
				</script></td>
			</tr>
			<tr>
				<td>cookieEnabled</td>
				<td><script>
					document.write(navigator.cookieEnabled)
				</script></td>
			</tr>
			<tr>
				<td>userAgent</td>
				<td><script>
					document.write(navigator.userAgent)
				</script></td>
			</tr>
			<tr>
				<td>javaEnabled</td>
				<td><script>
					document.write(navigator.javaEnabled())
				</script></td>
			</tr>
			<tr>
				<td>taintEnabled</td>
				<td><script>
					document.write(navigator.taintEnabled())
				</script></td>
			</tr>
		</table>
	</body>
</html>