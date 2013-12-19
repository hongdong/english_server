package model;

public class AppBookPageSentencesModel extends BaseModel {

	private String id; // 主键
	private String bookPageID;
	private String sentence;
	private String audio;
	private int sort;
	
	public AppBookPageSentencesModel() {
	}

	public AppBookPageSentencesModel(String bookPageID) {
		super();
		this.bookPageID = bookPageID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookPageID() {
		return bookPageID;
	}

	public void setBookPageID(String bookPageID) {
		this.bookPageID = bookPageID;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
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
