package com.user.role.services.serviceImpl;

import com.user.role.models.travel.Agent;
import com.user.role.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentFilterService agentFilterService;

    public AgentServiceImpl(AgentFilterService agentFilterService) {
        this.agentFilterService = agentFilterService;
    }

    @Override
    public List<Agent> allAgentDetails() {
        return agentFilterService.allAgentDetails();
    }

    @Override
    public List<Agent> getAgentNameAndEmail( String address) {
        return (List<Agent>) agentFilterService.getAgentNameAndEmail(address);
    }
}
