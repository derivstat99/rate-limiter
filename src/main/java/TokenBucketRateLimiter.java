package main.src;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketRateLimiter implements RateLimiter {
    private final int capacity;
    private final double refillRate;
    private final Map<String, Double> tokenCounts;
    private final Map<String, Long> lastRefillTime;

    public TokenBucketRateLimiter(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokenCounts = new HashMap<>();
        this.lastRefillTime = new HashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();

        if (!tokenCounts.containsKey(userId)) {
            tokenCounts.put(userId, (double) capacity);
            lastRefillTime.put(userId, now);
        }

        long lastTime = lastRefillTime.get(userId);
        double secondsPassed = (now - lastTime) / 1000.0;
        double currentTokens = tokenCounts.get(userId);
        currentTokens = Math.min(currentTokens + secondsPassed * refillRate, capacity);

        lastRefillTime.put(userId, now);

        if (currentTokens >= 1.0) {
            tokenCounts.put(userId, currentTokens - 1.0);
            return true;
        } else {
            tokenCounts.put(userId, currentTokens);
            return false;
        }
    }
}