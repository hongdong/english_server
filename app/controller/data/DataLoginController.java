package controller.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TbsLoginLogModel;
import model.TbsUserModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.TbsLoginLogService;
import service.TbsUserService;
import util.core.MethodUtil;
import util.spring.SessionUtil;
import controller.admin.BaseController;

 
@Controller
@RequestMapping("/data")
public class DataLoginController extends BaseController {
	private final static Logger log = Logger.getLogger(DataLoginController.class);
	public static MethodUtil util = new MethodUtil();

	@Autowired
	private TbsUserService<TbsUserModel> tbsUserService; 
	@Autowired
	private TbsLoginLogService<TbsLoginLogModel> tbsLoginLogService;

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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void submit(TbsUserModel tbsUserModel,TbsLoginLogModel tbsLoginLogModel, HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("tbsLoginLogModel:"+tbsLoginLogModel.toString());
		String msg;
		String ip=MethodUtil.getIpAddr(request);
		tbsUserModel.setIp(ip);
		tbsLoginLogModel.setIp(ip);
		tbsLoginLogModel.setId(util.getUid()); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", tbsUserModel.getUsername());
		map.put("password", util.getDES("desKey!@#", tbsUserModel.getPassword(), 0));
		List<TbsUserModel> ltub = tbsUserService.selectByMap(map);
		if (null == ltub || ltub.size() != 1) {
			msg="用户名密码有误";
			util.toJsonMsg(response, 1, msg);
			tbsLoginLogModel.setMsg(msg);
			tbsLoginLogService.insert(tbsLoginLogModel);
			return;
		}
		tbsUserModel = ltub.get(0);
		Integer isAdmin = tbsUserModel.getIsAdmin() == null ? 1 : tbsUserModel.getIsAdmin();
		SessionUtil.setAttr(request, "tbsUserModel", tbsUserModel);
		util.toJsonMsg(response, 0, null);
		//
		tbsLoginLogModel.setStatus(0);//成功
		tbsLoginLogModel.setMsg("登录成功,"+(isAdmin==0?"超级管理员":"普通用户"));
		tbsLoginLogService.insert(tbsLoginLogModel);
		return;
	}
	/**
	 * 功能：</b>注销<br>
	 * @param response
	 * @param request
	 */
	@RequestMapping("/exit")
	public void exit( HttpServletResponse response, HttpServletRequest request) {
		SessionUtil.removeSessionAll(request.getSession());
		util.toJsonMsg(response, 0, "注销成功");
	}
	
	/**
	 * 判断session是否存在
	 * @param response
	 * @param request
	 */
	@RequestMapping("/isLogin")
	public void isLogin( HttpServletResponse response, HttpServletRequest request){
		util.toJsonMsg(response, 0, null);
	}
 	 
}
