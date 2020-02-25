package com.user.role.security.services.agent;

import com.user.role.models.travel.Agent;
import com.user.role.repository.AgentRepository;
import com.user.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    AgentRepository agentRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Agent> allAgentDetails(Long userId) {

        CriteriaBuilder cBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Agent> agentQuery = cBuilder.createQuery(Agent.class);
        Root<Agent> rootQuery = agentQuery.from(Agent.class);
        agentQuery.select(rootQuery);
       // List<Agent> result = entityManager.createQuery(agentQuery).getResultList();

        if(implementerId(userId)){
            return agentRepository.findAll();
        }
//    agentRepositoryCustom.customeAgetnEntiryManager();

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
    public void saveAgentDetails(Long userId, Agent agents) {

//        Optional<Object> result = userRepository.findById(userId)
//                                                .map(a->agents.setUser(a););
            agentRepository.save(agents);
    }
}
