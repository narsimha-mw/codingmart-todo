package com.user.role.security.services.agent;

import com.user.role.models.travel.Agent;
import com.user.role.repository.UserRepository;
import com.user.role.repository.travel.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    AgentRepository agentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Agent> allAgentDetails(Long userId) {

        if(implementerId(userId)){
           return agentRepository.findAll();
        }
        return null;
    }
      private  boolean implementerId(Long userId){
          Optional<Long> validateId =  userRepository.findById(userId).map(user -> user.getId());
          if(validateId.isPresent())
              return  true;
            else
              return false;
      }

    @Override
    public void saveAgentDetails(Long userId, Agent agent) {

        if (implementerId(userId)) {
            agentRepository.save(agent);
        }
    }
}