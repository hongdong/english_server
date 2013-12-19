package service.imp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.AppCategoryService;
import dao.AppCategoryMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("appCategoryService")
public class AppCategoryServiceImp<T> extends BaseServiceImp<T> implements AppCategoryService<T>{
	@Autowired
    private AppCategoryMapper<T> mapper;
		
	public AppCategoryMapper<T> getMapper() {
	    return mapper;
	}
	
}
