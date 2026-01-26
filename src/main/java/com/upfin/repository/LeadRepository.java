package com.upfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.upfin.lead.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    boolean existsByEmail(String email);
}