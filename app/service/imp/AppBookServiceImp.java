package service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.AppBookService;
import dao.AppBookMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("appBookService")
public class AppBookServiceImp<T> extends BaseServiceImp<T> implements
		AppBookService<T> {
	@Autowired
	private AppBookMapper<T> mapper;
 
	public AppBookMapper<T> getMapper() {
		return mapper;
	}

	public void delBookByPrimaryKeys(Object... keys) throws Exception {

		for (Object key : keys) {
			getMapper()
					.deleteBySql(
							" delete  from  AppBookPageSentences  where  bookPageID in (select id from AppBookPage where bookID in ("
									+ key + ")) ");
			getMapper().deleteBySql(
					" delete  from  AppBookPage  where  bookID in (" + key
							+ ") ");
		}
		this.deleteByPrimaryKeys(keys);
	}


}
