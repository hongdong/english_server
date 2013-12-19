package model;

public class AppBookModel extends BaseModel {

	private String id; // 主键
	private String name; // 用户名
	private String description; // 密码
	private String category;// 类型ID
	private java.sql.Timestamp createTime; // 时间
	private String cover; // 封面
	private int version; //打包版本号
	private String pack; //打包目录

	
	
	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>主键<br>
	 * 
	 * @return id
	 */

	public String getId() {
		return id;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>主键<br>
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>时间<br>
	 * 
	 * @return createTime
	 */
	@com.alibaba.fastjson.annotation.JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>时间<br>
	 * 
	 * @param createTime
	 */
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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
