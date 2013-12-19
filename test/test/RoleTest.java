package test;


import model.TbsRoleModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.TbsRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-common.xml","classpath:spring-servlet.xml"})
public class RoleTest {
	 
	 @Autowired
	 TbsRoleService<TbsRoleModel> tbsRoleService;
	 TbsRoleModel tbsRoleModel=new TbsRoleModel();
	 @Test
     public void testRole() throws Exception{
		 tbsRoleModel.setId("11");
		 tbsRoleModel = tbsRoleService.selectByEntiry(tbsRoleModel).get(0);
		 System.out.println(util.json.JsonUtil.toJson(tbsRoleModel));
     }
}
