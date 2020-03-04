package com.user.role.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.user.role.models.User;
import com.user.role.repository.UserRepository;
import com.user.role.services.service.TravelUserMailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserForgotEmail {

@Autowired
private UserRepository userRepository;
@Autowired
private TravelUserMailServer travelUserMailServer;

private Integer otp_code;
private String userEmail=null;

    public UserForgotEmail(UserRepository userRepository, TravelUserMailServer travelUserMailServer) {
        this.userRepository = userRepository;
        this.travelUserMailServer = travelUserMailServer;
    }

    @PostMapping("/user/forgot_pwd")
    public ResponseEntity<?> forgotPwd(@RequestParam("verify_code") Integer verify_code,
                                                @RequestParam("password") String password,
                                                @RequestParam("confirm_pwd") String confirm_pwd) {

           if(verify_code.equals(this.otp_code)) {
               if (password.endsWith(confirm_pwd)) {
                   List<User> user = userRepository.findByEmail(this.userEmail);
                   List<User> userName = new ArrayList<>(user);
                   for (User u : userName) {
                       u.setPassword(pwdConvert(password));
                       userRepository.save(u);
                   }
               } else {
                   return new ResponseEntity<>("you enter invalid password credentials, please try valid once.",HttpStatus.NOT_ACCEPTABLE);
               }
           }else{
               return new ResponseEntity<>("you enter invalid verification code, please try valid once..",HttpStatus.NOT_ACCEPTABLE);
           }
        return new ResponseEntity<>("Your Password was updated successfully",HttpStatus.ACCEPTED);
    }
    @GetMapping("/user/verify_email")
    public ResponseEntity<?> sendMail(@RequestParam("email") String email)  {

        Boolean response = userRepository.existsByEmail(email);
        if(response) {
            this.userEmail=email;
            try {
                this.otp_code=generateMailCode();
               // travelUserMailServer.placeOrder(this.otp_code);
                travelUserMailServer.sendEmailWithHTML(this.otp_code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            return new ResponseEntity<>("Your email doesn't exists",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Verification code send to your registered mail, Please check with inbox/spam folder",HttpStatus.ACCEPTED);
    }

    // this will convert any number sequence into 6 character.
    private int generateMailCode() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return Integer.parseInt(String.format("%06d", number));
    }

    // pwd was encrypted..
    private String pwdConvert(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
