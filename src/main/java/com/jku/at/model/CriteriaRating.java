package com.jku.at.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
public class CriteriaRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Getter
    @Setter
    private Integer rating;

    @Getter
    private String criteriaName;

    @Getter
    @Setter
    @Column(length = 500)
    private String description;

    @ManyToOne
    @Getter
    @Setter
    private Software software;

    public CriteriaRating(String name, Software software) {
        this.criteriaName = name;
        this.software = software;
        rating = 0;
    }


    @Override
    public int hashCode() {
        return rating * criteriaName.hashCode() * software.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((CriteriaRating) o).getId());
    }

    @Override
    public String toString() {
        return criteriaName + " rated " + rating + " for " + software.getName();
    }
}