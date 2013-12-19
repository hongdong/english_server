package model;
import util.core.PageUtil;
/**
 * 
 * <br>
 * <b>功能：</b>模型-处理分页查询功能<br>
 */
public class BaseModel {

    /**
     * 
     * <br>
     * <b>功能：</b>分页实现<br>
     * @param navigate
     */
	private PageUtil pageUtil=new PageUtil();

	public PageUtil getPageUtil() {
		return pageUtil;
	}
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}
	
}
