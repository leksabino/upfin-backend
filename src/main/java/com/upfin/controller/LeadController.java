package com.upfin.controller;

import com.upfin.lead.Lead;
import com.upfin.repository.LeadRepository;
import org.springframework.http.HttpStatus;
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

    // 🔓 PUBLICO – CAPTURA DE LEAD
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Lead lead) {

        if (repository.existsByEmail(lead.getEmail())) {
            return ResponseEntity.status(409)
                    .body(java.util.Map.of("message", "E-mail já cadastrado"));
        }

        return ResponseEntity.status(201).body(repository.save(lead));
    }

    // 🔐 ADMIN – LISTAR LEADS
    @GetMapping
    public ResponseEntity<List<Lead>> listar(
            @RequestHeader(value = "X-ADMIN-TOKEN", required = false) String token
    ) {

        String correto = System.getenv("ADMIN_TOKEN");

        if (correto == null || token == null || !correto.equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(repository.findAll());
    }
}
