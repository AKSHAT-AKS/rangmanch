package com.rangmanch.watchlist;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleCatalogService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/watchlist")
public class WatchlistController {
    private static final String SESSION_KEY = "rangmanch.watchlist";
    private final TitleCatalogService catalogService;

    public WatchlistController(TitleCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public String view(HttpSession session, Model model) {
        Set<Long> ids = watchlistIds(session);
        List<Title> titles = new ArrayList<>();
        for (Long id : ids) {
            catalogService.findById(id).ifPresent(titles::add);
        }
        model.addAttribute("titles", titles);
        return "watchlist";
    }

    @PostMapping("/{id}")
    public String add(@PathVariable Long id, HttpSession session, RedirectAttributes redirect) {
        Title title = catalogService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        watchlistIds(session).add(title.getId());
        redirect.addFlashAttribute("message", "Added to your watchlist.");
        return "redirect:/watchlist";
    }

    @PostMapping("/{id}/remove")
    public String remove(@PathVariable Long id, HttpSession session, RedirectAttributes redirect) {
        watchlistIds(session).remove(id);
        redirect.addFlashAttribute("message", "Removed from your watchlist.");
        return "redirect:/watchlist";
    }

    private Set<Long> watchlistIds(HttpSession session) {
        Object current = session.getAttribute(SESSION_KEY);
        if (current instanceof Set<?> existing) {
            @SuppressWarnings("unchecked")
            Set<Long> ids = (Set<Long>) existing;
            return ids;
        }
        Set<Long> ids = new LinkedHashSet<>();
        session.setAttribute(SESSION_KEY, ids);
        return ids;
    }
}