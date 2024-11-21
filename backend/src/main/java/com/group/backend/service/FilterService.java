package com.group.backend.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FilterService {

    private static final String LOG_FILE_PATH = "filter_logs.txt";

    public FilterCriteria processFilters(String tag, String portal) {
        List<String> tags = (tag == null || tag.isEmpty()) ? null : List.of(tag.split(","));
        List<String> portals = (portal == null || portal.isEmpty()) ? null : List.of(portal.split(","));

        FilterCriteria criteria = new FilterCriteria(tags, portals);

        logFilters(criteria);

        return criteria;
    }

    private void logFilters(FilterCriteria criteria) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("Data/Hora: " + LocalDateTime.now() + "\n");
            writer.write("Filtros Aplicados:\n");
            writer.write("  - Tags: " + (criteria.getTags() == null ? "Todas" : criteria.getTags()) + "\n");
            writer.write("  - Portais: " + (criteria.getPortals() == null ? "Todos" : criteria.getPortals()) + "\n");
            writer.write("-----------------------------------\n");
        } catch (IOException e) {
            System.err.println("Erro ao registrar filtros no arquivo: " + e.getMessage());
        }
    }
}
