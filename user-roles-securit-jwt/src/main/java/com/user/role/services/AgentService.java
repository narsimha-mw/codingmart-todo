package com.user.role.services;

import com.user.role.models.travel.Agent;

import java.util.List;

public interface AgentService {
    List<Agent> allAgentDetails(Long userId);

    void saveAgentDetails(Long userId, Agent agent);
}
