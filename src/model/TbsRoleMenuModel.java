package model;

public class TbsRoleMenuModel extends BaseModel {

    private String id; //主键
    private String roleId; //角色主键
    private String menuIdFun; //功能主键
    private String menuId; //菜单主键
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
	 * <b>功能：</b>功能主键<br>
	 * @return menuIdFun
	 */
		
	public String getMenuIdFun(){
	   return menuIdFun;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>功能主键<br>
	 * @param menuIdFun
	 */
	public void setMenuIdFun(String menuIdFun){
	   this.menuIdFun=menuIdFun;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>菜单主键<br>
	 * @return menuId
	 */
		
	public String getMenuId(){
	   return menuId;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>菜单主键<br>
	 * @param menuId
	 */
	public void setMenuId(String menuId){
	   this.menuId=menuId;
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
