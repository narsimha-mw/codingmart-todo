package com.user.role.security.services.agent;

import com.user.role.models.travel.Agent;
import com.user.role.repository.AgentRepositoryCustom;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class AgentRepositoryCustomImpl implements AgentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void customeAgetnEntiryManager() {

        System.err.println("++++++++++++++++++");

        CriteriaBuilder cBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Agent> agentQuery = cBuilder.createQuery(Agent.class);
        Root<Agent> rootQuery = agentQuery.from(Agent.class);
        agentQuery.select(rootQuery);
        List<Agent> result = entityManager.createQuery(agentQuery).getResultList();
        result.forEach(System.err::println);
    }
}
