package com.user.role.services.serviceImpl;

import com.user.role.models.travel.TravelsAgent;
import com.user.role.services.TravelsAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TravelsAgentServiceImpl implements TravelsAgentService {

    @Autowired
    private TravelsAgentFilterService agentFilterService;

    public TravelsAgentServiceImpl(TravelsAgentFilterService agentFilterService) {
        this.agentFilterService = agentFilterService;
    }

    @Override
    public List<TravelsAgent> allAgentDetails(Long userId) {
        return agentFilterService.allAgentDetails(userId);
    }

    @Override
    public List<TravelsAgent> getAgentNameAndEmail(String address) {
        return (List<TravelsAgent>) agentFilterService.getAgentNameAndEmail(address);
    }
}
