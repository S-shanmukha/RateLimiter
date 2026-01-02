package com.example.ratelimiter.Dto;

public class RateLimiterResponseDto {
    private boolean Allowed;
    private int RetryAfter;

    public boolean getAllowed() {
        return Allowed;
    }
    public void setAllowed(boolean allowed) {
        Allowed = allowed;
    }
    public int getRetryAfter() {
        return RetryAfter;
    }
    public void setRetryAfter(int retryAfter) {
        RetryAfter = retryAfter;
    }
}
