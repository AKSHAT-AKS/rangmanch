package com.rangmanch.review;

import com.rangmanch.title.Title;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {
    private final TitleReviewRepository reviewRepository;

    public ReviewService(TitleReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public List<TitleReview> reviewsFor(Long titleId) {
        return reviewRepository.findByTitleIdOrderByCreatedAtDesc(titleId);
    }

    public TitleReview addReview(Title title, ReviewForm form) {
        TitleReview review = new TitleReview(
                title, form.getDisplayName().trim(), form.getRating(), form.getBody().trim());
        return reviewRepository.save(review);
    }
}