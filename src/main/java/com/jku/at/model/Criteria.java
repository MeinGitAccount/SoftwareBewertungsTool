package com.jku.at.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    Integer id;

    @Column(length = 50, unique = true, nullable = false)
    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    Integer importance;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    Set<CriteriaRating> criteriaRatings = new HashSet<>();

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Criteria) o).getId());
    }

    @Override
    public String toString() {
        return name;
    }
}