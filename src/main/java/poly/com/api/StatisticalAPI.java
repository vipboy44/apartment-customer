package poly.com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.com.dto.StatisticalDTO;
import poly.com.service.StatisticalService;

@RestController
@RequestMapping("/api/statistical")
public class StatisticalAPI {

	@Autowired
	StatisticalService statisticalService;
	
	@GetMapping("/price")
	public ResponseEntity<double[]> findsTotalPriceApartmentByYear(@RequestParam("id") String id,
																	   @RequestParam("year") int year){
		return statisticalService.findsTotalPriceByYear(id, year);
	}
	
	@GetMapping("/number")
	public ResponseEntity<StatisticalDTO> findsWaterAndElectricityNumbersByYear(@RequestParam("id") String id,
																				@RequestParam("year") int year){	
		return statisticalService.findsWaterAndElectricityNumbersByYear(id, year);
	}
	
	
}
