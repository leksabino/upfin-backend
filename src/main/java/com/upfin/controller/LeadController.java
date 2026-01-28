package com.upfin.controller;

import com.upfin.lead.Lead;          // ✅ IMPORT CORRETO
import com.upfin.repository.LeadRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.status(409)
                    .body(java.util.Map.of("message", "E-mail já cadastrado"));
        }

        return ResponseEntity.status(201).body(repository.save(lead));
    }

    @GetMapping
    public List<Lead> listar() {
        return repository.findAll();
    }
}