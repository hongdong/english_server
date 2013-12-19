package service;

import dao.AppBookPageMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 AppBookPageServiceImp来实现 私有的 <br>
 */
public interface AppBookPageService<T> extends BaseService<T>,
		AppBookPageMapper<T> {
	public static final int PAGE_TYPE_READ = 0;

	public void delBookPageByPrimaryKeys(Object... keys) throws Exception;
}
