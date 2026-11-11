package com.rangmanch.title;

import com.rangmanch.review.ReviewForm;
import com.rangmanch.review.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class TitleController {
    private final TitleCatalogService catalogService;
    private final ReviewService reviewService;

    public TitleController(TitleCatalogService catalogService, ReviewService reviewService) {
        this.catalogService = catalogService;
        this.reviewService = reviewService;
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
        model.addAttribute("reviews", reviewService.reviewsFor(id));
        model.addAttribute("reviewForm", new ReviewForm());
        return "title-details";
    }
}