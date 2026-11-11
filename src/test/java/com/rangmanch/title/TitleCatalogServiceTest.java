package com.rangmanch.title;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TitleCatalogServiceTest {
    private TitleRepository repository;
    private TitleCatalogService service;

    @BeforeEach
    void setUp() {
        repository = mock(TitleRepository.class);
        service = new TitleCatalogService(repository);
    }

    @Test
    void trimsSearchTextBeforeLookingUpTitles() {
        Title title = new Title("Interstellar", TitleType.MOVIE, 2014, "Science Fiction", "A space adventure.");
        when(repository.findByNameContainingIgnoreCaseOrderByNameAsc("Interstellar")).thenReturn(List.of(title));

        assertThat(service.search("  Interstellar  ")).containsExactly(title);
        verify(repository).findByNameContainingIgnoreCaseOrderByNameAsc("Interstellar");
    }

    @Test
    void blankSearchReturnsAllTitlesInAlphabeticalOrder() {
        Title zulu = new Title("Zulu", TitleType.MOVIE, 1964, "Drama", "A film.");
        Title alpha = new Title("Alpha", TitleType.SERIES, 2020, "Drama", "A series.");
        when(repository.findAll()).thenReturn(List.of(zulu, alpha));

        assertThat(service.search(" ")).containsExactly(alpha, zulu);
    }

    @Test
    void looksUpTitleById() {
        Title title = new Title("Interstellar", TitleType.MOVIE, 2014, "Science Fiction", "A space adventure.");
        when(repository.findById(4L)).thenReturn(Optional.of(title));

        assertThat(service.findById(4L)).contains(title);
    }
}