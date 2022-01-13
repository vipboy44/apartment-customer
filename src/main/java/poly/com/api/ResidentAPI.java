package poly.com.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.com.entity.Resident;
import poly.com.service.ResidentService;

@RestController
@RequestMapping("/api/reisident")
public class ResidentAPI {

	@Autowired
	ResidentService residentService;
	
	@GetMapping("/{id}")
	public ResponseEntity<List<Resident>> findByApartment(@PathVariable("id") String idApartment){
		
		return residentService.findByApartment(idApartment);
	}
}
