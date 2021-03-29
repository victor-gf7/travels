package br.com.treino.travels.airline;

import br.com.treino.travels.country.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/travels")
public class AirlineController {

    private final CountryRepository repository;
    private final EntityManager manager;

    public AirlineController(CountryRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @PostMapping("/airlines")
    @Transactional
    public ResponseEntity<?> registerAirline(@RequestBody @Valid NewAirlineRequest request, UriComponentsBuilder builder){

        @Valid Airline airline = request.toModel(repository);

        manager.persist(airline);

        URI location = builder.path("/api/v1/travels/airlines/{id}").buildAndExpand(airline.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
