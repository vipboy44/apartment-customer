package poly.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trang-chu/tin-tuc")
public class NewsController {

	@GetMapping
	public String news() {
		
		return "contents/user/news";
	}
	
	@GetMapping("/{id}")
	public String detail(ModelMap model, @PathVariable int id) {
		
		model.addAttribute("id", id);
		return "contents/user/news-detail";
	}
	
	
}
