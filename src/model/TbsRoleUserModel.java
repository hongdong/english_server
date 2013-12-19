package model;

public class TbsRoleUserModel extends BaseModel {

    private String id; //主键
    private String userId; //用户主键
    private String roleId; //角色主键
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
	 * <b>功能：</b>用户主键<br>
	 * @return userId
	 */
		
	public String getUserId(){
	   return userId;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>用户主键<br>
	 * @param userId
	 */
	public void setUserId(String userId){
	   this.userId=userId;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>角色主键<br>
	 * @return roleId
	 */
		
	public String getRoleId(){
	   return roleId;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>角色主键<br>
	 * @param roleId
	 */
	public void setRoleId(String roleId){
	   this.roleId=roleId;
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
