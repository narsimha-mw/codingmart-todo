package com.user.role.controllers;

import com.user.role.models.User;
import com.user.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserForgot_email {

@Autowired
private JavaMailSender javaMailSender;
@Autowired
private UserRepository userRepository;

private Integer otp_code;
private String userEmail=null;

    public UserForgot_email(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }


    @PostMapping("/user/forgot_pwd")
    public void forgotPwd(@RequestParam("verify_code") Integer verify_code,
                          @RequestParam("password") String password,
                          @RequestParam("confirm_pwd") String confirm_pwd) {

           if(verify_code.equals(otp_code)) {
               if (password.endsWith(confirm_pwd)) {
                   List<User> user = userRepository.findByEmail(this.userEmail);
                   List<User> userName = new ArrayList<>(user);
                   for (User u : userName) {
                       u.setPassword(pwdConvert(password));
                       userRepository.save(u);
                   }
               } else {
                   System.err.print("pwd doesn't match");
               }
           }else{
                System.err.print("you enter invalid verification code, please try valid one..");
               }
}

    @GetMapping("/user/verify_email")
    public void sendMail(@RequestParam("email") String email) {
        System.err.println("Sending Email...");

        Boolean response = userRepository.existsByEmail(email);
        if(response) {
            this.userEmail=email;
            try {
                sendEmail();
                //sendEmailWithAttachment();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            System.err.print("email doesn't exists");
        }
    }
    private void sendEmail() {
        int sendmailOTPCode= generateRandomCode();
        this.otp_code=sendmailOTPCode;
        System.err.print("sendmailOTPCode "+ sendmailOTPCode);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("snlshvsnj@gmail.com", "narasimhulu@codingmart.com");
        msg.setSubject("Reset your Travel password request");
        msg.setText("Hi...  \n  Password Reset Request on Travel \n");
        msg.setText("\033[0;1m" +sendmailOTPCode);
        msg.setText("\n Thanks & Regards \n travel support team.");
        javaMailSender.send(msg);
        System.err.print("Verification code will be send to your register mail, please check with inbox/spam folder");
    }
    
    private int generateRandomCode(){

        int min=0,max=9,code = 0;
        Random random = new Random();
        for (int i = 0; i < 6; i++)
            code= random.nextInt((max - min) + 1) + min;
        return code;
    }

    private void sendEmailWithAttachment() throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("1@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();

        //ResourceUtils.getFile("classpath:android.png");

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
    // pwd was encrypted..
    private String pwdConvert(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
