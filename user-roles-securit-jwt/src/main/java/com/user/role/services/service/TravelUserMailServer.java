package com.user.role.services.service;

import javax.mail.MessagingException;

public interface TravelUserMailServer {
    void placeOrder(Integer otpCode);
    void sendEmailWithHTML(Integer otpCode) throws MessagingException;

}
