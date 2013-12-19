package dao;
/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由Mapper映射文件来实现 私有的<br>
 */
public interface TbsUserMapper<T> extends BaseMapper<T> {
	public java.util.List<java.util.Map<String,Object>> selectByRoleUrls(java.util.Map<?,?> map) throws Exception;
}
