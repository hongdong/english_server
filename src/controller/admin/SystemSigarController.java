package controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/SystemSigar/")
public class SystemSigarController {
	@RequestMapping("index.html")
    public String index(){
    	return "/admin/SystemSigar/index";
    }
}
