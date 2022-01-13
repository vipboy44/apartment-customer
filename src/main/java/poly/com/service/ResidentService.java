package poly.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import poly.com.entity.Resident;
import poly.com.repository.ResidentRepository;

@Service
public class ResidentService {

	@Autowired
	ResidentRepository residentRepository;
	
	public ResponseEntity<List<Resident>> findByApartment(String id){
		try {	
			List<Resident> lists = residentRepository.findByIdApartment(id);	
			return ResponseEntity.ok(lists);
		} catch (Exception e) {
		 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
