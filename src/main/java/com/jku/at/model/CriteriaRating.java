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

    @ManyToOne
    @Getter
    @Setter
    private Criteria criteria;

    @ManyToOne
    @Getter
    @Setter
    private Software software;

    public CriteriaRating(Criteria criteria, Software software) {
        this.criteria = criteria;
        this.software = software;
        rating = 0;
    }


    @Override
    public int hashCode() {
        return rating * criteria.getId() * software.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((CriteriaRating) o).getId());
    }

    @Override
    public String toString() {
        return criteria.getName() + " rated " + rating + " for " + software.getName();
    }
}