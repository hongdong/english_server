package service.imp;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.TbsRoleUserService;
import dao.TbsRoleUserMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("tbsRoleUserService")
public class TbsRoleUserServiceImp<T> extends BaseServiceImp<T> implements TbsRoleUserService<T>{
	@Autowired
    private TbsRoleUserMapper<T> mapper;
		
	public TbsRoleUserMapper<T> getMapper() {
	    return mapper;
	}
	

}
