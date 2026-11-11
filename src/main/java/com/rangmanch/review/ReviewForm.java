package com.rangmanch.review;

import jakarta.validation.constraints.*;

public class ReviewForm {
    @NotBlank
    @Size(max = 60)
    private String displayName;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    @Size(max = 1000)
    private String body;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}