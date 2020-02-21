import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Data
@AllArgsConstructor
@DynamicUpdate
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @JsonIgnore
    private String password;

    @NotBlank
    private  String address;

    @NotBlank
    private  String city;

    @Column(name="contact_no")
    private  Long mobileNumber;
    @ManyToMany(targetEntity =  Role.class, fetch = FetchType.LAZY)
    @JoinTable(	name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(targetEntity = Agents.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,
            mappedBy="user", orphanRemoval = true)
    @JsonIgnore
    private Set<Agents> agents;

    public User(){}
    public User(String username, String email, String password, String address, String city, Long mobileNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address=address;
        this.city=city;
        this.mobileNumber=mobileNumber;
    }
}

