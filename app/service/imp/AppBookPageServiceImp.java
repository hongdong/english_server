package service.imp;
import model.AppBookPageSentencesModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.AppBookPageService;
import dao.AppBookPageMapper;
import dao.AppBookPageSentencesMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("appBookPageService")
public class AppBookPageServiceImp<T> extends BaseServiceImp<T> implements AppBookPageService<T>{
	@Autowired
    private AppBookPageMapper<T> mapper;
		
	public AppBookPageMapper<T> getMapper() {
	    return mapper;
	}
	
	@Autowired
	private AppBookPageSentencesMapper<AppBookPageSentencesModel> appBookPageSentencesMapper;
	

	public void delBookPageByPrimaryKeys(Object... keys) throws Exception {
		 
		this.deleteByPrimaryKeys(keys);
		for(Object key:keys){
			appBookPageSentencesMapper.deleteByEntity(new AppBookPageSentencesModel(String.valueOf(key)));
		}
		
	}
 
}
