package com.group.backend.service;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class FilterCriteria {
    private final List<String> tags;
    private final List<String> portals;
    private final List<String> reporters;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String keyword;

    public FilterCriteria(
        List<String> tags,
        List<String> portals,
        List<String> reporters,
        LocalDate startDate,
        LocalDate endDate,
        String keyword
    ) {
        this.tags = tags != null ? tags : List.of();
        this.portals = portals;
        this.reporters = reporters;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword != null ? keyword.trim() : null;
    }
}
