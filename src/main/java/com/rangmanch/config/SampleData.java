package com.rangmanch.config;

import com.rangmanch.title.Title;
import com.rangmanch.title.TitleRepository;
import com.rangmanch.title.TitleType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleData {
    @Bean
    CommandLineRunner loadSampleTitles(TitleRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            repository.save(new Title("The Shawshank Redemption", TitleType.MOVIE, 1994, "Drama",
                    "Two imprisoned men build a friendship over the years and find hope in unexpected places."));
            repository.save(new Title("Interstellar", TitleType.MOVIE, 2014, "Science Fiction",
                    "A team travels beyond this galaxy to discover whether mankind has a future among the stars."));
            repository.save(new Title("The Dark Knight", TitleType.MOVIE, 2008, "Action",
                    "Batman faces a criminal mastermind who pushes Gotham into chaos."));
            repository.save(new Title("Breaking Bad", TitleType.SERIES, 2008, "Crime Drama",
                    "A chemistry teacher turns to manufacturing drugs after a life-changing diagnosis."));
            repository.save(new Title("The Office", TitleType.SERIES, 2005, "Comedy",
                    "A mockumentary follows the everyday lives of employees at a paper company."));
        };
    }
}