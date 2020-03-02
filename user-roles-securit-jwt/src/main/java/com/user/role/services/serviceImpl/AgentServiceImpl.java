package com.user.role.services.serviceImpl;

import com.user.role.annotations.EntityFilter;
import com.user.role.annotations.FilterQuery;
import com.user.role.models.travel.Agent;
import com.user.role.repository.AgentRepository;
import com.user.role.repository.UserRepository;
import com.user.role.services.AgentService;
import org.apache.commons.codec.binary.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Filter;

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

    @Override
    public List<Agent> searchAgent(Specification<Agent> searchQueryItem) {
        return agentRepository.findAll(searchQueryItem);
    }

    @Override
    public Page<Agent> findAgentName(Pageable pageable) {
        return  agentRepository.findAll(pageable);
    }

    @Override
    public Page<Agent> findByName(String name, Pageable pageable) {
       return (Page)agentRepository.findByAgentName(name,pageable);
    }

}
