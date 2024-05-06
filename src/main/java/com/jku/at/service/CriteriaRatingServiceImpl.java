package com.jku.at.service;

import com.jku.at.model.CriteriaRating;
import com.jku.at.repo.CriteriaRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaRatingServiceImpl implements CriteriaRatingService {

    @Autowired
    CriteriaRatingRepository repository;

    @Override
    public CriteriaRating save(CriteriaRating criteriaRating) {
        return repository.save(criteriaRating);
    }

    @Override
    public void delete(CriteriaRating criteriaRating) {
        repository.delete(criteriaRating);
    }

    @Override
    public List<CriteriaRating> findAll() {
        return repository.findAll();
    }
}