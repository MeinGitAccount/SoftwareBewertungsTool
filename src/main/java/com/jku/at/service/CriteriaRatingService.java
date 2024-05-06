package com.jku.at.service;

import com.jku.at.model.CriteriaRating;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CriteriaRatingService {

    @Transactional
    CriteriaRating save(CriteriaRating criteriaRating);

    void delete(CriteriaRating criteriaRating);

    List<CriteriaRating> findAll();
}