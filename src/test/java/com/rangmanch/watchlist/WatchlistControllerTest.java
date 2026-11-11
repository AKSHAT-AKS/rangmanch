package com.rangmanch.watchlist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleCatalogService;
import com.rangmanch.title.TitleType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class WatchlistControllerTest {
    private MockMvc mockMvc;
    private TitleCatalogService catalogService;
    private Title title;

    @BeforeEach
    void setUp() {
        catalogService = org.mockito.Mockito.mock(TitleCatalogService.class);
        title = new Title("Interstellar", TitleType.MOVIE, 2014, "Science Fiction", "A space adventure.");
        when(catalogService.findById(1L)).thenReturn(Optional.of(title));
        mockMvc = MockMvcBuilders.standaloneSetup(new WatchlistController(catalogService)).build();
    }

    @Test
    void addingTitleMakesItAppearInSessionWatchlist() throws Exception {
        MvcResult addResult = mockMvc.perform(post("/watchlist/1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession session = (MockHttpSession) addResult.getRequest().getSession(false);

        MvcResult listResult = mockMvc.perform(get("/watchlist").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("watchlist"))
                .andReturn();

        var titles = (java.util.List<Title>) listResult.getModelAndView().getModel().get("titles");
        assertThat(titles).containsExactly(title);
    }

    @Test
    void removingTitleEmptiesSessionWatchlist() throws Exception {
        MvcResult addResult = mockMvc.perform(post("/watchlist/1")).andReturn();
        MockHttpSession session = (MockHttpSession) addResult.getRequest().getSession(false);

        mockMvc.perform(post("/watchlist/1/remove").session(session))
                .andExpect(status().is3xxRedirection());

        MvcResult listResult = mockMvc.perform(get("/watchlist").session(session))
                .andExpect(status().isOk())
                .andReturn();
        var titles = (java.util.List<Title>) listResult.getModelAndView().getModel().get("titles");
        assertThat(titles).isEmpty();
    }
}