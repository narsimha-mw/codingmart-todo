package com.user.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TravelsAgentDTO implements Serializable {

    private Long id;
    private String agentName;
    private String email;
    private String address;

//    public TravelsAgentDTO(Long id, String agentName, String email, String address) {
//        this.id = id;
//        this.agentName = agentName;
//        this.email = email;
//        this.address = address;
//    }
}
