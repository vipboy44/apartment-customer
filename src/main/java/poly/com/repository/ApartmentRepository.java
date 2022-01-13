package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.com.entity.Apartment;

import java.util.Optional;


public interface ApartmentRepository extends JpaRepository<Apartment, String> {

    Optional<Apartment> findApartmentById(String id);

}
