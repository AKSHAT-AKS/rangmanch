package com.rangmanch.title;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

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

        mockMvc.perform(get("/").param("type", "SERIES"))
                .andExpect(status().isOk())
                .andExpect(view().name("titles"))
                .andExpect(model().attribute("titles", hasSize(1)));
    }

    @Test
    void missingTitleReturnsNotFound() throws Exception {
        mockMvc.perform(get("/titles/999999"))
                .andExpect(status().isNotFound());
    }
}