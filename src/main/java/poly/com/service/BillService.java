package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import poly.com.entity.Bill;
import poly.com.repository.BillRepository;

@Service
public class BillService {

	@Autowired
	BillRepository billRepository;
	
	public ResponseEntity<Bill> findByMonth(String id_Apartment, int month, int year){
		try {
			
			Bill bill =  billRepository.findByMonth(id_Apartment, month, year).orElse(null);
			
			return ResponseEntity.ok(bill);		
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
