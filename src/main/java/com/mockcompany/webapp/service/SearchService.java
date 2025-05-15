package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductItemRepository productItemRepository;

    public List<ProductItem> search(String query) {
        List<ProductItem> allItems = StreamSupport
                .stream(productItemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        List<ProductItem> matchingItems = new ArrayList<>();

        boolean isExactMatch = query.startsWith("\"") && query.endsWith("\"");
        String processedQuery = query.replaceAll("^\"|\"$", "");
        String lowerQuery = processedQuery.toLowerCase();

        for (ProductItem item : allItems) {
            String name = item.getName() != null ? item.getName() : "";
            String description = item.getDescription() != null ? item.getDescription() : "";

            if (isExactMatch) {
                if (name.contains(processedQuery) || description.contains(processedQuery)) {
                    matchingItems.add(item);
                }
            } else {
                if (name.toLowerCase().contains(lowerQuery) || description.toLowerCase().contains(lowerQuery)) {
                    matchingItems.add(item);
                }
            }
        }

        return matchingItems;
    }
}
