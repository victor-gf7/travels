package br.com.treino.travels.country;

import br.com.treino.travels.validation.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class NewCountryRequest {

    @JsonProperty
    @NotBlank
    @UniqueValue(domainClass = Country.class, fieldName = "name")
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NewCountryRequest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewCountryRequest{" +
                "name='" + name + '\'' +
                '}';
    }

    public Country toModel() {
        return new Country(this.name);
    }
}
