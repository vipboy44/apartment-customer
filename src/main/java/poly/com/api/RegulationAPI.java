package poly.com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.com.entity.Regulation;
import poly.com.service.RegulationService;

@RestController
@RequestMapping("/api/regulation")
public class RegulationAPI {
    @Autowired
    private RegulationService regulationService;
    /* ---------------------------------------------------*/


    // find All regulation
    @GetMapping()
    public ResponseEntity<Regulation> findAll(){
        return  regulationService.findAll();
    }
}
