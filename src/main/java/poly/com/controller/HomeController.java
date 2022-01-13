package poly.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
	@RequestMapping("/")
	public String redirectPage() {
		
		 return "redirect:trang-chu";
	}
	
	@RequestMapping("/trang-chu")
	public String homePage() {
		
		return "contents/user/home";
	}
	
	@RequestMapping("/trang-chu/gioi-thieu")
	public String aboutUs() {
		
		return "contents/user/about";
	}
	
	@RequestMapping("/trang-chu/thiet-ke")
	public String design() {
		
		return "contents/user/design";
	}
	
	
	@RequestMapping("/trang-chu/noi-quy")
	public String roles() {
		
		return "contents/user/rules";
	}
	
}
