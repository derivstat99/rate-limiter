package main.src;

public interface RateLimiter {
    boolean allowRequest(String userId);
}