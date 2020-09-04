package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.web.model.BeerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/beers")
@RestController
public class BeerController {

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID id){
        return new ResponseEntity<>(BeerDTO.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody BeerDTO beerDTO){
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBeerById(@PathVariable UUID id, @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
