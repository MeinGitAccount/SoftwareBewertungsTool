package com.jku.at.service;

import com.jku.at.model.Criteria;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CriteriaService {

    @Transactional
    Criteria save(Criteria criteria);

    void delete(Criteria criteria);

    List<Criteria> findAll();
}