package com.rangmanch.title;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class TitleControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private TitleRepository titleRepository;

    @Test
    @Transactional
    void catalogueFiltersByTitleType() throws Exception {
        titleRepository.saveAndFlush(new Title(
                "Series Filter Test", TitleType.SERIES, 2024, "Drama", "A test series."));

        MvcResult result = mockMvc.perform(get("/").param("type", "SERIES"))
                .andExpect(status().isOk())
                .andExpect(view().name("titles"))
                .andReturn();

        @SuppressWarnings("unchecked")
        java.util.List<Title> titles = (java.util.List<Title>) result.getModelAndView().getModel().get("titles");
        assertThat(titles).isNotEmpty();
        assertThat(titles).allMatch(title -> title.getType() == TitleType.SERIES);
        assertThat(titles).extracting(Title::getName).contains("Series Filter Test");
    }

    @Test
    void missingTitleReturnsNotFound() throws Exception {
        mockMvc.perform(get("/titles/999999"))
                .andExpect(status().isNotFound());
    }
}