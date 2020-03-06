package com.user.role.models.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.role.models.User;
import com.user.role.models.global.Audit;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
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
                query = "select a from TravelsAgent a where a.agentName=?1 OR a.address=?2")
})
public class TravelsAgent extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String agentName;
    @Email
    private String email;
    private  String address;
    @Column(name="contact_no")
    @Pattern(regexp="(^$|[0-9]{10})")
    private String agentMobileNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="agent_fk", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy="agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private Set<DriverInfo> driverInfo;

    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TravelsAgentFile> agentFiles;

    public TravelsAgent() {
    }

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

    public String getAgentMobileNumber() {
        return agentMobileNumber;
    }

    public void setAgentMobileNumber(String agentMobileNumber) {
        this.agentMobileNumber = agentMobileNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TravelsAgentFile> getAgentFiles() {
        return agentFiles;
    }

    public void setAgentFiles(Set<TravelsAgentFile> agentFiles) {
        this.agentFiles = agentFiles;
    }


    public Set<DriverInfo> getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(Set<DriverInfo> driverInfo) {
        this.driverInfo = driverInfo;
    }

    @Override
    public String toString() {
        return "TravelsAgent{" +
                "id=" + id +
                ", agentName='" + agentName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", agentMobileNumber=" + agentMobileNumber +
                ", user=" + user +
                ", agentFiles=" + agentFiles +
                "' driverInfo="+ driverInfo+
                '}';
    }
}
