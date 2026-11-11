package com.rangmanch.review;

import com.rangmanch.title.Title;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "title_reviews")
public class TitleReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String displayName;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String body;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected TitleReview() { }

    public TitleReview(Title title, String displayName, Integer rating, String body) {
        this.title = title;
        this.displayName = displayName;
        this.rating = rating;
        this.body = body;
    }

    @PrePersist
    void setCreatedAt() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Title getTitle() { return title; }
    public String getDisplayName() { return displayName; }
    public Integer getRating() { return rating; }
    public String getBody() { return body; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}