package com.shelter.ua.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "adverts")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "[a-zA-Z]+")
    @Column(nullable = false)
    private String country;

    @Length(min = 3, max = 50)
    @Pattern(regexp = "[a-zA-Z]+")
    private String region;

    @NotBlank
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$")
    @Column(nullable = false)
    private String phoneNumber;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "[a-zA-Z]+")
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(nullable = false)
    private String street;

    @Min(1)
    @Column(nullable = false)
    private Integer persons;

    @ElementCollection
    @CollectionTable(name = "criterias")
    @Column(name = "criterias")
    private List<String> criterias;

    @NotBlank
    @Length(min = 3, max = 50)
    private String period;

    @NotBlank
    @Length(min = 10, max = 200)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User owner;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
    }

    public static List<String> getSearchableFieldNames() {
        return List.of("country", "region", "city", "persons", "criterias", "period");
    }

}
