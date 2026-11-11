package com.rangmanch.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleReviewRepository extends JpaRepository<TitleReview, Long> {
    List<TitleReview> findByTitleIdOrderByCreatedAtDesc(Long titleId);
}