package controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.core.MethodUtil;

@Controller
@RequestMapping("/admin/SystemInfo/")
public class SystemInfoControllerAdmin extends BaseController{
    
	private MethodUtil util=new MethodUtil();
    
	@RequestMapping("index.html")
	public String index(){
		return "/admin/SystemInfo/index";
	}
	
}
