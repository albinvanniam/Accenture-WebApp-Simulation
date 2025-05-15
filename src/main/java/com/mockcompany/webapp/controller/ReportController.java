package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ReportController {

    private final SearchService searchService;

    @Autowired
    public ReportController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/api/reports/search")
    public SearchReportResponse runReport() {
        List<String> searchTerms = Arrays.asList("Cool", "Amazing", "Perfect", "Kids");

        Set<ProductItem> uniqueMatches = new HashSet<>();
        Map<String, Integer> searchCounts = new HashMap<>();

        for (String term : searchTerms) {
            List<ProductItem> matchingItems = searchService.search(term);
            searchCounts.put(term, matchingItems.size());
            uniqueMatches.addAll(matchingItems); // Avoid double counting
        }

        return new SearchReportResponse(searchCounts, uniqueMatches.size());
    }
}
