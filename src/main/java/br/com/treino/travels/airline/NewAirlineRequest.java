package br.com.treino.travels.airline;

import br.com.treino.travels.country.Country;
import br.com.treino.travels.country.CountryRepository;
import br.com.treino.travels.validation.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class NewAirlineRequest {

    @JsonProperty
    @NotBlank
    @UniqueValue(domainClass = Airline.class, fieldName = "name")
    private final String name;

    @JsonProperty
    @NotBlank
    private final String countryName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NewAirlineRequest(String name, String countryName) {
        this.name = name;
        this.countryName = countryName;
    }


    public Airline toModel(CountryRepository repository) {
        Country country = repository.findByName(countryName);
        return new Airline(this.name, country);
    }
}
