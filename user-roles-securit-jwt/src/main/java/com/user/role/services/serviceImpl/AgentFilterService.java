package com.user.role.services.serviceImpl;

import com.user.role.models.travel.Agent;
import com.user.role.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgentFilterService {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AgentRepository agentRepository;


    public AgentFilterService(EntityManager entityManager, AgentRepository agentRepository) {
        this.entityManager = entityManager;
        this.agentRepository = agentRepository;
    }

    public ResponseEntity<List<Agent>> getAgentNameAndEmail( String address) {

        if (entityManager.isOpen()) {
//            List<Agent> employees = entityManager.createQuery("from Agent ", Agent.class).getResultList();
//            List<String> result = employees.stream().map(agent -> agent.getAgentName()).collect(Collectors.toList());
//            for (String a:result) {
//            return new ResponseEntity<>("you enter invalid password credentials, please try valid once.", HttpStatus.NOT_ACCEPTABLE);

//            }
//            List<Agent> files = entityManager.createQuery("select a from Agent a join a.agentFiles f where a.agentName = 'abcd' and f.fileName = 'two.png'", Agent.class).getResultList();
//            TypedQuery<Agent> query = entityManager.createQuery("getAllDetails", Agent.class);
            TypedQuery<Agent> query = entityManager.createQuery("from Agent ", Agent.class);
//            query.setParameter(1, agentName);
//            List<Agent> result=query.getResultList().stream().collect(Collectors.toList());
//            List<Agent> result = query.setParameter(1, address).getResultList().stream().collect(Collectors.toList());
            ArrayList<Agent> result = new ArrayList<>(query.setParameter(1, address).getResultList());

            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity("INVALID EMIALS ",HttpStatus.BAD_REQUEST );
    }

    public List<Agent> allAgentDetails() {

        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Agent> agentQuery = cBuilder.createQuery(Agent.class);
        Root<Agent> rootQuery = agentQuery.from(Agent.class);
        agentQuery.select(rootQuery);
        return agentRepository.findAll();
    }
}
