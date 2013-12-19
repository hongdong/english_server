package controller.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TbsLoginLogModel;
import model.TbsMenuModel;
import model.TbsUserModel;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.TbsLoginLogService;
import service.TbsMenuService;
import service.TbsUserService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import util.core.MapBeanUtil;
import util.core.MethodUtil;
import util.spring.SessionUtil;

 
@Controller
@RequestMapping("/admin")
public class SystemIndexControllerAdmin extends BaseController {
	private final static Logger log = Logger.getLogger(SystemIndexControllerAdmin.class);
	public static MethodUtil util = new MethodUtil();
	private StringBuffer sb = new StringBuffer();

	@Autowired
	private TbsUserService<TbsUserModel> tbsUserService;
	@Autowired
	private TbsMenuService<TbsMenuModel> tbsMenuService;
	@Autowired
	private TbsLoginLogService<TbsLoginLogModel> tbsLoginLogService;

	/**
	 * 
	 * <br>
	 * <b>功能：</b>登录页面<br>
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String from() {
		return "/admin/login";
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>登录递交页<br>
	 * 
	 * @param tbsUserModel
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	public void submit(TbsUserModel tbsUserModel,TbsLoginLogModel tbsLoginLogModel, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String sessionVerifyCode = (String) SessionUtil.getAttr(request, "VERIFY_TYPE_COMMENT");// session验证码
		SessionUtil.removeAttr(request, "VERIFY_TYPE_COMMENT");
		String verifyCode = request.getParameter("verifyCode"); // 递交的验证码
		System.out.println("tbsLoginLogModel:"+tbsLoginLogModel.toString());
		String msg;
		String ip=MethodUtil.getIpAddr(request);
		tbsUserModel.setIp(ip);
		tbsLoginLogModel.setIp(ip);
		tbsLoginLogModel.setId(util.getUid());
		/*if (null == sessionVerifyCode || null == verifyCode || verifyCode.trim().length() != 4) {
			msg="验证码长度有误或已失效";
			util.toJsonMsg(response, 2, msg);
			tbsLoginLogModel.setMsg(msg);
			tbsLoginLogService.insert(tbsLoginLogModel); 
			return;
		}
		if (!sessionVerifyCode.toUpperCase().equals(verifyCode.toUpperCase())) {
			msg="验证码错误";
			util.toJsonMsg(response, 2, msg);
			//
			tbsLoginLogModel.setMsg(msg);
			tbsLoginLogService.insert(tbsLoginLogModel);
			return;
		}*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", tbsUserModel.getUsername());
		map.put("password", util.getDES("desKey!@#", tbsUserModel.getPassword(), 0));
		List<TbsUserModel> ltub = tbsUserService.selectByMap(map);
		if (null == ltub || ltub.size() != 1) {
			msg="用户名密码有误";
			util.toJsonMsg(response, 1, msg);
			//
			tbsLoginLogModel.setMsg(msg);
			tbsLoginLogService.insert(tbsLoginLogModel);
			return;
		}
		tbsUserModel = ltub.get(0);
		Integer isAdmin = tbsUserModel.getIsAdmin() == null ? 1 : tbsUserModel.getIsAdmin();
		SessionUtil.setAttr(request, "isAdmin", "" + isAdmin);
		SessionUtil.setAttr(request, "tbsUserModel", tbsUserModel);
		List<String> authUrls = new ArrayList<String>();
		authUrls.add("/admin/index.html");
		SessionUtil.setAttr(request, "authUrls", authUrls);
		util.toJsonMsg(response, 0, null);
		//
		tbsLoginLogModel.setStatus(0);//成功
		tbsLoginLogModel.setMsg("登录成功,"+(isAdmin==0?"超级管理员":"授权管理员"));
		tbsLoginLogService.insert(tbsLoginLogModel);
		return;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>主页<br>
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index.html")
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("andCondition", "parentId IS NULL");
		map.put("orderCondition", "sortNumber");
		List<TbsMenuModel> parentMenu = tbsMenuService.selectByMap(map);
		String isAdmin = (String) SessionUtil.getAttr(request, "isAdmin");
		if (null != isAdmin && isAdmin.equals("0")) { // 管理员
			for (int i = 0; i < parentMenu.size(); i++) {
				String id = parentMenu.get(i).getId();
				map.clear();
				map.put("parentId", id);
				List<TbsMenuModel> child = tbsMenuService.selectByMap(map);
				for (int j = 0; j < child.size(); j++) {
					if(parentMenu.get(i).getListTbsMenuModel()==null){
						parentMenu.get(i).setListTbsMenuModel(new java.util.ArrayList<TbsMenuModel>());
					}
					parentMenu.get(i).getListTbsMenuModel().add(child.get(j));
				}
			}
			modelMap.put("listTbsMenuModel", parentMenu);
			return "admin/index";
		}
		// 其他用户
		TbsUserModel tbsUserModel = (TbsUserModel) SessionUtil.getAttr(request, "tbsUserModel");
		@SuppressWarnings("unchecked")
		List<String> authUrls = (List<String>) SessionUtil.getAttr(request, "authUrls");
		map.clear();
		map.put("cloumn", "menuIdFun");
		map.put("userId", tbsUserModel.getId());
		List<Map<String, Object>> childMenu = tbsUserService.selectByRoleUrls(map);
		if (childMenu != null && childMenu.size() > 0) { // 添加授权地址
			for (int i = 0; i < childMenu.size(); i++) {
				String roleUrls = (String) childMenu.get(i).get("url");
				String[] urls = roleUrls.split("\\,");
				for (int j = 0; j < urls.length; j++) {
					System.out.println("addUrl:" + urls[j]);
					authUrls.add("/"+urls[j]);
				}
			}
		}
		map.clear();
		map.put("cloumn", "menuId");
		map.put("userId", tbsUserModel.getId());
		childMenu = tbsUserService.selectByRoleUrls(map);
		for (int i = 0; i < parentMenu.size(); i++) { // 主菜单找子菜单
			TbsMenuModel tbsMenuModel = parentMenu.get(i);
			if (null != childMenu && childMenu.size() > 0) {
				for (int j = 0; j < childMenu.size(); j++) {
					Map<String, Object> childMap = childMenu.get(j);
					System.out.println("childMap:" + childMap);
					String parentId = (String) childMap.get("parentId");
					if (tbsMenuModel != null && tbsMenuModel.getId().equals(parentId)) {
						TbsMenuModel bean=MapBeanUtil.mapToBean(childMap, TbsMenuModel.class);
						authUrls.add("/"+bean.getUrl()); // 权限URL
						if(parentMenu.get(i).getListTbsMenuModel()==null){
							parentMenu.get(i).setListTbsMenuModel(new java.util.ArrayList<TbsMenuModel>());
						}
						parentMenu.get(i).getListTbsMenuModel().add(bean);
						System.out.println("childMap:" + childMap+"|bean:"+bean.toString());
					}
				}
			}
			if (tbsMenuModel.getListTbsMenuModel()==null || tbsMenuModel.getListTbsMenuModel().size()==0) { // 没找到子菜单 删除自己
				parentMenu.remove(i);
				i--;
			}
		}
		SessionUtil.setAttr(request, "authUrls", authUrls);// 重置
		modelMap.put("listTbsMenuModel", parentMenu);
		return "admin/index";
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>退出<br>
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/exit.html")
	public String exit(HttpSession session) {
		SessionUtil.removeSessionAll(session);
		return "/admin/login";
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>同步数目录递归JSON<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/dirJson.html")
	@ResponseBody
	public synchronized String dirJson(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("RealPath:" + path);
		if (sb.length() > 0)
			sb.delete(0, sb.length());// 清除缓存
		String commonPathJson = this.getJsonData(new File(path)); // 项目路径Json
		commonPathJson = "[" + commonPathJson + "]";
		System.out.println("dirJson:" + commonPathJson);
		return commonPathJson;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>异步树 递归<br>
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/asyJson.html")
	@ResponseBody
	public synchronized String asyJson(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String asyJson = null;
		String path = null;

		if (null == id) {
			path = request.getSession().getServletContext().getRealPath("/");
		} else {
			path = new String(new BASE64Decoder().decodeBuffer(URLDecoder.decode(id, "utf-8")));
		}
		System.out.println("id:" + id + "|asyJsonPath:" + path);
		if (sb.length() > 0)
			sb.delete(0, sb.length());// 清除缓存
		asyJson = "[" + this.getAsyJsonData(new File(path)) + "]";
		System.out.println(asyJson);
		return asyJson;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>打开文件<br>
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/openFile.html")
	public String getTreeOpenFile(String path, String id, ModelMap modelMap) throws IOException {
		System.out.println("path:" + path);
		modelMap.addAttribute("path", path); // base64加密路径
		path = new String(new BASE64Decoder().decodeBuffer(URLDecoder.decode(id, "utf-8")));
		File file = new File(path);
		if (!file.exists()) {
			return "error";
		}
		System.out.println("path:" + path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\r");
		}
		String text = sb.toString();
		text = text.replaceAll("<", "&lt");
		modelMap.addAttribute("str", text);
		// modelMap.addAttribute("id", id);
		modelMap.addAttribute("id", util.getMD5(id, null, 1)); // utils.getMD5UTF8(filePath)
		// modelMap.addAttribute("")
		// System.out.println("modelMap:"+modelMap);
		return "admin/SystemTree/openFile";
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>保存文件<br>
	 * 
	 * @param request
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/saveFile.html")
	@ResponseBody
	// 输出字符串
	public void getTreeSave(String path, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		if(true){
			util.toJsonMsg(response, 1, "演示项目不允许被修改"); //不让文件被恶意修改
			return;
		}
		if (null == path){ // 路径为空
			util.toJsonMsg(response, 1, null);
			return;
		}
		path = new String(new BASE64Decoder().decodeBuffer(URLDecoder.decode(path, "utf-8")));
		String textarea = request.getParameter("textarea") == null ? "" : request.getParameter("textarea"); // 写入内容
		textarea = textarea.trim();
		System.out.println("savePath:" + path);
		// System.out.println("textarea:" + textarea);
		File file = new File(path);
		if (!file.exists()) {
			util.toJsonMsg(response, 1, null);
			return ;
		}
		file.delete(); // 存在删除文件
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			outputStreamWriter.write(textarea);
			outputStreamWriter.flush();
			outputStreamWriter.close();
			util.toJsonMsg(response, 0, null);
			return ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		util.toJsonMsg(response, 1, null);
		return;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>文件树JSON<br>
	 * 
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private synchronized String getJsonData(File file) throws UnsupportedEncodingException {
		if (!file.exists()) {
			return null;
		}
		boolean isFile = file.isFile();
		boolean isDir = file.isDirectory();
		String type = "file";
		if (isDir)
			type = "folder";

		String fileName = file.getName();
		String filePath = file.getPath();
		String md5Str = util.getMD5(filePath, null, 1);
		String base64Encoder = URLEncoder.encode(new BASE64Encoder().encode(filePath.getBytes()), "UTF-8");
		String url = "admin/openFile.html"; // 路径
		// System.out.println(base64Encoder);
		sb.append("{"); // ,\"attributes\":{\"url\":\"/admin/tree/openFile.html\",\"target\":\"mainFrame\"
		sb.append("\"id\":\"" + md5Str + "\",\"text\":\"" + fileName + "\"");
		sb.append(",\"attributes\":{\"text\":\"" + fileName + "\",\"url\":\"" + url + "\",\"type\":\"" + type + "\",\"path\":\"" + base64Encoder + "\"}");
		if (isDir) {
			File fileList[] = file.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					String fileNameLower = pathname.getName().toLowerCase();
					if (pathname.isHidden())
						return false;
					/*********** 隐藏文件过滤 ***********/
					if (fileNameLower.matches(".*(meta-inf|templates)$|.*.(gif|jpg|png|ico|class|.jar)$")) {
						return false;
					}
					return true;
				}
			});
			// sb.append(",\"attributes\":{\"id\":\""+md5Str+"\",\"path\":\""+base64Encoder+"\"}");
			if (fileList.length > 0) {
				sb.append(",\"state\":\"closed\",\"children\":[");
				for (int i = 0; i < fileList.length; i++) {
					if (i > 0)
						sb.append(",");
					this.getJsonData(fileList[i]);
				}
				sb.append("]");
			}
		}
		// target="mainFrame"
		if (isFile) {
			// sb.append(",\"state\":\"closed\""); //\"target\":\"mainFrame\",
			// sb.append(",\"attributes\":{\"id\":\""+md5Str+"\",\"path\":\""+base64Encoder+"\"}");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>异步数实现<br>
	 * 
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private synchronized String getAsyJsonData(File file) throws UnsupportedEncodingException {
		if (!file.exists()) {
			return null;
		}
		File fileList[] = file.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				String fileNameLower = pathname.getName().toLowerCase();
				if (pathname.isHidden())
					return false;
				/*********** 隐藏文件过滤 ***********/
				if (fileNameLower.matches(".*(meta-inf|templates)$|.*.(gif|jpg|png|ico|class|.jar|.zip|.gz|.sql|.exe|.bt|.sh)$")) {
					return false;
				}
				return true;
			}
		});
		for (int i = 0; i < fileList.length; i++) {
			file = fileList[i];
			boolean isDir = file.isDirectory();
			String type = "file";
			String state = "open";
			if (isDir) {
				type = "folder";
				state = "closed";
			}
			String fileName = file.getName();
			String filePath = file.getPath();
			// String md5Str = utils.getMD5UTF8(filePath);
			String base64Encoder = URLEncoder.encode(new BASE64Encoder().encode(filePath.getBytes()), "UTF-8");
			String url = "admin/openFile.html"; // 路径
			// System.out.println(base64Encoder);
			sb.append("{"); // ,\"attributes\":{\"url\":\"/admin/tree/openFile.html\",\"target\":\"mainFrame\"
			sb.append("\"id\":\"" + base64Encoder + "\",\"text\":\"" + fileName + "\",\"state\":\"" + state + "\"");
			sb.append(",\"attributes\":{\"text\":\"" + fileName + "\",\"url\":\"" + url + "\",\"type\":\"" + type + "\",\"path\":\"" + base64Encoder + "\"}");
			sb.append("},");
		}
		if (fileList.length > 0) {
			sb.delete(sb.length() - 1, sb.length());
		} else {
			sb.append("");
		}
		return sb.toString();
	}
    
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	/**
	 * 
	 * <br>
	 * <b>功能：</b>修复用户<br>
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/rollBack.html")
	public void backUpAdmin(HttpServletResponse response) {
		//TbsUserModel tbsUserModel = new TbsUserModel();
		try {
			 String sqlFilePath=SystemIndexControllerAdmin.class.getResource("/").toURI().getPath()+"back.sql";
			 StringWriter errorWriter=new StringWriter();
			 StringWriter logWriter=new StringWriter();
			 System.out.println("sqlFilePath:"+sqlFilePath);
		     Connection con=sqlSessionFactory.openSession().getConnection();
		     ScriptRunner sqlScript=new ScriptRunner(con);
		     sqlScript.setErrorLogWriter(new PrintWriter(errorWriter));
		     sqlScript.setLogWriter(new PrintWriter(logWriter));
		     Reader reader=new BufferedReader(new InputStreamReader(new FileInputStream(new File(sqlFilePath)),"UTF-8"));
		     sqlScript.runScript(reader);
		     sqlSessionFactory.openSession().close();
		     //DBFactory.getDBDao().getCloseCon(con, null, null, null);
		     System.out.println("errorWriter:"+errorWriter);
		     System.out.println("logWriter:"+logWriter);
		     util.toJsonMsg(response, 0, null);
		/*	tbsUserModel.setUsername("admin");
			tbsUserService.deleteByEntity(tbsUserModel);
			tbsUserModel.setUsername("test");
			tbsUserService.deleteByEntity(tbsUserModel);
			tbsUserModel.setId(util.getUidString());
			tbsUserModel.setUsername("admin");
			tbsUserModel.setPassword("adb72b256f38df9e");
			tbsUserModel.setIsAdmin(0);
			tbsUserService.insert(tbsUserModel);
			tbsUserModel.setId(util.getUidString());
			tbsUserModel.setUsername("test");
			tbsUserModel.setPassword("adb72b256f38df9e");
			tbsUserModel.setIsAdmin(1);
			tbsUserService.insert(tbsUserModel);*/
		} catch (Exception e) {
			e.printStackTrace();
			util.toJsonMsg(response, 1, null);
		}
	}
	 
}
