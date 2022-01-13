package poly.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poly.com.entity.Resident;

@Repository
public interface ResidentRepository extends JpaRepository <Resident,Integer> {

  @Query(value = "select * from residents where id_apartment = ?1 ", nativeQuery = true)
  List<Resident> findByIdApartment(String id);
  
	
}
