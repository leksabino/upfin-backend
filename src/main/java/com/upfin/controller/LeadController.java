package com.upfin.controller;

import com.upfin.lead.Lead;
import com.upfin.lead.LeadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    private final LeadRepository repository;

    public LeadController(LeadRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, String> body) {

        String nome = body.get("nome");
        String email = body.get("email");

        if (repository.findByEmail(email).isPresent()) {
            // 👉 409 Conflict
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "E-mail já cadastrado"));
        }

        Lead lead = new Lead();
        lead.setNome(nome);
        lead.setEmail(email);

        repository.save(lead);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lead);
    }
}