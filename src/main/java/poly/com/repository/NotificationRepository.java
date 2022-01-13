package poly.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import poly.com.entity.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	Page<Notification> findByTitleContaining(String title, Pageable pageable);
	
	
}
