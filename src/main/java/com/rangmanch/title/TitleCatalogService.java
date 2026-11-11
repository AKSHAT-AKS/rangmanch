package com.rangmanch.title;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TitleCatalogService {
    private final TitleRepository titleRepository;

    public TitleCatalogService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public List<Title> search(String query) {
        return search(query, null);
    }

    public List<Title> search(String query, TitleType type) {
        if (query == null || query.isBlank()) {
            return titleRepository.findAll().stream()
                    .filter(title -> type == null || title.getType() == type)
                    .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                    .toList();
        }
        return titleRepository.findByNameContainingIgnoreCaseOrderByNameAsc(query.trim()).stream()
                .filter(title -> type == null || title.getType() == type)
                .toList();
    }

    public Optional<Title> findById(Long id) {
        return titleRepository.findById(id);
    }
}