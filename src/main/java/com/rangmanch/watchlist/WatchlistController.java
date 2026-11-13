package com.rangmanch.watchlist;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleCatalogService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
    private static final String STATUS_KEY = "rangmanch.watchlist.statuses";
    private final TitleCatalogService catalogService;

    public WatchlistController(TitleCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public String view(HttpSession session, Model model) {
        Set<Long> ids = watchlistIds(session);
        Map<Long, WatchStatus> statuses = watchlistStatuses(session);
        List<Title> titles = new ArrayList<>();
        for (Long id : ids) {
            catalogService.findById(id).ifPresent(titles::add);
        }
        model.addAttribute("titles", titles);
        model.addAttribute("statuses", statuses);
        model.addAttribute("watchStatuses", WatchStatus.values());
        return "watchlist";
    }

    @PostMapping("/{id}")
    public String add(@PathVariable Long id, HttpSession session, RedirectAttributes redirect) {
        Title title = findTitle(id);
        watchlistIds(session).add(title.getId());
        watchlistStatuses(session).putIfAbsent(title.getId(), WatchStatus.PLAN_TO_WATCH);
        redirect.addFlashAttribute("message", "Added to your watchlist.");
        return "redirect:/watchlist";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam WatchStatus status,
                               HttpSession session,
                               RedirectAttributes redirect) {
        Title title = findTitle(id);
        watchlistIds(session).add(title.getId());
        watchlistStatuses(session).put(title.getId(), status);
        redirect.addFlashAttribute("message", "Watch status updated.");
        return "redirect:/watchlist";
    }

    @PostMapping("/{id}/remove")
    public String remove(@PathVariable Long id, HttpSession session, RedirectAttributes redirect) {
        watchlistIds(session).remove(id);
        watchlistStatuses(session).remove(id);
        redirect.addFlashAttribute("message", "Removed from your watchlist.");
        return "redirect:/watchlist";
    }

    private Title findTitle(Long id) {
        return catalogService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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

    private Map<Long, WatchStatus> watchlistStatuses(HttpSession session) {
        Object current = session.getAttribute(STATUS_KEY);
        if (current instanceof Map<?, ?> existing) {
            @SuppressWarnings("unchecked")
            Map<Long, WatchStatus> statuses = (Map<Long, WatchStatus>) existing;
            return statuses;
        }
        Map<Long, WatchStatus> statuses = new LinkedHashMap<>();
        session.setAttribute(STATUS_KEY, statuses);
        return statuses;
    }
}
