package poly.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/can-ho")
public class ApartmentController {

	@GetMapping()
	public String apartment() {
		return "contents/user/apartment";
	}
	
	@GetMapping("/bill")
	public String bill(@RequestParam("date") String date, ModelMap model) {
		model.addAttribute("date", date);
		return "contents/user/bill";
	}
	
	
}
