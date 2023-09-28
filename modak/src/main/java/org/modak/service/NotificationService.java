package org.modak.service;

public interface NotificationService {

    void send(String type, String user, String amount) throws Exception;
}
