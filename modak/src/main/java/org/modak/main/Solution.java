package org.modak.main;

import org.modak.gateway.Gateway;
import org.modak.service.impl.NotificationServiceImpl;

import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {

        HashMap<String, Integer> rateLimits = new HashMap<>();
        rateLimits.put("Status", 2);
        rateLimits.put("News", 1);
        rateLimits.put("Marketing", 3);

        NotificationServiceImpl service = new NotificationServiceImpl(new Gateway(), rateLimits);

        service.send("Status", "user", "Status notification 1");
        service.send("Status", "user", "Status notification 2");
        service.send("Marketing", "user", "Marketing 1");
        service.send("Marketing", "user", "Marketing 2");
        service.send("Marketing", "user", "Marketing 3");
        service.send("Update", "user", "Update notification 1");
    }
}