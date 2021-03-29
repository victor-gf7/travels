package br.com.treino.travels.country;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @Deprecated
    public Country() {
    }

    public Country(@NotBlank String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
