package com.upfin.controller;

import com.upfin.lead.Lead;
import com.upfin.lead.LeadRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    private final LeadRepository repository;

    public LeadController(LeadRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Lead lead) {

        if (repository.existsByEmail(lead.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email já cadastrado");
        }

        return ResponseEntity.ok(repository.save(lead));
    }
}