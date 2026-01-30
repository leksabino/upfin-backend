package com.upfin.repository;

import com.upfin.lead.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    boolean existsByEmail(String email);
}
