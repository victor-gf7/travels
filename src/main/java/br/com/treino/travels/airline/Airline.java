package br.com.treino.travels.airline;

import br.com.treino.travels.country.Country;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @Valid
    private Country country;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public Airline() {
    }

    public Airline(@NotBlank String name, @Valid Country country) {
        this.name = name;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", createdAt=" + createdAt +
                '}';
    }
}
