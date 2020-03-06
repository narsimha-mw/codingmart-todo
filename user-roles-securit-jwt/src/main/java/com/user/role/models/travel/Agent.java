package com.user.role.models.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.role.models.User;
import com.user.role.models.global.Audit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

//@Data
@DynamicUpdate
@AllArgsConstructor
@Entity
@Table(name="agent", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "contact_no")})

@NamedQueries({
        @NamedQuery(name = "getAllDetails",
                query = "select a from Agent a where a.agentName=?1 OR a.address=?2")
})
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
    private  String address;
    @Column(name="contact_no")
    private Long agentMobileNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="agent_fk", nullable = false)
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAgentMobileNumber() {
        return agentMobileNumber;
    }

    public void setAgentMobileNumber(Long agentMobileNumber) {
        this.agentMobileNumber = agentMobileNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<AgentFile> getAgentFiles() {
        return agentFiles;
    }

    public void setAgentFiles(Set<AgentFile> agentFiles) {
        this.agentFiles = agentFiles;
    }

    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<AgentFile> agentFiles;

    public Agent() {
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", agentName='" + agentName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", agentMobileNumber=" + agentMobileNumber +
                ", user=" + user +
                ", agentFiles=" + agentFiles +
                '}';
    }
}
