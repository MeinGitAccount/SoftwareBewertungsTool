package com.jku.at.service;

import com.jku.at.model.Criteria;
import com.jku.at.repo.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaServiceImpl implements CriteriaService {

    @Autowired
    CriteriaRepository repository;

    @Override
    public Criteria save(Criteria criteria) {
        return repository.save(criteria);
    }

    @Override
    public void delete(Criteria criteria) {
        repository.delete(criteria);
    }

    @Override
    public List<Criteria> findAll() {
        return repository.findAll();
    }
}