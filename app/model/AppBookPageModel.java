package model;

public class AppBookPageModel extends BaseModel {

	private String id; // 主键 
	private String bookID;
	private String title;
	private String img;
	private int type;
	private int sort;

	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getBookID() {
		return bookID;
	}



	public void setBookID(String bookID) {
		this.bookID = bookID;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}


 


	public int getSort() {
		return sort;
	}



	public void setSort(int sort) {
		this.sort = sort;
	}



	/**
	 * 
	 * <br>
	 * <b>功能：</b>重写<br>
	 * 
	 * @return
	 */
	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
		// return ${SQL.toString}
	}

}
