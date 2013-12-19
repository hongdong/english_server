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

import model.TbsMenuModel;
import model.TbsRoleMenuModel;

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

import service.TbsMenuService;
import service.TbsRoleMenuService;
import util.core.ExcelUtil;
import util.core.MethodUtil;
import util.core.PageParams;
import util.spring.MyTimestampPropertyEdit;
import util.spring.SessionUtil;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/admin/TbsMenu/")
public class TbsMenuControllerAdmin extends BaseController{	private final static Logger log= Logger.getLogger(TbsMenuControllerAdmin.class);
	private static  MethodUtil util = new MethodUtil();
	

	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private TbsMenuService<TbsMenuModel> tbsMenuService; 
	
	
	
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
		return new ModelAndView("admin/TbsMenu/index", modelMap);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */	
	@RequestMapping("charts.html")
	public String charts(){
		return "admin/TbsMenu/charts";
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
			List<Map<?, ?>> list = tbsMenuService.charts(paramMap);
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
		return "admin/TbsMenu/baseDlg";
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("importDlg.html")
	public String importDlg(){
		return "admin/TbsMenu/importDlg";
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("searchDlg.html")
	public String searchDlg(){
		return "admin/TbsMenu/searchDlg";
	}
	   
   
   /**
     * 
     * <br>
     * <b>功能：</b>方法功能描述<br>
     * @param pageParams
     * @param tbsMenuModel
     * @return
     * @throws Exception
     */
	@RequestMapping("data.html")
	@ResponseBody
	public String data(PageParams pageParams, TbsMenuModel tbsMenuModel) throws Exception {
		System.out.println("pageParams:" + pageParams + "|tbsMenuModel:" + tbsMenuModel);
		tbsMenuModel.getPageUtil().setPaging(true);
		String result = "[]";
		if (pageParams.getPage() != null) {
			try {
				tbsMenuModel.getPageUtil().setPageId(Integer.parseInt(pageParams.getPage())); // 当前页
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getRows() != null) {
			try {
				tbsMenuModel.getPageUtil().setPageSize(Integer.parseInt(pageParams.getRows()));// 显示X条
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getSort() != null) {
			try {
				tbsMenuModel.getPageUtil().setOrderByCondition(pageParams.getSort()); // 排序字段名称
			} catch (Exception e) {
				log.error(e);
			}
		}

		// tbsMenuModel.getPageUtil().setOrderDirection(true); //true false 表示 正序与倒序
        String str="";
        String suffix = "}";
        if(pageParams.getGridName() != null){
        	str="[";
        	suffix="]}";
        }
		List<TbsMenuModel> listTbsMenuModel = null;
		StringBuilder center = new StringBuilder();

		if (pageParams.getSearchType() != null) {
			if (pageParams.getSearchType().equals("1")) { // 模糊搜索
				tbsMenuModel.getPageUtil().setLike(true);
				listTbsMenuModel = tbsMenuService.selectByModel(tbsMenuModel);
				center.append("{\"total\":\"" + tbsMenuModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
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
					tbsMenuModel.getPageUtil().setAndCondition(sb.toString());
					listTbsMenuModel = tbsMenuService.selectByModel(tbsMenuModel);
					center.append("{\"total\":\"" + tbsMenuModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
				}
			}
		} else {
			if (pageParams.getGridName() == null) {
				listTbsMenuModel = tbsMenuService.selectByModel(tbsMenuModel);
				center.append("{\"total\":\"" + tbsMenuModel.getPageUtil().getRowCount() + "\",\"rows\":");
				suffix = "}";
			} else {
				if (tbsMenuModel.getId() == null) { // 首次打开
					tbsMenuModel.getPageUtil().setAndCondition("parentId IS NULL");
					tbsMenuModel.getPageUtil().setOrderByCondition("sortNumber");
					listTbsMenuModel = tbsMenuService.selectByModel(tbsMenuModel);
					center.append("{\"total\":\"" + tbsMenuModel.getPageUtil().getRowCount() + "\",\"rows\":"+str);
				} else { // 子节点加载
					tbsMenuModel.setParentId(tbsMenuModel.getId());
					tbsMenuModel.setId(null);
					listTbsMenuModel = tbsMenuService.selectByModel(tbsMenuModel);
					center.append("[");
					suffix = "]";
				}
			}
		}

		if (pageParams.getGridName() == null) { //datagrid
			center.append(JSON.toJSONString(listTbsMenuModel));
		} else {
			if (listTbsMenuModel.size() > 0) { // TreeGrid
				for (int i = 0; i < listTbsMenuModel.size(); i++) {
					TbsMenuModel model = listTbsMenuModel.get(i);
					String json = JSON.toJSONString(model);
					TbsMenuModel model2 = new TbsMenuModel();
					model2.setParentId(model.getId());
					if (tbsMenuService.selectByModelCount(model2) > 0) {
						json = json.substring(0, json.length() - 1);
						json += ",\"state\":\"closed\"}";
					}
					center.append(json + ",");
				}
				center.delete(center.length() - 1, center.length());
			}
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
	public void tbsMenuExport(HttpServletResponse response,TbsMenuModel tbsMenuModel) {
		List<TbsMenuModel> listTbsMenuModel=null;
		try {
			listTbsMenuModel=tbsMenuService.selectByModel(tbsMenuModel);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, List<TbsMenuModel>> exportMap = new HashMap<String, List<TbsMenuModel>>();
		exportMap.put("sheet", listTbsMenuModel);
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
	public void tbsMenuImport(HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			ExcelUtil excelUtil = new ExcelUtil();
			String[] columnName=new String[]{"id","href","text"};
			try {
				Map<String, List<TbsMenuModel>> ml=excelUtil.readExcel(new ByteArrayInputStream(mf.getBytes()), columnName, 2, TbsMenuModel.class);
				for(Map.Entry<String, List<TbsMenuModel>> map:ml.entrySet()){
					List<TbsMenuModel> lt=map.getValue();
					for(int i=0;i<lt.size();i++){
						try {
							tbsMenuService.insert(lt.get(i));
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
     * <b>功能：</b>增加 TbsMenuModel<br>
     * @return
     */
	@RequestMapping("add.html")
	public void add(TbsMenuModel tbsMenuModel,HttpServletResponse response){
		tbsMenuModel.setId(util.getUid());//设置主键
		System.out.println("tbsMenuModel:"+tbsMenuModel.toString());
	    try {
	    	if(null!=tbsMenuModel.getParentId() && tbsMenuModel.getParentId().trim().length()==0){
	    		tbsMenuModel.setParentId(null);
	    	}
			if(tbsMenuService.insert(tbsMenuModel)>0){
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
     * <b>功能：</b>保存 TbsMenuModel<br>
     * @return
     */
	@RequestMapping("save.html") 
	public void save(TbsMenuModel tbsMenuModel,HttpServletResponse response){
		try{
	    	if(null!=tbsMenuModel.getParentId() && tbsMenuModel.getParentId().trim().length()==0){
	    		tbsMenuModel.setParentId(null);
	    	}
		 	if(tbsMenuService.updateByPrimaryKey(tbsMenuModel)>0){
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
     * <b>功能：</b>删除 TbsMenuBean<br>
     * @param ids
     * @param response
     */
	@RequestMapping("del.html") 
	public void del(String[] ids,HttpServletResponse response){
		System.out.println("del-ids:"+Arrays.toString(ids));
		try{
			 if(null!=ids && ids.length>0){
				 if(tbsMenuService.deleteByPrimaryKeys(ids)>0){
					 util.toJsonMsg(response, 0, null);
					 return;
				 }
			 }
		}catch(Exception e){
			util.toJsonMsg(response, 1, null);
			log.error(e);
		}
	}
		
	@RequestMapping("gridTree.html")
	@ResponseBody
	public String gridTree(HttpServletResponse response, String id,String name,String value) throws Exception {
		List<Map<String, Object>> lm;
		Map<String,Object> map=new HashMap<String, Object>();
		if (null == id && null==name) {
			map.put("andCondition", "parentId IS NULL");
			map.put("orderCondition", "sortNumber");
			lm=tbsMenuService.selectByMenuOther(map);
		} else {
			if(null != id){
				map.put("parentId", id);
				lm = tbsMenuService.selectByMenuOther(map);
			}else{
				map.put(name, value);
				lm = tbsMenuService.selectByMenuOther(map);
			}
		}
		map.clear();
		if (lm != null && lm.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < lm.size(); i++) {
				sb.append("{");
				map = lm.get(i);
				Long child = (Long) map.get("child");
				String menuId = (String) map.get("id");
				if (child > 0 ) {
					sb.append("\"state\":\"closed\",");
				}
				sb.append("\"id\":" + "\"" + menuId + "\",");
				sb.append("\"url\":" + "\"" + map.get("url") + "\",");
				sb.append("\"createTime\":" + "\"" + map.get("createTime") + "\",");
				sb.append("\"parentId\":" + "\"" + map.get("parentId") + "\",");
				sb.append("\"type\":" + "\"" + map.get("type") + "\",");
				sb.append("\"sortNumber\":" + "\"" + map.get("sortNumber") + "\",");
				sb.append("\"parent\":" + "\"" + map.get("parent") + "\",");
				sb.append("\"child\":" + "\"" + map.get("child") + "\",");
				sb.append("\"isMenu\":" + "\"" + map.get("isMenu") + "\",");
				sb.append("\"status\":" + "\"" + map.get("status") + "\",");
				//sb.append("\"button\":" + "\"" + map.get("button") + "\",");
				sb.append("\"name\":" + "\"" + map.get("name") + "\"");
				sb.append("},");
			}
			sb = sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
			System.out.println("json:" + sb.toString());
			return sb.toString();
		}
		return "[]";
	}
	
	@Autowired
	TbsRoleMenuService<TbsRoleMenuModel> tbsRoleMenuService;
	@RequestMapping("comboxTree.html")
	@ResponseBody
	public String comboxTree(String id, String roleId, Integer type) throws Exception {
		List<Map<String, Object>> lm;
		Map<String,Object> map=new HashMap<String, Object>();
		//TbsRoleMenuModel tbsRoleMenuModel = new TbsRoleMenuModel();
		if (null == id) {
			map.put("andCondition", "parentId IS NULL");
			lm = tbsMenuService.selectByMenuOther(map);
		} else {
			map.put("parentId", id);
			lm = tbsMenuService.selectByMenuOther(map);
		}
		map.clear();
		if (lm != null && lm.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < lm.size(); i++) {
				sb.append("{");
				map = lm.get(i);
				Long child = (Long) map.get("child");
				String menuId = (String) map.get("id");
				if (roleId != null) {
					TbsRoleMenuModel tbsRoleMenuModel=new TbsRoleMenuModel();
					tbsRoleMenuModel.setRoleId(roleId);
					tbsRoleMenuModel.setMenuIdFun(menuId);
					if (tbsRoleMenuService.selectByEntiry(tbsRoleMenuModel).size() > 0) {
						sb.append("\"checked\":true,");
					}
				}
				// Integer parent=(Integer) map.get("parent");
				if (child > 0) {
					sb.append("\"state\":\"closed\",");
					if (type != null && type != 0) { // 同步递归
						sb.append("\"children\":").append(this.comboxTree(menuId, roleId, type) + ",");
					}
				}
				sb.append("\"id\":" + "\"" + menuId + "\",");
				sb.append("\"text\":" + "\"" + map.get("name") + "\"");
				sb.append("},");
			}
			sb = sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
			System.out.println("json:" + sb.toString());
			return sb.toString();
		}
		return "[]";
	}
	
	




}
