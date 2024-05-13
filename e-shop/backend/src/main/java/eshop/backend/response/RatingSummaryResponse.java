package eshop.backend.response;

public record RatingSummaryResponse(
        double averageRating,
        int[] ratingCounts
) {}