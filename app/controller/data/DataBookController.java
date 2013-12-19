package controller.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AppBookModel;
import model.AppBookPageModel;
import model.AppBookPageSentencesModel;
import model.AppCategoryModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.AppBookPageSentencesService;
import service.AppBookPageService;
import service.AppBookService;
import service.AppCategoryService;
import util.core.PageParams;

import com.alibaba.fastjson.JSON;

import controller.admin.BaseController;

@Controller
@RequestMapping("/data/book/")
public class DataBookController extends BaseController {
	private final static Logger log = Logger
			.getLogger(DataBookController.class);

	// 服务类
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppBookService<AppBookModel> appBookService;

	// 服务类
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppCategoryService<AppCategoryModel> appCategoryService;

	// 服务类
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppBookPageService<AppBookPageModel> appBookPageService;

	// 服务类
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private AppBookPageSentencesService<AppBookPageSentencesModel> appBookPageSentencesService;

	/**
	 * 
	 * <br>
	 * <b>功能：</b>方法功能描述<br>
	 * 
	 * @param pageParams
	 * @param appCategoryModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("categorylist")
	@ResponseBody
	public String getCategoryList(PageParams pageParams,
			AppCategoryModel appCategoryModel) throws Exception {
		System.out.println("pageParams:" + pageParams + "|appCategoryModel:"
				+ appCategoryModel);
		appCategoryModel.getPageUtil().setPaging(true);
		String result = null;
		if (pageParams.getPage() != null) {
			try {
				appCategoryModel.getPageUtil().setPageId(
						Integer.parseInt(pageParams.getPage())); // 当前页
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getRows() != null) {
			try {
				appCategoryModel.getPageUtil().setPageSize(
						Integer.parseInt(pageParams.getRows()));// 显示X条
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getSort() != null) {
			try {
				appCategoryModel.getPageUtil().setOrderByCondition(
						pageParams.getSort() + " " + pageParams.getOrder()); // 排序字段名称
			} catch (Exception e) {
				log.error(e);
			}
		}

		// appCategoryModel.getPageUtil().setOrderDirection(true); //true false
		// 表示 正序与倒序

		List<AppCategoryModel> listAppCategoryModel = null;

		if (pageParams.getSearchType() != null) {
			if (pageParams.getSearchType().equals("1")) { // 模糊搜索
				appCategoryModel.getPageUtil().setLike(true);
				listAppCategoryModel = appCategoryService
						.selectByModel(appCategoryModel);
			}
		} else {

			listAppCategoryModel = appCategoryService
					.selectByModel(appCategoryModel);
		}

		List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();

		Map<String, Object> temp = null;
		for (AppCategoryModel category : listAppCategoryModel) {
			temp = new HashMap<String, Object>();
			temp.put("id", category.getId());
			temp.put("name", category.getName());
			categoryList.add(temp);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", categoryList);
		resultMap.put("pageUtil", listAppCategoryModel.get(0).getPageUtil());
		resultMap.put("total", appCategoryModel.getPageUtil().getRowCount());
		result = JSON.toJSONString(resultMap);

		System.out.println("json:" + result);
		return result;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>方法功能描述<br>
	 * 
	 * @param pageParams
	 * @param appBookModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("booklist")
	@ResponseBody
	public String getBookList(PageParams pageParams, AppBookModel appBookModel)
			throws Exception {
		System.out.println("pageParams:" + pageParams + "|appBookModel:"
				+ appBookModel);
		appBookModel.getPageUtil().setPaging(true);
		String result = null;
		if (pageParams.getPage() != null) {
			try {
				appBookModel.getPageUtil().setPageId(
						Integer.parseInt(pageParams.getPage())); // 当前页
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getRows() != null) {
			try {
				appBookModel.getPageUtil().setPageSize(
						Integer.parseInt(pageParams.getRows()));// 显示X条
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (pageParams.getSort() != null) {
			try {
				appBookModel.getPageUtil().setOrderByCondition(
						pageParams.getSort() + " " + pageParams.getOrder()); // 排序字段名称
			} catch (Exception e) {
				log.error(e);
			}
		}

		List<AppBookModel> listAppBookModel = null;

		if (pageParams.getSearchType() != null) {
			if (pageParams.getSearchType().equals("1")) { // 模糊搜索
				appBookModel.getPageUtil().setLike(true);
				listAppBookModel = appBookService.selectByModel(appBookModel);

			}
		} else {

			listAppBookModel = appBookService.selectByModel(appBookModel);

		}
		List<Map<String, Object>> bookList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> temp = null;
		for (AppBookModel book : listAppBookModel) {
			temp = new HashMap<String, Object>();
			temp.put("category", book.getCategory());
			temp.put("cover", book.getCover());
			temp.put("creattime", book.getCreateTime());
			temp.put("description", book.getDescription());
			temp.put("id", book.getId());
			temp.put("name", book.getName());
			temp.put("pack", book.getPack());
			temp.put("version", book.getVersion());
			bookList.add(temp);
		}
		resultMap.put("rows", bookList);
		resultMap.put("total", appBookModel.getPageUtil().getRowCount());
		resultMap.put("pageUtil", listAppBookModel.get(0).getPageUtil());
		result = JSON.toJSONString(resultMap);

		System.out.println("json:" + result);
		return result;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：返回书籍中一页的内容,用来前台客户端的异步调用来在线看书，不需要提前下载全本。（未完成）
	 * 
	 * @param bookId
	 *            书籍的ID
	 * @param sort
	 *            书籍的具体页数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("contentBySort")
	@ResponseBody
	public String getBookContentBySort(String bookId, int sort)
			throws Exception {

		String result = "[]";

		AppBookPageModel model = new AppBookPageModel();
		model.setBookID(bookId);
		model.setSort(sort);
		AppBookPageModel page = appBookPageService.selectByModel(model).get(0);
		List<AppBookPageSentencesModel> sentencess = appBookPageSentencesService
				.selectBySql("select * from AppBookSentences where bookPageID="
						+ page.getId());
		System.out.println(page.getImg() + sentencess);
		// 书籍一页的内容json:{"picture":"url","sentences":[{"sentence":"句子1","audio":"url"},{"sentence":"句子2","audio":"url"},{"sentence":"句子3","audio":"url"}]}

		return result;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：返回整本书籍的索引内容（不包括资源信息），用于下载用。
	 * 
	 * @param bookId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("content")
	@ResponseBody
	public String getBookContent(String bookId) throws Exception {

		String result = null;
		String pageId = null;

		List<AppBookPageSentencesModel> sentencess;
		List<Map<String, String>> sentenceList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		AppBookPageModel model = new AppBookPageModel();
		model.setBookID(bookId);
		List<AppBookPageModel> pages = appBookPageService.selectByModel(model);// 拿到整本书的所有页
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		for (AppBookPageModel page : pages) {
			pageId = page.getId();
			sentencess = appBookPageSentencesService
					.selectByEntiry(new AppBookPageSentencesModel(pageId));
			for (AppBookPageSentencesModel sentences : sentencess) {
				map.put("sentence", sentences.getSentence());
				map.put("audio", sentences.getAudio());
				sentenceList.add(map);
			}
			pageMap.put("sort", page.getSort());

			pageMap.put("picture", page.getImg());
			pageMap.put("sentence", sentenceList);
			jsonList.add(pageMap);
		}
		result = JSON.toJSONString(jsonList);
		System.out.println(result);

		// json格式:[{"sort":"1","picture":"name","sentence":[{"sentence":"句子1","audio":"name"},{"sentence":"句子2","audio":"name"}]},{"sort":"2","picture":"name","sentence":[{"sentence":"句子1","audio":"name"},{"sentence":"句子2","audio":"name"}]}]

		return result;
	}
}
