package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.com.entity.Regulation;

import java.util.Optional;

public interface RegulationRepository extends JpaRepository<Regulation, Integer> {
    Optional<Regulation> findTopByOrderByIdDesc();
}