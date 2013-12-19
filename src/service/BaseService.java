package service;

import dao.BaseMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 BaseServiceImp来实现通用的<br>
 */
public interface BaseService<T> extends BaseMapper<T>{
	/**
	 * 
	 * <br>
	 * <b>功能：</b>删除多条记录<br>
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrimaryKeys(Object... keys) throws Exception ;
}
