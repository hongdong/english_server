package service;

import dao.AppCategoryMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 AppCategoryServiceImp来实现 私有的 <br>
 */
public interface AppCategoryService<T>  extends BaseService<T> , AppCategoryMapper<T> {

}
