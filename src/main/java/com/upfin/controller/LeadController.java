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
    @GetMapping("/export")
    public void exportarCsv(
            @RequestHeader(value = "X-ADMIN-TOKEN", required = false) String token,
            jakarta.servlet.http.HttpServletResponse response
    ) throws Exception {

        String correto = System.getenv("ADMIN_TOKEN");

        if (correto == null || token == null || !correto.equals(token)) {
            response.setStatus(401);
            return;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=leads-upfin.csv");

        var writer = response.getWriter();
        writer.println("id,nome,email,created_at");

        for (var lead : repository.findAll()) {
            writer.println(
                    lead.getId() + "," +
                            (lead.getNome() == null ? "" : lead.getNome()) + "," +
                            lead.getEmail() + "," +
                            lead.getCreatedAt()
            );
        }

        writer.flush();
    }
}