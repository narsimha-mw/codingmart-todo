package com.user.role.services.serviceImpl;

import com.user.role.services.service.TravelUserMailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserMailServerImpl implements TravelUserMailServer {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

public void setMailSender(MailSender mailSender){
    this.mailSender=mailSender;
}
public void setTemplateMessage(SimpleMailMessage templateMessage){
    this.templateMessage=templateMessage;
}
    @Override
    public void placeOrder(Integer otpCode) {
        System.err.print("sendmailOTPCode "+ otpCode);
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo("narasimhulu@codingmart.com");
        msg.setSubject("Reset your Travel password request");
        msg.setText("Hi..."+"\n Password Reset Request on Travel \n"+
                "\033[0;1m" +otpCode+"\n Thanks & Regards \n travel support team.");
        mailSender.send(msg);
        System.err.print("Verification code will be send to your register mail, please check with inbox/spam folder");
    }

    @Override
    public void sendEmailWithAttachment() {
//        MimeMessage msg = javaMailSender.createMimeMessage();
//
//        // true = multipart message
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//        helper.setTo("1@gmail.com");
//
//        helper.setSubject("Testing from Spring Boot");
//
//        // default = text/plain
//        //helper.setText("Check attachment for image!");
//
//        // true = text/html
//        helper.setText("<h1>Check attachment for image!</h1>", true);
//
//        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));
//
//        //Resource resource = new ClassPathResource("android.png");
//        //InputStream input = resource.getInputStream();
//
//        //ResourceUtils.getFile("classpath:android.png");
//
//        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
//
//        javaMailSender.send(msg);
//
//    }
//    //void placeOrder(Order order);
    }
}
