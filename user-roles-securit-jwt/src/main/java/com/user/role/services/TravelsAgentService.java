package com.user.role.services;

import com.user.role.models.travel.TravelsAgent;

import java.util.List;

public interface TravelsAgentService {

    List<TravelsAgent> allAgentDetails(Long userId);
    List<TravelsAgent> getAgentNameAndEmail(String address);
}
