package service.imp;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.TbsMenuService;
import dao.TbsMenuMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("tbsMenuService")
public class TbsMenuServiceImp<T> extends BaseServiceImp<T> implements TbsMenuService<T>{
	@Autowired
    private TbsMenuMapper<T> mapper;
		
	public TbsMenuMapper<T> getMapper() {
	    return mapper;
	}
	

	public java.util.List<java.util.Map<String,Object>> selectByMenuIsMenu(java.util.Map<String,Object> map) throws java.sql.SQLException{
		return mapper.selectByMenuIsMenu(map);
	}
	
	public java.util.List<java.util.Map<String,Object>> selectByMenuOther(java.util.Map<String,Object> map) throws java.sql.SQLException{
		return mapper.selectByMenuOther(map);
	}
	
	public List<T> selectByButtons(Map<String, Object> map) throws SQLException {
		return mapper.selectByButtons(map);
	}

}
