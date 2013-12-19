package controller;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AppCategoryModel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.AppCategoryService;
import util.core.ExcelUtil;
import util.core.MethodUtil;
import util.core.PageParams;
import util.spring.MyTimestampPropertyEdit;
import util.spring.SessionUtil;

import com.alibaba.fastjson.JSON;

import controller.admin.BaseController;
@Controller
@RequestMapping("/app/category/")
public class AppCategoryController extends BaseController{	private final static Logger log= Logger.getLogger(AppCategoryController.class);
	private static  MethodUtil util = new MethodUtil();
	

	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppCategoryService<AppCategoryModel> appCategoryService; 
	
	@Autowired
	service.TbsMenuService<model.TbsMenuModel> tbsMenuService;
	
	@InitBinder//必须有一个参数WebDataBinder 日期类型装换
	public void initBinder(WebDataBinder binder) {
		    binder.registerCustomEditor(Timestamp.class,new MyTimestampPropertyEdit()); //使用自定义属性编辑器
		//  binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	

	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("index.html")
	public ModelAndView index(String id, ModelMap modelMap, HttpServletRequest request) {
		List<String> buttons = new java.util.ArrayList<String>();
		model.TbsMenuModel tbsMenuModel=new model.TbsMenuModel();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", id);
		map.put("orderCondition", "sortNumber");
		System.out.println("id:" + id);
		String isAdmin = (String) SessionUtil.getAttr(request, "isAdmin");
		List<model.TbsMenuModel> list=null;
		try {
			if (null != isAdmin && isAdmin.equals("0")) {// 超管不需要验证权限
				list = tbsMenuService.selectByMap(map);
			} else {
				list = tbsMenuService.selectByButtons(map);
			}
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					tbsMenuModel = list.get(i);
					String button = tbsMenuModel.getButton();
					if (null != button && "null" != button) {
						buttons.add(button); 
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("buttons", buttons);
		return new ModelAndView("app/category/index", modelMap);
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("baseDlg.html")
	public String baseDlg(){
		return "app/category/baseDlg";
	}
	   
   
   /**
     * 
     * <br>
     * <b>功能：</b>方法功能描述<br>
     * @param pageParams
     * @param appCategoryModel
     * @return
     * @throws Exception
     */
	@RequestMapping("data.html")
	@ResponseBody
	public String data(PageParams pageParams, AppCategoryModel appCategoryModel) throws Exception {
		System.out.println("pageParams:" + pageParams + "|appCategoryModel:" + appCategoryModel);
		appCategoryModel.getPageUtil().setPaging(true);
		String result = "[]";
		if (pageParams.getPage() != null) {
			try {
				appCategoryModel.getPageUtil().setPageId(Integer.parseInt(pageParams.getPage())); // 当前页
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getRows() != null) {
			try {
				appCategoryModel.getPageUtil().setPageSize(Integer.parseInt(pageParams.getRows()));// 显示X条
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getSort() != null) {
			try {
				appCategoryModel.getPageUtil().setOrderByCondition(pageParams.getSort()+" "+pageParams.getOrder()); // 排序字段名称
			} catch (Exception e) {
				log.error(e);
			}
		}

		// appCategoryModel.getPageUtil().setOrderDirection(true); //true false 表示 正序与倒序
        String str="";
        String suffix = "}";
        if(pageParams.getGridName() != null){
        	str="[";
        	suffix="]}";
        }
		List<AppCategoryModel> listAppCategoryModel = null;
		StringBuilder center = new StringBuilder();

		if (pageParams.getSearchType() != null) {
			if (pageParams.getSearchType().equals("1")) { // 模糊搜索
				appCategoryModel.getPageUtil().setLike(true);
				listAppCategoryModel = appCategoryService.selectByModel(appCategoryModel);
				center.append("{\"total\":\"" + appCategoryModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
			} 
		} else {
			if (pageParams.getGridName() == null) {
				listAppCategoryModel = appCategoryService.selectByModel(appCategoryModel);
				center.append("{\"total\":\"" + appCategoryModel.getPageUtil().getRowCount() + "\",\"rows\":");
				suffix = "}";
			} else {
			}
		}

		if (pageParams.getGridName() == null) { //datagrid
			center.append(JSON.toJSONString(listAppCategoryModel));
		} else {
		}
		center.append(suffix);
		result = center.toString();
		System.out.println("json:" + result);
		return result;
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>导出<br>
	 * @param response
	 * @param tbcTempModel
	 */
	@RequestMapping("export.html")
	public void appCategoryExport(HttpServletResponse response,AppCategoryModel appCategoryModel) {
		List<AppCategoryModel> listAppCategoryModel=null;
		try {
			listAppCategoryModel=appCategoryService.selectByModel(appCategoryModel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, List<AppCategoryModel>> exportMap = new HashMap<String, List<AppCategoryModel>>();
		exportMap.put("sheet", listAppCategoryModel);
		Workbook wb = excelUtil.writeExcel2(exportMap, null, 1);
		String filename=util.getDate(1, null)+".xls";
		OutputStream out=null;
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename="+filename);
		try {
			out = response.getOutputStream();
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    /**
     * 
     * <br>
     * <b>功能：</b>保存 AppCategoryModel信息<br>
     * @return
     */
	@RequestMapping("save.html") 
	@ResponseBody
	public void save(AppCategoryModel appCategoryModel,HttpServletResponse response){
		try{
			  if(appCategoryModel.getId() != null ){
				    appCategoryService.updateByPrimaryKey(appCategoryModel);
					util.toJsonMsg(response, 0, null);
			   }
		      		      
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			e.printStackTrace();
		}
	}
	
    /**
     * 
     * <br>
     * <b>功能：</b>删除 AppCategoryModel<br>
     * @param ids
     * @param response
     */
	@RequestMapping("del.html") 
	public void del(String[] ids,HttpServletResponse response){
		 System.out.println("del-ids:"+Arrays.toString(ids));
		try{
			appCategoryService.deleteByPrimaryKeys(ids);
			util.toJsonMsg(response, 0, null);
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}

	/**
     * 
     * <br>
     * <b>功能：</b>增加操作 AppCategoryModel<br>
     * @return
     */
	@RequestMapping("add.html")
	public void add(AppCategoryModel appCategoryModel,HttpServletResponse response){
		String id=util.getUid();
		appCategoryModel.setId(id);
			try {
				appCategoryService.insert(appCategoryModel);//入库
				util.toJsonMsg(response, 0, null);
			} catch (Exception e) {
				util.toJsonMsg(response, 1, null);
				e.printStackTrace();
			}
	}
	

}
