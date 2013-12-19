package service;

import dao.AppBookMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 AppBookServiceImp来实现 私有的 <br>
 */
public interface AppBookService<T>  extends BaseService<T> , AppBookMapper<T> {

	public void delBookByPrimaryKeys(Object... keys) throws Exception;
	
}
