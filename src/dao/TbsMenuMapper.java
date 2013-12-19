package dao;
/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由Mapper映射文件来实现 私有的<br>
 */
public interface TbsMenuMapper<T> extends BaseMapper<T> {
	public java.util.List<java.util.Map<String,Object>> selectByMenuIsMenu(java.util.Map<String,Object> map) throws java.sql.SQLException;
	public java.util.List<java.util.Map<String,Object>> selectByMenuOther(java.util.Map<String,Object> map) throws java.sql.SQLException;
	public java.util.List<T> selectByButtons(java.util.Map<String,Object> map) throws java.sql.SQLException;
}
