package com.jku.at.repo;

import com.jku.at.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareRepository extends JpaRepository<Software, Integer> {
}