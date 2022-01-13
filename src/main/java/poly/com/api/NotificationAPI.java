package poly.com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.com.entity.Notification;
import poly.com.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationAPI {

	@Autowired
	NotificationService notificationService;
	
	@GetMapping
	public ResponseEntity<Page<Notification>> pagenation(@RequestParam(name = "page", defaultValue = "0") int page,
															@RequestParam(name = "size", defaultValue = "6") int size,
															@RequestParam(name = "title", defaultValue = "") String title,
															@RequestParam(name = "sortType", defaultValue = "DESC") String sortType ){	
		
		return notificationService.pagenation(page, size, title, sortType);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Notification> findById(@PathVariable int id){	
		
		return notificationService.findById(id);
	}
	
}
