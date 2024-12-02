package com.group.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.ReporterRepository;
import com.group.backend.entity.Reporter;

@RestController
@RequestMapping("/reporters")
@CrossOrigin(origins = "*")
public class ReporterController {

    private final ReporterRepository reporterRepository;

    public ReporterController(ReporterRepository reporterRepository) {
        this.reporterRepository = reporterRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Reporter>> listarReporters() {
        List<Reporter> reporters = reporterRepository.findAll();
        return ResponseEntity.ok(reporters);
    }
}
