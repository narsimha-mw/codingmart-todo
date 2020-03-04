package com.user.role.services.service;

import javax.mail.MessagingException;

public interface UserMailServer {
    void placeOrder(Integer otpCode);
    void sendEmailWithAttachment() throws MessagingException;

}
