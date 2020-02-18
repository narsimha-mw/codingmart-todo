package com.user.role.models.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.role.models.User;
import com.user.role.models.global.Audit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="agent", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "contact_no")})
@Data
@DynamicUpdate
@AllArgsConstructor
public class Agent extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name="username")
    private String agentName;

    @Size(min = 4, max = 8)
    private String password;

    @Email
    private String email;

    @Column(name="contact_no")
    private Long agentMobileNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="agent_fk", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "agent", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            targetEntity = AgentFile.class)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<AgentFile> agentFiles;

    public Agent() {
    }
}
