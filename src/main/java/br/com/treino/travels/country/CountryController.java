package br.com.treino.travels.country;

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
public class CountryController {

    private final EntityManager manager;

    public CountryController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping("/countries")
    @Transactional
    public ResponseEntity<?> registerCountry(@RequestBody @Valid NewCountryRequest request, UriComponentsBuilder builder){

        @Valid Country country = request.toModel();

        manager.persist(country);

        URI location = builder.path("/api/v1/travels/countries/{id}").buildAndExpand(country.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
