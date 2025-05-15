package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SearchService {

    @Autowired
    private ProductItemRepository productItemRepository;

    // Used by SearchController - handles quoted "exact match"
    public List<ProductItem> search(String query) {
        boolean isExactMatch = query.startsWith("\"") && query.endsWith("\"");
        String processedQuery = query.replaceAll("^\"|\"$", "");
        String lowerQuery = processedQuery.toLowerCase();

        return StreamSupport.stream(productItemRepository.findAll().spliterator(), false)
                .filter(item -> {
                    String name = item.getName() != null ? item.getName() : "";
                    String description = item.getDescription() != null ? item.getDescription() : "";

                    if (isExactMatch) {
                        return name.contains(processedQuery) || description.contains(processedQuery);
                    } else {
                        return name.toLowerCase().contains(lowerQuery) || description.toLowerCase().contains(lowerQuery);
                    }
                })
                .collect(Collectors.toList());
    }

    // Used by ReportController - always partial and case-insensitive
    public List<ProductItem> search(List<String> queries) {
        return StreamSupport.stream(productItemRepository.findAll().spliterator(), false)
                .filter(item -> {
                    String name = item.getName() != null ? item.getName().toLowerCase() : "";
                    String description = item.getDescription() != null ? item.getDescription().toLowerCase() : "";

                    return queries.stream().anyMatch(query -> {
                        String lowerQuery = query.toLowerCase();
                        return name.contains(lowerQuery) || description.contains(lowerQuery);
                    });
                })
                .collect(Collectors.toList());
    }
}
