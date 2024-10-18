package com.group.backend.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.domain.RegionalismoRepository;
import com.group.backend.entity.Regionalismo;

@RequestMapping("/regionalismo")
@RestController
@CrossOrigin(origins = "*")
public class RegionalismoController {

    private final RegionalismoRepository regionalismoRepository;

    public RegionalismoController(RegionalismoRepository regionalismoRepository) {
        this.regionalismoRepository = regionalismoRepository;
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Regionalismo>> list() {
        return ResponseEntity.ok(regionalismoRepository.findAll());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Regionalismo> save(@RequestBody Regionalismo regionalismo) {
        Regionalismo newRegionalismo = regionalismoRepository.save(regionalismo);
        return ResponseEntity.ok(newRegionalismo);
    }

}