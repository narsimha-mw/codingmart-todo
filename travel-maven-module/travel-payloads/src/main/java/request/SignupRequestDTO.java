package request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SignupRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    
    private Set<String> roles;

    @NotBlank
    private String address;
    private  Long mobileNumber;
    @NotBlank
    private String city;

    @NotBlank
    private String password;
  }
