package com.rangmanch.watchlist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleRepository;
import com.rangmanch.title.TitleType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class WatchlistControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private TitleRepository titleRepository;

    @Test
    @Transactional
    void addingTitleMakesItAppearInSessionWatchlist() throws Exception {
        Title title = titleRepository.saveAndFlush(new Title(
                "Watchlist Test Film", TitleType.MOVIE, 2024, "Drama", "A test synopsis."));

        MvcResult addResult = mockMvc.perform(post("/watchlist/{id}", title.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/watchlist"))
                .andReturn();
        MockHttpSession session = (MockHttpSession) addResult.getRequest().getSession(false);

        MvcResult listResult = mockMvc.perform(get("/watchlist").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("watchlist"))
                .andReturn();

        @SuppressWarnings("unchecked")
        List<Title> titles = (List<Title>) listResult.getModelAndView().getModel().get("titles");
        assertThat(titles).extracting(Title::getName).containsExactly("Watchlist Test Film");
    }

    @Test
    @Transactional
    void removingTitleEmptiesSessionWatchlist() throws Exception {
        Title title = titleRepository.saveAndFlush(new Title(
                "Removable Test Film", TitleType.MOVIE, 2023, "Drama", "A test synopsis."));
        MvcResult addResult = mockMvc.perform(post("/watchlist/{id}", title.getId())).andReturn();
        MockHttpSession session = (MockHttpSession) addResult.getRequest().getSession(false);

        mockMvc.perform(post("/watchlist/{id}/remove", title.getId()).session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/watchlist"));

        MvcResult listResult = mockMvc.perform(get("/watchlist").session(session))
                .andExpect(status().isOk())
                .andReturn();
        @SuppressWarnings("unchecked")
        List<Title> titles = (List<Title>) listResult.getModelAndView().getModel().get("titles");
        assertThat(titles).isEmpty();
    }
    @Test
    @Transactional
    void updatingStatusStoresWatchedStateInSession() throws Exception {
        Title title = titleRepository.saveAndFlush(new Title(
                "Status Test Film", TitleType.MOVIE, 2025, "Drama", "A test synopsis."));
        MvcResult addResult = mockMvc.perform(post("/watchlist/{id}", title.getId())).andReturn();
        MockHttpSession session = (MockHttpSession) addResult.getRequest().getSession(false);

        mockMvc.perform(post("/watchlist/{id}/status", title.getId())
                        .session(session).param("status", "WATCHED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/watchlist"));

        MvcResult listResult = mockMvc.perform(get("/watchlist").session(session))
                .andExpect(status().isOk())
                .andReturn();
        @SuppressWarnings("unchecked")
        java.util.Map<Long, WatchStatus> statuses =
                (java.util.Map<Long, WatchStatus>) listResult.getModelAndView().getModel().get("statuses");
        assertThat(statuses).containsEntry(title.getId(), WatchStatus.WATCHED);
    }

}
