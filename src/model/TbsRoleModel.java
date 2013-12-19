package model;

public class TbsRoleModel extends BaseModel {

    private String id; //主键
    private String name; //角色
    private String text; //所有权限
    private java.sql.Timestamp createTime; //时间
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>主键<br>
	 * @return id
	 */
		
	public String getId(){
	   return id;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>主键<br>
	 * @param id
	 */
	public void setId(String id){
	   this.id=id;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>角色<br>
	 * @return name
	 */
		
	public String getName(){
	   return name;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>角色<br>
	 * @param name
	 */
	public void setName(String name){
	   this.name=name;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>所有权限<br>
	 * @return text
	 */
		
	public String getText(){
	   return text;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>所有权限<br>
	 * @param text
	 */
	public void setText(String text){
	   this.text=text;
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
	
   

}
