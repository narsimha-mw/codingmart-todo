package com.user.role.services.serviceImpl;
import com.user.role.services.service.TravelUserMailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class UserMailServerImpl implements TravelUserMailServer {

    @Autowired
    private JavaMailSender javaMailSender;

    public UserMailServerImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    // normal text
    @Override
    public void placeOrder(Integer otpCode) {
        System.err.print("sendmailOTPCode:  "+ otpCode);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("narasimhulu@codingmart.com");
        msg.setSubject("Reset your Travel password request");
        msg.setText("Hi..."+" \n Password Reset Request on Travel \n"+ "This code using your password will be updated:  ["+otpCode+"]\n ------- \n Thanks & Regards \n Travel support team.");
        javaMailSender.send(msg);
    }

    // html format
    @Override
    public void sendEmailWithHTML(Integer otpCode) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
//        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("narasimhulu@codingmart.com");
        helper.setSubject("Reset your Travel password request");//
        String htmlText = "<div "+
                "font-size:12pt;font-weight:bold;padding:10px;'>"+
                "Hi......<br /><br />"+
                "<b>Password Reset Request on Travel</b> <br /><br />"+
                "This code using your password will be updated:<br><br/>"+ "<b>"+otpCode+"</b>"+
                "<br/>----------"+
                "Thanks & Regards<br></br>"+"Travel support team."+
                "</div>";
        helper.setText(htmlText,true);
        javaMailSender.send(msg);
}
}
