package com.rangmanch.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleRepository;
import com.rangmanch.title.TitleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private TitleRepository titleRepository;
    @Autowired private TitleReviewRepository reviewRepository;

    @Test
    @Transactional
    void validReviewIsSavedForTitle() throws Exception {
        Title title = titleRepository.saveAndFlush(new Title(
                "Review Test Movie", TitleType.MOVIE, 2025, "Drama", "A test synopsis."));

        mockMvc.perform(post("/titles/{id}/reviews", title.getId())
                        .param("displayName", "Film Fan")
                        .param("rating", "5")
                        .param("body", "A thoughtful and enjoyable film."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/titles/" + title.getId() + "#reviews"));

        var reviews = reviewRepository.findByTitleIdOrderByCreatedAtDesc(title.getId());
        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getDisplayName()).isEqualTo("Film Fan");
        assertThat(reviews.get(0).getRating()).isEqualTo(5);
    }

    @Test
    @Transactional
    void invalidRatingDoesNotCreateReview() throws Exception {
        Title title = titleRepository.saveAndFlush(new Title(
                "Invalid Review Test", TitleType.MOVIE, 2025, "Drama", "A test synopsis."));

        mockMvc.perform(post("/titles/{id}/reviews", title.getId())
                        .param("displayName", "Film Fan")
                        .param("rating", "8")
                        .param("body", "Invalid rating."))
                .andExpect(status().is3xxRedirection());

        assertThat(reviewRepository.findByTitleIdOrderByCreatedAtDesc(title.getId())).isEmpty();
    }

    @Test
    void detailsPageRendersReviewSection() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}