package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import poly.com.entity.Regulation;
import poly.com.repository.RegulationRepository;

@Service
public class RegulationService {
    @Autowired
    private RegulationRepository regulationRepository;

    // ----------------  Find all  ---------------------------------- 4
    public ResponseEntity<Regulation> findAll() {
        try {
            Regulation regulationList = regulationRepository.findTopByOrderByIdDesc().orElse(null);
            return ResponseEntity.ok(regulationList);
        }catch (Exception e){
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}