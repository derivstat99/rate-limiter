package main.java;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = new TokenBucketRateLimiter(3, 3.0);
        System.out.println("Simulating Jack sending 5 rapid requests...");

        for (int i = 1; i <= 5; i++) {
            boolean allowed = limiter.allowRequest("jack");
            System.out.println("Request processed " + (allowed ? "Allowed" : "Blocked"));
        }
    }
}
