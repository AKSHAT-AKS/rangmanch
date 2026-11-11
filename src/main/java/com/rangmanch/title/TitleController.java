package com.rangmanch.title;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class TitleController {
    private final TitleCatalogService catalogService;

    public TitleController(TitleCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q,
                       @RequestParam(required = false) TitleType type, Model model) {
        model.addAttribute("titles", catalogService.search(q, type));
        model.addAttribute("query", q == null ? "" : q);
        model.addAttribute("selectedType", type);
        model.addAttribute("types", TitleType.values());
        return "titles";
    }

    @GetMapping("/titles/{id}")
    public String details(@PathVariable Long id, Model model) {
        Title title = catalogService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("title", title);
        return "title-details";
    }
}