package com.group.backend.service;

import java.util.List;

public class FilterCriteria {
    private final List<String> tags;
    private final List<String> portals;

    public FilterCriteria(List<String> tags, List<String> portals) {
        this.tags = tags;
        this.portals = portals;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getPortals() {
        return portals;
    }
}
