package com.user.role.payload.request;

import lombok.Data;

import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
public class SignupRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    
    private Set<String> role;

    @NotBlank
    private String address;
    private  Long mobileNumber;
    @NotBlank
    private String city;

    @NotBlank
    private String password;
  }
