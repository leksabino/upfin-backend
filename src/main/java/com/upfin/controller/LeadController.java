package com.upfin.controller;

import com.upfin.lead.Lead;
import com.upfin.lead.LeadRepository;
import com.upfin.lead.LeadRequest;
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

    @GetMapping("/ping")
    public String ping() {
        return "UPFIN BACKEND OK";
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody LeadRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "E-mail já cadastrado"));
        }

        Lead lead = new Lead();
        lead.setNome(request.getNome());
        lead.setEmail(request.getEmail());

        repository.save(lead);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lead);
    }
}