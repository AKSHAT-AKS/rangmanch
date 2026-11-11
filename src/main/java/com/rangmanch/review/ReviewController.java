package com.rangmanch.review;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleCatalogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewController {
    private final TitleCatalogService catalogService;
    private final ReviewService reviewService;

    public ReviewController(TitleCatalogService catalogService, ReviewService reviewService) {
        this.catalogService = catalogService;
        this.reviewService = reviewService;
    }

    @PostMapping("/titles/{id}/reviews")
    public String addReview(@PathVariable Long id,
                            @Valid @ModelAttribute("reviewForm") ReviewForm form,
                            BindingResult result,
                            RedirectAttributes redirect) {
        Title title = catalogService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (result.hasErrors()) {
            redirect.addFlashAttribute("reviewError", "Enter a name, a rating from 1 to 5, and a review of up to 1000 characters.");
            return "redirect:/titles/" + id + "#reviews";
        }
        reviewService.addReview(title, form);
        redirect.addFlashAttribute("reviewMessage", "Your review has been added.");
        return "redirect:/titles/" + id + "#reviews";
    }
}