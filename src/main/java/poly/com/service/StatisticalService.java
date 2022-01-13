package poly.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import poly.com.dto.NumberStatisticalDTO;
import poly.com.dto.PriceStatisticalDTO;
import poly.com.dto.StatisticalDTO;
import poly.com.repository.BillRepository;

@Service
public class StatisticalService {

	@Autowired
	BillRepository billRepository;
	
	public ResponseEntity<double[]> findsTotalPriceByYear(String id_apartment, int year){
		try {
			List<PriceStatisticalDTO> lists =  billRepository.findsTotalPriceByYear(id_apartment, year);
			double[] prices = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};		
			for (int i = 0; i < lists.size(); i++) {
				 int j = lists.get(i).getMonth();
				 prices[j-1] = lists.get(i).getPrice();
			}
			
			return ResponseEntity.ok(prices);		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<StatisticalDTO> findsWaterAndElectricityNumbersByYear(String id_apartment, int year){
		try {
			List<NumberStatisticalDTO> electricitys =  billRepository.findsElectricityNumberByYear(id_apartment, year);
			List<NumberStatisticalDTO> waters =  billRepository.findswaterNumberByYear(id_apartment, year);
			
			Integer[] electricitysNumber = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};		
			Integer[] watersNumber = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
			for (int i = 0; i < electricitys.size(); i++) {
				 int j = electricitys.get(i).getMonth();
				 electricitysNumber[j-1] = electricitys.get(i).getNumber();
			}
			
			for (int i = 0; i < waters.size(); i++) {
				 int j = waters.get(i).getMonth();
				 watersNumber[j-1] = waters.get(i).getNumber();
			}
						
			StatisticalDTO statisticalDTO = new StatisticalDTO(watersNumber, electricitysNumber);
			
			return ResponseEntity.ok(statisticalDTO);		
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
