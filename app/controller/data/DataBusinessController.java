package controller.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TbsRoleUserModel;
import model.TbsUserModel;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import service.TbsRoleUserService;
import service.TbsUserService;
import util.core.MethodUtil;
import util.spring.MyTimestampPropertyEdit;
import util.spring.SessionUtil;
import controller.admin.BaseController;

@Controller
@RequestMapping("/data/user/")
public class DataBusinessController extends BaseController {
	private final static Logger log = Logger
			.getLogger(DataBusinessController.class);
	private static MethodUtil util = new MethodUtil();
	private static final String ROLE_USER = "13120210554325449089";
	private static final String ROLE_TEACHER = "13120210592551803153";
	private static String path = "/TbsUser/portrait/";
	

	// 服务类
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private TbsUserService<TbsUserModel> tbsUserService;
	 

	@Autowired
	TbsRoleUserService<TbsRoleUserModel> tbsRoleUserService;

	@InitBinder
	// 必须有一个参数WebDataBinder 日期类型装换
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Timestamp.class,
				new MyTimestampPropertyEdit()); // 使用自定义属性编辑器
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>保存 TbsUserModel信息<br>
	 * 
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(TbsUserModel tbsUserModel,HttpServletRequest request,HttpServletResponse response) {
			tbsUserModel.setPassword(util.getDES("desKey!@#", tbsUserModel.getPassword(), 0));
			tbsUserModel.setId(((TbsUserModel)SessionUtil.getAttr(request, "tbsUserModel")).getId());
			try {
				tbsUserService.updateByPrimaryKey(tbsUserModel);
				util.toJsonMsg(response, 0, null);
			} catch (Exception e) {
				util.toJsonMsg(response, 1, null);
			}
		
	}
	
	@RequestMapping("imageSave")
	public void imageSave(HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		
		String fileSave = request.getSession().getServletContext().getRealPath("/") + File.separator + "upload";
		fileSave += path;
		File file = new File(fileSave);
		if(!file.exists()){
			file.mkdirs();
		}
		String fileName = null;
		String newFileName = null;
		for(Map.Entry<String, MultipartFile> entity : fileMap.entrySet()){
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			File uploadFile = new File(fileSave + newFileName);
			try {
				BufferedImage src = ImageIO.read(mf.getInputStream());
				ImageIO.write(src, fileExt, new File(fileSave + newFileName));
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		TbsUserModel tbsUserModel = new TbsUserModel();
		tbsUserModel.setId(((TbsUserModel)SessionUtil.getAttr(request, "tbsUserModel")).getId());
		tbsUserModel.setPortrait(path + newFileName);
		try {
			tbsUserService.updateByPrimaryKey(tbsUserModel);
			util.toJsonMsg(response, 0, "保存路径:" + path + newFileName);
		} catch (Exception e) {
			util.toJsonMsg(response, 1, null);
		}
	}
	
	 
	/**
	 * 
	 * <br>
	 * <b>功能：</b>增加操作 TbsUserModel<br>
	 * 
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public void add(TbsUserModel tbsUserModel, HttpServletResponse response) {
		String id = util.getUid();
		tbsUserModel.setId(id);
		tbsUserModel.setIsAdmin(1);
		try {
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("username", tbsUserModel.getUsername());
			if(tbsUserService.selectByMapCount(map)!=0)
			{
				util.toJsonMsg(response, 2, "用户名已存在");
				return ;
			}
			TbsRoleUserModel tbsRoleUserModel = new TbsRoleUserModel();
			tbsRoleUserModel.setId(util.getUid());
			tbsRoleUserModel.setRoleId(ROLE_USER);
			tbsRoleUserModel.setUserId(id);
			tbsRoleUserService.insert(tbsRoleUserModel);
			tbsUserModel.setPassword(util.getDES("desKey!@#", tbsUserModel
					.getPassword(), 0));
			tbsUserService.insert(tbsUserModel);// 入库
			util.toJsonMsg(response, 0, null);
		} catch (Exception e) {
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}

}
