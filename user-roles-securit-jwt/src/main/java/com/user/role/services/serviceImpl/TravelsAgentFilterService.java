package com.user.role.services.serviceImpl;

import com.user.role.models.travel.TravelsAgent;
import com.user.role.repository.TravelsAgentRepository;
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
public class TravelsAgentFilterService {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TravelsAgentRepository agentRepository;


    public TravelsAgentFilterService(EntityManager entityManager, TravelsAgentRepository agentRepository) {
        this.entityManager = entityManager;
        this.agentRepository = agentRepository;
    }

    public ResponseEntity<List<TravelsAgent>> getAgentNameAndEmail(String address) {

        if (entityManager.isOpen()) {
//            List<TravelsAgent> employees = entityManager.createQuery("from TravelsAgent ", TravelsAgent.class).getResultList();
//            List<String> result = employees.stream().map(agent -> agent.getAgentName()).collect(Collectors.toList());
//            for (String a:result) {
//            return new ResponseEntity<>("you enter invalid password credentials, please try valid once.", HttpStatus.NOT_ACCEPTABLE);

//            }
//            List<TravelsAgent> files = entityManager.createQuery("select a from TravelsAgent a join a.agentFiles f where a.agentName = 'abcd' and f.fileName = 'two.png'", TravelsAgent.class).getResultList();
//            TypedQuery<TravelsAgent> query = entityManager.createQuery("getAllDetails", TravelsAgent.class);
            TypedQuery<TravelsAgent> query = entityManager.createQuery("from TravelsAgent ", TravelsAgent.class);
//            query.setParameter(1, agentName);
//            List<TravelsAgent> result=query.getResultList().stream().collect(Collectors.toList());
//            List<TravelsAgent> result = query.setParameter(1, address).getResultList().stream().collect(Collectors.toList());
            ArrayList<TravelsAgent> result = new ArrayList<>(query.setParameter(1, address).getResultList());

            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity("INVALID EMIALS ",HttpStatus.BAD_REQUEST );
    }

    public List<TravelsAgent> allAgentDetails(Long userId) {
//
//        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<TravelsAgent> agentQuery = cBuilder.createQuery(TravelsAgent.class);
//        Root<TravelsAgent> rootQuery = agentQuery.from(TravelsAgent.class);
//        agentQuery.select(rootQuery);
        return agentRepository.findByUserId(userId);
    }
}
