package service.imp;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.TbsRoleService;
import dao.TbsRoleMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("tbsRoleService")
public class TbsRoleServiceImp<T> extends BaseServiceImp<T> implements TbsRoleService<T>{
	@Autowired
    private TbsRoleMapper<T> mapper;
		
	public TbsRoleMapper<T> getMapper() {
	    return mapper;
	}
	

}
