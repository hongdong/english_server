package service;

import dao.TbsUserMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 TbsUserServiceImp来实现 私有的 <br>
 */
public interface TbsUserService<T>  extends BaseService<T> , TbsUserMapper<T> {

}
