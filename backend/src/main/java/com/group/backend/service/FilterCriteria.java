package com.group.backend.service;

import java.time.LocalDate;
import java.util.List;

public class FilterCriteria {
    private final List<String> tags;
    private final List<String> portals;
    private final List<String> reporters;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public FilterCriteria(List<String> tags, List<String> portals, List<String> reporters, LocalDate startDate, LocalDate endDate) {
        this.tags = tags;
        this.portals = portals;
        this.reporters = reporters;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getPortals() {
        return portals;
    }

    public List<String> getReporters() {
        return reporters;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
