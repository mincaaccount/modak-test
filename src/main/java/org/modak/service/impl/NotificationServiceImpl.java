package org.modak.service.impl;

import org.modak.gateway.Gateway;
import org.modak.service.NotificationService;

import java.util.HashMap;
import java.util.Map;

public class NotificationServiceImpl implements NotificationService {

    private Gateway gateway;
    private Map<String, Integer> rateLimits;
    private Map<String, Long> lastSentTimes = new HashMap<>();
    private static final int MINUTE = 1;
    private static final int HOUR = 60;
    private static final int DAY = 24 * HOUR;


    public NotificationServiceImpl(Gateway gateway, Map<String, Integer> rateLimits) {
        this.gateway = gateway;
        this.rateLimits = rateLimits;
    }

    @Override
    public void send(String type, String user, String amount) {
        long currentTimeInMinutes = System.currentTimeMillis() / 60000;
        Integer rateLimit = rateLimits.get(type);
        Long lastSentTime = lastSentTimes.getOrDefault(type, currentTimeInMinutes);

        if (isRateLimitExceeded(type, currentTimeInMinutes, lastSentTime, rateLimit)) {
            throw new RuntimeException("Rate limit exceeded for " + type + " notifications.");
        }

        gateway.send(user, amount);

        lastSentTimes.put(type, currentTimeInMinutes);
    }

    private boolean isRateLimitExceeded(String type, long currentTimeInMinutes, long lastSentTime, int rateLimit) {
        long rateLimitDurationMinutes = getRateLimitMinutesByType(type);

        long timeDifference = currentTimeInMinutes - lastSentTime;
        return timeDifference < rateLimitDurationMinutes && timeDifference < rateLimit;
    }

    private long getRateLimitMinutesByType(String type) {
        return switch (type) {
            case "Status" -> MINUTE;
            case "News" -> DAY;
            case "Marketing" -> HOUR;
            default -> 0;
        };
    }
}
