package poly.com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.com.entity.Bill;
import poly.com.service.BillService;

@RestController
@RequestMapping("/api/bill")
public class BillAPI {

	@Autowired
	BillService billService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Bill> findByMonth(@PathVariable String id, @RequestParam("month") int month,
			              @RequestParam("year") int year){
		
		return billService.findByMonth(id, month, year);
	}
	
}

