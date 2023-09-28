package org.modak.gateway;

public class Gateway {

    public void send(String userId, String message) {
        System.out.println("sending message to user " + userId);
        //Send message to another service in cloud
    }
}
