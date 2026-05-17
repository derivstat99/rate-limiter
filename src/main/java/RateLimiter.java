package main.java;

public interface RateLimiter {
    boolean allowRequest(String userId);
}