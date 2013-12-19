package controller;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AppBookModel;
import model.AppBookPageModel;
import model.AppBookPageSentencesModel;

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

import service.AppBookPageSentencesService;
import service.AppBookPageService;
import service.AppBookService;
import util.core.ExcelUtil;
import util.core.MethodUtil;
import util.core.PageParams;
import util.core.ZipUtil;
import util.spring.MyTimestampPropertyEdit;
import util.spring.SessionUtil;

import com.alibaba.fastjson.JSON;

import controller.admin.BaseController;
@Controller
@RequestMapping("/app/book/")
public class AppBookController extends BaseController{	private final static Logger log= Logger.getLogger(AppBookController.class);
	private static  MethodUtil util = new MethodUtil();
	
  
	// 服务类1
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppBookService<AppBookModel> appBookService; 
	
	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppBookPageService<AppBookPageModel> appBookPageService; 
	
	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppBookPageSentencesService<AppBookPageSentencesModel> appBookPageSentencesService; 
	
	@Autowired
	service.TbsMenuService<model.TbsMenuModel> tbsMenuService;
	
	@InitBinder//必须有一个参数WebDataBinder 日期类型装换
	public void initBinder(WebDataBinder binder) {
		    binder.registerCustomEditor(Timestamp.class,new MyTimestampPropertyEdit()); //使用自定义属性编辑器
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
		return new ModelAndView("app/book/index", modelMap);
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("baseDlg.html")
	public String baseDlg(){
		return "app/book/baseDlg";
	}
	   
  
   /**
     * 
     * <br>
     * <b>功能：</b>方法功能描述<br>
     * @param pageParams
     * @param appBookModel
     * @return
     * @throws Exception
     */
	@RequestMapping("data.html")
	@ResponseBody
	public String data(PageParams pageParams, AppBookModel appBookModel) throws Exception {
		System.out.println("pageParams:" + pageParams + "|appBookModel:" + appBookModel);
		appBookModel.getPageUtil().setPaging(true);
		String result = "[]";
		if (pageParams.getPage() != null) {
			try {
				appBookModel.getPageUtil().setPageId(Integer.parseInt(pageParams.getPage())); // 当前页
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getRows() != null) {
			try {
				appBookModel.getPageUtil().setPageSize(Integer.parseInt(pageParams.getRows()));// 显示X条
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getSort() != null) {
			try {
				appBookModel.getPageUtil().setOrderByCondition(pageParams.getSort()+" "+pageParams.getOrder()); // 排序字段名称
			} catch (Exception e) {
				log.error(e);
			}
		}
 
        String str="";
        String suffix = "}";
        if(pageParams.getGridName() != null){
        	str="[";
        	suffix="]}";
        }
		List<AppBookModel> listAppBookModel = null;
		StringBuilder center = new StringBuilder();

		if (pageParams.getSearchType() != null) {
			if (pageParams.getSearchType().equals("1")) { // 模糊搜索
				appBookModel.getPageUtil().setLike(true);
				listAppBookModel = appBookService.selectByModel(appBookModel);
				center.append("{\"total\":\"" + appBookModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
			} 
		} else {
			if (pageParams.getGridName() == null) {
				listAppBookModel = appBookService.selectByModel(appBookModel);
				center.append("{\"total\":\"" + appBookModel.getPageUtil().getRowCount() + "\",\"rows\":");
				suffix = "}";
			} else {
			}
		}

		if (pageParams.getGridName() == null) { //datagrid
			center.append(JSON.toJSONString(listAppBookModel));
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
	public void appCategoryExport(HttpServletResponse response,AppBookModel appBookModel) {
		List<AppBookModel> listAppBookModel=null;
		try {
			listAppBookModel=appBookService.selectByModel(appBookModel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, List<AppBookModel>> exportMap = new HashMap<String, List<AppBookModel>>();
		exportMap.put("sheet", listAppBookModel);
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
     * <b>功能：</b>保存 AppBookModel信息<br>
     * @return
     */
	@RequestMapping("save.html") 
	@ResponseBody
	public void save(AppBookModel appBookModel,HttpServletResponse response){
		try{
			  if(appBookModel.getId() != null ){
				    appBookService.updateByPrimaryKey(appBookModel);
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
     * <b>功能：</b>删除 AppBookModel<br>
     * @param ids
     * @param response
     */
	@RequestMapping("del.html") 
	public void del(String[] ids,HttpServletResponse response){
		 System.out.println("del-ids:"+Arrays.toString(ids));
		try{
			appBookService.delBookByPrimaryKeys(ids);
			util.toJsonMsg(response, 0, null);
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}

	/**
     * 
     * <br>
     * <b>功能：</b>增加操作 AppBookModel<br>
     * @return
     */
	@RequestMapping("add.html")
	public void add(AppBookModel appBookModel,HttpServletResponse response){
		String id=util.getUid();
		appBookModel.setId(id);
			try {
				appBookService.insert(appBookModel);//入库
				util.toJsonMsg(response, 0, null);
			} catch (Exception e) {
				util.toJsonMsg(response, 1, null);
				e.printStackTrace();
			}
	}
	
	
	@RequestMapping("packBook.html") 
	public void packBook(String[] ids,HttpServletResponse response,HttpServletRequest request){
		 System.out.println("pack-ids:"+Arrays.toString(ids));
		try{
			
			String ctxPath = request.getSession().getServletContext().getRealPath(
			"/")
			+ File.separator ;
			
			List<String> inList;
			for (Object k : ids) {
				inList = new ArrayList<String>();
				ZipUtil z = new ZipUtil();

				AppBookModel book =  appBookService.selectByPrimaryKey(k);
				if(book.getCover()!=null&&!"".equals(book.getCover()))
				{
					File file = new File(ctxPath+"upload"+book.getCover());
					if (file.exists()) {
						inList.add(ctxPath+"upload"+book.getCover());
					}
				}
			 
				Map<String, Object> pageMap = new HashMap<String, Object>();
				pageMap.put("bookID", book.getId());
				List<AppBookPageModel> pageList = appBookPageService.selectByMap(pageMap);
				Map<String, Object> pageContentMap;
				for (AppBookPageModel page : pageList) {
					if(page.getImg()!=null&&!"".equals(page.getImg()))	
					{
						File file = new File(ctxPath+"upload"+page.getImg());
						if (file.exists()) {
							inList.add(ctxPath+"upload"+page.getImg());
						}
					}
						
					if (page.getType() == AppBookPageService.PAGE_TYPE_READ) {
						pageContentMap = new HashMap<String, Object>();
						pageMap.put("bookPageID", page.getId());
						List<AppBookPageSentencesModel> pageSentencesList =  appBookPageSentencesService.selectByMap(pageContentMap);
						for(AppBookPageSentencesModel sentence :  pageSentencesList)
						{
							if(sentence.getAudio()!=null&&!"".equals(sentence.getAudio()))	
							{
								File file = new File(ctxPath+"upload"+sentence.getAudio());
								if (file.exists()) {
									inList.add(ctxPath+"upload"+sentence.getAudio());
								}
							}
						}
					}
				} 
				String packPath = ctxPath+"package\\book\\"+book.getId()+"\\";
				File file = new File(packPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				book.setVersion(book.getVersion()+1);
				book.setPack("package\\book\\"+book.getId()+"\\"+book.getId()+"_"+book.getVersion()+".zip");
				if(z.zip(inList, packPath+book.getId()+"_"+book.getVersion()+".zip"))
					appBookService.updateByPrimaryKey(book);

			}
			
			util.toJsonMsg(response, 0, null);
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}
	

}
