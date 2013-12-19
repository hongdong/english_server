package service.imp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.AppBookPageSentencesService;
import dao.AppBookPageSentencesMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("appBookPageSentencesService")
public class AppBookPageSentencesServiceImp<T> extends BaseServiceImp<T> implements AppBookPageSentencesService<T>{
	@Autowired
    private AppBookPageSentencesMapper<T> mapper;
		
	public AppBookPageSentencesMapper<T> getMapper() {
	    return mapper;
	}
	
}
