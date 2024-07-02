package com.jku.at.service;

import com.jku.at.model.Software;
import jakarta.transaction.Transactional;

import java.util.List;

public interface SoftwareService {

    @Transactional
    Software save(Software software);

    void delete(Software software);

    Software findByName(String name);

    List<Software> findAll();
}