package service.imp;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import model.BaseModel;
import dao.BaseMapper;
import model.BaseModel;
import service.BaseService;

/**
 * 
 * <br>
 * <b>功能：</b>基础实现<br>
 */
public class BaseServiceImp<T> implements BaseService<T>{

	private BaseMapper<T> mapper;
	/**
	 * 
	 * <br>
	 * <b>功能：</b>子类重写方法<br>
	 * @return
	 */
	public BaseMapper<T> getMapper(){
		return mapper;
	}
	 /**
     * 
     * <br>
     * <b>功能：</b>主键查询<br>
     * @param key
     * @return
     * @throws Exception
     */
	public T selectByPrimaryKey(Object key) throws Exception {
		return getMapper().selectByPrimaryKey(key);
	}
    /**
     * 
     * <br>
     * <b>功能：</b>主键修改<br>
     * @param t
     * @throws Exception
     */
	public Integer updateByPrimaryKey(T t) throws Exception {
		return getMapper().updateByPrimaryKey(t);
	}
    /**
     * 
     * <br>
     * <b>功能：</b>主键删除<br>
     * @param keys
     * @throws Exception
     */
	public Integer deleteByPrimaryKeys(Object... keys) throws Exception {
		int i=0;
		for(Object key:keys){
			i+=getMapper().deleteByPrimaryKey(key);
		}
		return i;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>插入<br>
	 * @param t
	 * @throws Exception
	 */
	public Integer insert(T t) throws Exception {
		return getMapper().insert(t);
	}
    /**
     * 
     * <br>
     * <b>功能：</b>查询总行数<br>
     * @param model
     * @return
     * @throws Exception
     */
	public Integer selectByModelCount(T model) throws Exception {
		return getMapper().selectByModelCount(model);
	}
    /**
     * 
     * <br>
     * <b>功能：</b>查询Map总行数<br>
     * @param map
     * @return
     * @throws Exception
     */
	public Integer selectByMapCount(Map<?, ?> map) throws Exception {
		return getMapper().selectByMapCount(map);
	}
    /**
     * 
     * <br>
     * <b>功能：</b>模型分页<br>
     * @param model
     * @return
     * @throws Exception
     */
	public List<T> selectByModel(T model) throws Exception {
		if(((BaseModel)model).getPageUtil().getPaging()){
			try {
				((BaseModel)model).getPageUtil().setRowCount(selectByModelCount(model));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getMapper().selectByModel(model);
	}
    /**
     * 
     * <br>
     * <b>功能：</b>Map查询 <br>
     * @param map
     * @return
     * @throws Exception
     */
	public List<T> selectByMap(Map<?, ?> map) throws Exception {
		return getMapper().selectByMap(map);
	}
  
	/**
	 * 
	 * <br>
	 * <b>功能：</b>实体查询<br>
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<T> selectByEntiry(T entity) throws Exception{
		return getMapper().selectByEntiry(entity);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>实体删除<br>
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByEntity(T entity) throws Exception{
		  return getMapper().deleteByEntity(entity);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>SQL查询<br>
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<T> selectBySql(String sql) throws Exception{
		return getMapper().selectBySql(sql);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>SQL修改<br>
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Integer updateBySql(String sql) throws Exception{
		return getMapper().updateBySql(sql);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>SQL删除<br>
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Integer deleteBySql(String sql) throws Exception{
		return getMapper().deleteBySql(sql);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>SQL增加<br>
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Integer insertBySql(String sql) throws Exception{
		return getMapper().insertBySql(sql);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>查询分页数<br>
	 * @param map
	 * @return
	 * @throws java.sql.SQLException
	 */
	public Integer selectByMapPagingCount(Map<?,?> map) throws java.sql.SQLException{
		return getMapper().selectByMapPagingCount(map);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>查询分页<br>
	 * @param map
	 * @return
	 * @throws java.sql.SQLException
	 */
	public List<T> selectByMapPaging(Map<?,?> map) throws java.sql.SQLException{
		int rowCount = getMapper().selectByMapPagingCount(map);
		util.core.PageUtil pageUtil=(util.core.PageUtil) map.get("pageUtil");
		if(null==pageUtil){
			System.out.println("错误!!!  pageUtil 参数为NULL");
			return null;
		}
		pageUtil.setRowCount(rowCount);
		return getMapper().selectByMapPaging(map);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>删除一条记录<br>
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrimaryKey(Object key) throws Exception {
		return getMapper().deleteByPrimaryKey(key);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>图表<br>
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<Map<?,?>> charts(Map<?,?> map) throws SQLException{
		return getMapper().charts(map);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>递归查询<br>
	 * @param tbcConTempModel
	 * @return
	 * @throws SQLException
	 */
	public List<T> selectByChild(T model) throws SQLException {
		if(((BaseModel)model).getPageUtil().getPaging()){
			try {
				((BaseModel)model).getPageUtil().setRowCount(selectByModelCount(model));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getMapper().selectByChild(model);
	}
}
