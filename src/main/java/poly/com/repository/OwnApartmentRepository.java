package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.com.entity.OwnApartment;
import poly.com.entity.Resident;

import java.util.Optional;

public interface OwnApartmentRepository extends JpaRepository<OwnApartment, Integer> {

}
