package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import poly.com.entity.Notification;
import poly.com.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	NotificationRepository notificationRepository;
	
	public ResponseEntity<Page<Notification>> pagenation(int page, int size, String title, String sortType ){	
		try {
			Sort sortable = null;
			if (sortType.equals("ASC")) {
			      sortable = Sort.by("date").ascending();
			}else {
				 sortable = Sort.by("date").descending();  // default DESC
			 }
			Pageable pageable = PageRequest.of(page, size, sortable);			
			Page<Notification> pages =  notificationRepository.findByTitleContaining(title, pageable);
			
			return ResponseEntity.ok(pages);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ResponseEntity<Notification> findById(int id){
		try {
			Notification notification = notificationRepository.findById(id).orElse(null);
			return ResponseEntity.ok(notification);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
