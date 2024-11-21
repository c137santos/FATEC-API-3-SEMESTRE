package com.group.backend.service;

import java.util.List;

public class FilterCriteria {
    private final List<String> tags;
    private final List<String> portals;
    private final List<String> reporters;

    public FilterCriteria(List<String> tags, List<String> portals, List<String> reporters) {
        this.tags = tags;
        this.portals = portals;
        this.reporters = reporters;
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
}
