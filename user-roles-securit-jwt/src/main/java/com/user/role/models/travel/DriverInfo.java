package com.user.role.models.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class DriverInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="DriverName", nullable = false)
     private String driverName;
    @Column(name="driving_lience", nullable = false, unique = true)
    private String drivingLience;
    @Column(name="age", nullable = false)
    @Size(min = 25, max = 55)
    private Integer driverAge;
    private String address;
    @Column(name="contact_no", unique = true, nullable = false)
    private Long contact_no;
    public DriverInfo() {
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="driver_agent_fk", nullable = false)
    @JsonIgnore
    private TravelsAgent agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDrivingLience() {
        return drivingLience;
    }

    public void setDrivingLience(String drivingLience) {
        this.drivingLience = drivingLience;
    }

    public Integer getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(Integer driverAge) {
        this.driverAge = driverAge;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContact_no() {
        return contact_no;
    }

    public void setContact_no(Long contact_no) {
        this.contact_no = contact_no;
    }

    public TravelsAgent getAgent() {
        return agent;
    }

    public void setAgent(TravelsAgent agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "DriverInfo{" +
                "id=" + id +
                ", driverName='" + driverName + '\'' +
                ", drivingLience='" + drivingLience + '\'' +
                ", driverAge=" + driverAge +
                ", address='" + address + '\'' +
                ", contact_no=" + contact_no +
                ", agent=" + agent +
                '}';
    }
}
