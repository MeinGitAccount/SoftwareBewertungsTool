package com.jku.at.service;

import com.jku.at.model.Software;
import com.jku.at.repo.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    SoftwareRepository repository;

    @Override
    public Software save(Software software) {
        return repository.save(software);
    }

    @Override
    public void delete(Software software) {
        repository.delete(software);
    }

    @Override
    public List<Software> findAll() {
        return repository.findAll();
    }

    @Override
    public Software findByName(String name) {
        return repository.findByName(name);
    }
}