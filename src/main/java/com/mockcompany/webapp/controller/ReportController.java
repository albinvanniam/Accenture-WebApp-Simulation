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
        List<String> searchTerms = Arrays.asList("Awesome", "Cool", "Cheap", "Quality", "Genuine");

        Map<String, Integer> searchCounts = new HashMap<>();
        int total = 0;

        for (String term : searchTerms) {
            List<ProductItem> matchingItems = searchService.search(term);
            searchCounts.put(term, matchingItems.size());
            total += matchingItems.size();
        }

        return new SearchReportResponse(searchCounts, total);
    }
}