package controller.admin;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TbsLoginLogModel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import service.TbsLoginLogService;
import util.core.ExcelUtil;
import util.core.MethodUtil;
import util.core.PageParams;
import util.spring.MyTimestampPropertyEdit;
import util.spring.SessionUtil;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/admin/TbsLoginLog/")
public class TbsLoginLogControllerAdmin extends BaseController{	private final static Logger log= Logger.getLogger(TbsLoginLogControllerAdmin.class);
	private static  MethodUtil util = new MethodUtil();
	
	@Autowired
	service.TbsMenuService<model.TbsMenuModel> tbsMenuService;

	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private TbsLoginLogService<TbsLoginLogModel> tbsLoginLogService; 
	
	
	
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
						buttons.add(button);//.replaceAll("&apos;", "'").replaceAll("&quot;", "\"")
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("buttons", buttons);
		return new ModelAndView("admin/TbsLoginLog/index", modelMap);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */	
	@RequestMapping("charts.html")
	public String charts(){
		return "admin/TbsLoginLog/charts";
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>图表<br>
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param chartsName
	 * @param type
	 */
	@RequestMapping("chartsJson.html")
	public void chartsJson(HttpServletResponse response,String startTime,String endTime,String chartsName,Integer type) {
		String resultJson="[]";
		String data = null;
		String categories=null;
		String startTimeFormat=null;
		String endTimeFormat=null;
		String groupTimeFormat=null;
		String year="%Y";
		String month="%m";
		String day="%d";
		String hour="%H";
		String minute="%i";
		String second="%s";
		Map<String, String> paramMap = new HashMap<String, String>();
		if(null==chartsName){
			chartsName="line";
		}
		if(null==type){
			type=4;
		}
		switch (type) {
			case 1: //年
				groupTimeFormat = year;
				break;
			case 2://月
				startTimeFormat = year+"-00";
				endTimeFormat = year+"-12";
				groupTimeFormat = year+"-"+month;
				break;
			case 3://日
				startTimeFormat = year+"-"+month+"-01";
				endTimeFormat = year+"-"+month+"-31";
				groupTimeFormat = year+"-"+month+"-"+day;
				break;
			case 4://时
				startTimeFormat = year+"-"+month+"-"+day+" 00";
				endTimeFormat = year+"-"+month+"-"+day+" 24";
				groupTimeFormat = year+"-"+month+"-"+day+" "+hour;
				break;
			case 5://分
				startTimeFormat = year+"-"+month+"-"+day+" "+hour+":01";
				endTimeFormat = year+"-"+month+"-"+day+" "+hour+":59";
				groupTimeFormat = year+"-"+month+"-"+day+" "+hour+":"+minute;
				break;
			case 6://秒
				startTimeFormat = year+"-"+month+"-"+day+" "+hour+":"+minute+":00";
				endTimeFormat = year+"-"+month+"-"+day+" "+hour+":"+minute+":59";
				groupTimeFormat = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
				break;
			default:
				break;
		}
		paramMap.put("startTimeFormat", startTimeFormat);
		paramMap.put("endTimeFormat", endTimeFormat);
		paramMap.put("groupTimeFormat", groupTimeFormat);
		try {
			List<Map<?, ?>> list = tbsLoginLogService.charts(paramMap);
			if(null!=list && list.size()>0){
				System.out.println("list:" + list.size());
				data="\"data\":[";
				categories="\"categories\":[";
				for (int i = 0; i < list.size(); i++) {
					Map<?, ?> map = list.get(i);
					int count = ((Long) map.get("COUNT(*)")).intValue();
					String time = map.get("createTime").toString();
					data+=count+",";
					categories+="\""+time+"\",";
					System.out.println("count:" + count + "|time:" + time);
				}
				data=data.substring(0,data.length()-1);
				data=data+"]";
				categories=categories.substring(0,categories.length()-1);
				categories=categories+"]";
				System.out.println(data);
				System.out.println(categories);
				resultJson="{"+data+","+categories+"}";
				util.toJsonPrint(response, resultJson);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		util.toJsonPrint(response, resultJson);
	}


	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("baseDlg.html")
	public String baseDlg(){
		return "admin/TbsLoginLog/baseDlg";
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("importDlg.html")
	public String importDlg(){
		return "admin/TbsLoginLog/importDlg";
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("searchDlg.html")
	public String searchDlg(){
		return "admin/TbsLoginLog/searchDlg";
	}
	   
   
   /**
     * 
     * <br>
     * <b>功能：</b>方法功能描述<br>
     * @param pageParams
     * @param tbsLoginLogModel
     * @return
     * @throws Exception
     */
	@RequestMapping("data.html")
	@ResponseBody
	public String data(PageParams pageParams, TbsLoginLogModel tbsLoginLogModel) throws Exception {
		System.out.println("pageParams:" + pageParams + "|tbsLoginLogModel:" + tbsLoginLogModel);
		tbsLoginLogModel.getPageUtil().setPaging(true);
		String result = "[]";
		if (pageParams.getPage() != null) {
			try {
				tbsLoginLogModel.getPageUtil().setPageId(Integer.parseInt(pageParams.getPage())); // 当前页
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getRows() != null) {
			try {
				tbsLoginLogModel.getPageUtil().setPageSize(Integer.parseInt(pageParams.getRows()));// 显示X条
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getSort() != null) {
			try {
				tbsLoginLogModel.getPageUtil().setOrderByCondition(pageParams.getSort()); // 排序字段名称
			} catch (Exception e) {
				log.error(e);
			}
		}

		// tbsLoginLogModel.getPageUtil().setOrderDirection(true); //true false 表示 正序与倒序
        String str="";
        String suffix = "}";
        if(pageParams.getGridName() != null){
        	str="[";
        	suffix="]}";
        }
		List<TbsLoginLogModel> listTbsLoginLogModel = null;
		StringBuilder center = new StringBuilder();

		if (pageParams.getSearchType() != null) {
			if (pageParams.getSearchType().equals("1")) { // 模糊搜索
				tbsLoginLogModel.getPageUtil().setLike(true);
				listTbsLoginLogModel = tbsLoginLogService.selectByModel(tbsLoginLogModel);
				center.append("{\"total\":\"" + tbsLoginLogModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
			} else {
				StringBuffer sb = new StringBuffer(); // 高级查询
				String[] searchColumnNameArray = pageParams.getSearchColumnNames().split("\\,");
				String[] searchAndsArray = pageParams.getSearchAnds().split("\\,");
				String[] searchConditionsArray = pageParams.getSearchConditions().split("\\,");
				String[] searchValsArray = pageParams.getSearchVals().split("\\,");
				System.out.println(Arrays.toString(searchColumnNameArray));
				for (int i = 0; i < searchColumnNameArray.length; i++) {
					if (searchColumnNameArray[i].trim().length() > 0 && searchConditionsArray[i].trim().length() > 0) {
						if (i == 0) {
							sb.append(searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " '" + searchValsArray[i].trim() + "'");
						} else {
							sb.append(" " + searchAndsArray[i].trim() + " " + searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " '" + searchValsArray[i].trim() + "'");
						}
					}
				}
				if (sb.length() > 0) {
					System.out.println("searchCondition:" + sb.toString());
					tbsLoginLogModel.getPageUtil().setAndCondition(sb.toString());
					listTbsLoginLogModel = tbsLoginLogService.selectByModel(tbsLoginLogModel);
					center.append("{\"total\":\"" + tbsLoginLogModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
				}
			}
		} else {
			if (pageParams.getGridName() == null) {
				listTbsLoginLogModel = tbsLoginLogService.selectByModel(tbsLoginLogModel);
				center.append("{\"total\":\"" + tbsLoginLogModel.getPageUtil().getRowCount() + "\",\"rows\":");
				suffix = "}";
			} else {
			}
		}

		if (pageParams.getGridName() == null) { //datagrid
			center.append(JSON.toJSONString(listTbsLoginLogModel));
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
	public void tbsLoginLogExport(HttpServletResponse response,TbsLoginLogModel tbsLoginLogModel) {
		List<TbsLoginLogModel> listTbsLoginLogModel=null;
		try {
			listTbsLoginLogModel=tbsLoginLogService.selectByModel(tbsLoginLogModel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, List<TbsLoginLogModel>> exportMap = new HashMap<String, List<TbsLoginLogModel>>();
		exportMap.put("sheet", listTbsLoginLogModel);
		Workbook wb = excelUtil.writeExcel2(exportMap, null, 1);
		String filename=util.getDate(1, null)+".xls";
		OutputStream out=null;
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename="+filename);
		//response.addHeader("Content-Length", "");
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
	 * <b>功能：</b>导入<br>
	 * @param request
	 * @param response
	 */
	@RequestMapping("import.html")
	public void tbsLoginLogImport(HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			ExcelUtil excelUtil = new ExcelUtil();
			String[] columnName=new String[]{"id","href","text"};
			try {
				Map<String, List<TbsLoginLogModel>> ml=excelUtil.readExcel(new ByteArrayInputStream(mf.getBytes()), columnName, 2, TbsLoginLogModel.class);
				for(Map.Entry<String, List<TbsLoginLogModel>> map:ml.entrySet()){
					List<TbsLoginLogModel> lt=map.getValue();
					for(int i=0;i<lt.size();i++){
						try {
							tbsLoginLogService.insert(lt.get(i));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				util.toJsonMsg(response, 1, null);
				return;
			}
		}
		util.toJsonMsg(response, 0, null);
	}
	
    /**
     * 
     * <br>
     * <b>功能：</b>增加 TbsLoginLogModel<br>
     * @return
     */
	@RequestMapping("add.html")
	public void add(TbsLoginLogModel tbsLoginLogModel,HttpServletResponse response){
		tbsLoginLogModel.setId(util.getUid());//设置主键
		System.out.println("tbsLoginLogModel:"+tbsLoginLogModel.toString());
	    try {
			if(tbsLoginLogService.insert(tbsLoginLogModel)>0){
				util.toJsonMsg(response, 0, null);
				return;
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	    util.toJsonMsg(response, 1, null);
	}

    /**
     * 
     * <br>
     * <b>功能：</b>保存 TbsLoginLogModel<br>
     * @return
     */
	@RequestMapping("save.html") 
	public void save(TbsLoginLogModel tbsLoginLogModel,HttpServletResponse response){
		try{
		 	if(tbsLoginLogService.updateByPrimaryKey(tbsLoginLogModel)>0){
			    util.toJsonMsg(response, 0, null);
			    return;
			 }
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			e.printStackTrace();
		}
	}
	
    /**
     * 
     * <br>
     * <b>功能：</b>删除 TbsLoginLogBean<br>
     * @param ids
     * @param response
     */
	@RequestMapping("del.html") 
	public void del(String[] ids,HttpServletResponse response){
		System.out.println("del-ids:"+Arrays.toString(ids));
		try{
			 if(null!=ids && ids.length>0){
				 if(tbsLoginLogService.deleteByPrimaryKeys(ids)>0){
					 util.toJsonMsg(response, 0, null);
					 return;
				 }
			 }
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}
	



}
