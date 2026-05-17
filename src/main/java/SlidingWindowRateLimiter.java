package main.java;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int limit;
    private final long windowSizeInSeconds;
    private final Map<String, LinkedList<Long>> requestLogs;

    public SlidingWindowRateLimiter(int limit, long windowSizeInSeconds) {
        this.limit = limit;
        this.windowSizeInSeconds = windowSizeInSeconds;
        requestLogs = new HashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();

        if (!requestLogs.containsKey(userId)) {
            requestLogs.put(userId, new LinkedList<>());
        }

        LinkedList<Long> userLog = requestLogs.get(userId);

        while (!userLog.isEmpty() && (now - userLog.getFirst()) / 1000.0 > windowSizeInSeconds) {
            userLog.removeFirst();
        }

        if (userLog.size() < limit) {
            userLog.addLast(now);
            return true;
        } else {
            return false;
        }
    }
}
