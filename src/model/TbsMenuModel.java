package model;

public class TbsMenuModel extends BaseModel {

    private String id; //唯一
    private String parentId; //父节点
    private String name; //名称
    private Integer isMenu; //菜单类型
    private Integer type; //加载方式
    private Integer sortNumber; //排序
    private String url; //地址
    private String button; //按钮
    private Integer status; //状态
    private java.sql.Timestamp createTime; //时间
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>唯一<br>
	 * @return id
	 */
		
	public String getId(){
	   return id;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>唯一<br>
	 * @param id
	 */
	public void setId(String id){
	   this.id=id;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>父节点<br>
	 * @return parentId
	 */
		
	public String getParentId(){
	   return parentId;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>父节点<br>
	 * @param parentId
	 */
	public void setParentId(String parentId){
	   this.parentId=parentId;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>名称<br>
	 * @return name
	 */
		
	public String getName(){
	   return name;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>名称<br>
	 * @param name
	 */
	public void setName(String name){
	   this.name=name;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>菜单类型<br>
	 * @return isMenu
	 */
		
	public Integer getIsMenu(){
	   return isMenu;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>菜单类型<br>
	 * @param isMenu
	 */
	public void setIsMenu(Integer isMenu){
	   this.isMenu=isMenu;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>加载方式<br>
	 * @return type
	 */
		
	public Integer getType(){
	   return type;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>加载方式<br>
	 * @param type
	 */
	public void setType(Integer type){
	   this.type=type;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>排序<br>
	 * @return sortNumber
	 */
		
	public Integer getSortNumber(){
	   return sortNumber;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>排序<br>
	 * @param sortNumber
	 */
	public void setSortNumber(Integer sortNumber){
	   this.sortNumber=sortNumber;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>地址<br>
	 * @return url
	 */
		
	public String getUrl(){
	   return url;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>地址<br>
	 * @param url
	 */
	public void setUrl(String url){
	   this.url=url;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>按钮<br>
	 * @return button
	 */
		
	public String getButton(){
	   return button;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>按钮<br>
	 * @param button
	 */
	public void setButton(String button){
	   this.button=button;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>状态<br>
	 * @return status
	 */
		
	public Integer getStatus(){
	   return status;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>状态<br>
	 * @param status
	 */
	public void setStatus(Integer status){
	   this.status=status;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>时间<br>
	 * @return createTime
	 */
	@com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")	
	public java.sql.Timestamp getCreateTime(){
	   return createTime;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>时间<br>
	 * @param createTime
	 */
	public void setCreateTime(java.sql.Timestamp createTime){
	   this.createTime=createTime;
	}
    
	/**
	 * 
	 * <br>
	 * <b>功能：</b>重写<br>
	 * @return
	 */
    public String toString(){
	  return com.alibaba.fastjson.JSON.toJSONString(this);
	  // return ${SQL.toString}
    }
	
	///////////////////////////增加/////////////////////////
	
	
    private java.util.List<TbsMenuModel> listTbsMenuModel;

	public java.util.List<TbsMenuModel> getListTbsMenuModel() {
		return listTbsMenuModel;
	}
	
	public void setListTbsMenuModel(java.util.List<TbsMenuModel> listTbsMenuModel) {
		this.listTbsMenuModel = listTbsMenuModel;
	}
	
   

}
