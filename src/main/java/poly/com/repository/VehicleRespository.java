package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poly.com.entity.Vehicle;

@Repository
public interface VehicleRespository extends JpaRepository<Vehicle, Integer> {


}
