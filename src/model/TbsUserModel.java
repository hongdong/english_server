package model;

public class TbsUserModel extends BaseModel {

    private String id; //主键
    private String username; //用户名
    private String password; //密码
    private String telephone;//电话号码
    private String email;//电子邮箱
    private java.sql.Timestamp createTime; //时间
    private String ip; //ip
    private Integer count; //次数
    private Integer isLock; //锁定
    private java.sql.Timestamp lockTime; //锁定时间
    private Integer failCount; //失败次数
    private Integer isAdmin; //权限类型
    private String portrait;//头像图片
	private String nickname;//昵称
	private String sex;//性别
	private java.sql.Date birthday;//生日日期
	private String description;//个人简介
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>性别<br>
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>性别<br>
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>生日日期<br>
	 * @return birthday
	 */
	public java.sql.Date getDate() {
		return birthday;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>生日日期<br>
	 * @param birthday
	 */
	public void setDate(java.sql.Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>个人简介<br>
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>个人简介<br>
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>昵称<br>
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>昵称<br>
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}    
    /**
     * 
     * <br>
     * <b>功能：</b>头像<br>
     * @return portrait
     */
	public String getPortrait() {
		return portrait;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>头像<br>
	 * @param portrait
	 */
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
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
	 * <b>功能：</b>用户名<br>
	 * @return username
	 */
		
	public String getUsername(){
	   return username;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>用户名<br>
	 * @param username
	 */
	public void setUsername(String username){
	   this.username=username;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>密码<br>
	 * @return password
	 */
		
	public String getPassword(){
	   return password;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>密码<br>
	 * @param password
	 */
	public void setPassword(String password){
	   this.password=password;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>电话<br>		
	 * @return telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>电话<br>
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>电子邮箱<br>
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>电子邮箱<br>
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * <b>功能：</b>ip<br>
	 * @return ip
	 */
		
	public String getIp(){
	   return ip;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>ip<br>
	 * @param ip
	 */
	public void setIp(String ip){
	   this.ip=ip;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>次数<br>
	 * @return count
	 */
		
	public Integer getCount(){
	   return count;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>次数<br>
	 * @param count
	 */
	public void setCount(Integer count){
	   this.count=count;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定<br>
	 * @return isLock
	 */
		
	public Integer getIsLock(){
	   return isLock;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定<br>
	 * @param isLock
	 */
	public void setIsLock(Integer isLock){
	   this.isLock=isLock;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定时间<br>
	 * @return lockTime
	 */
	@com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")	
	public java.sql.Timestamp getLockTime(){
	   return lockTime;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定时间<br>
	 * @param lockTime
	 */
	public void setLockTime(java.sql.Timestamp lockTime){
	   this.lockTime=lockTime;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>失败次数<br>
	 * @return failCount
	 */
		
	public Integer getFailCount(){
	   return failCount;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>失败次数<br>
	 * @param failCount
	 */
	public void setFailCount(Integer failCount){
	   this.failCount=failCount;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>权限类型<br>
	 * @return isAdmin
	 */
		
	public Integer getIsAdmin(){
	   return isAdmin;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>权限类型<br>
	 * @param isAdmin
	 */
	public void setIsAdmin(Integer isAdmin){
	   this.isAdmin=isAdmin;
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
