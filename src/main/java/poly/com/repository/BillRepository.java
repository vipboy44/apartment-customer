package poly.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.com.dto.NumberStatisticalDTO;
import poly.com.dto.PriceStatisticalDTO;
import poly.com.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

	@Query(value = "SELECT b.total_price as price,  month(a.date) as month " + 
			" FROM bills b INNER JOIN apartment_index a ON b.apartment_index_id = a.id " + 
			" WHERE  a.id_apartment = ?1 and  year(a.date) = ?2 " +
			" ORDER BY a.date ASC", nativeQuery = true)
	List<PriceStatisticalDTO> findsTotalPriceByYear(String id_apartment, int year);
	
	@Query(value = "SELECT b.electricity_number as number, month(a.date) as month " + 
			" FROM bills b INNER JOIN apartment_index a ON b.apartment_index_id = a.id " + 
			" WHERE  a.id_apartment = ?1 and  year(a.date) = ?2 ", nativeQuery = true)
	List<NumberStatisticalDTO> findsElectricityNumberByYear(String id_apartment, int year);
	
	@Query(value = "SELECT b.water_number as number, month(a.date) as month " + 
			" FROM bills b INNER JOIN apartment_index a ON b.apartment_index_id = a.id " + 
			" WHERE  a.id_apartment = ?1 and  year(a.date) = ?2 ", nativeQuery = true)
	List<NumberStatisticalDTO> findswaterNumberByYear(String id_apartment, int year);
	
	@Query(value = "SELECT b.* " + 
			" FROM bills b INNER JOIN apartment_index a ON b.apartment_index_id = a.id " + 
			" WHERE  a.id_apartment = ?1 and month(a.date)  = ?2 and  year(a.date) = ?3 ", nativeQuery = true)
	Optional<Bill> findByMonth(String id_Apartment, int month, int year);
	
	
}
