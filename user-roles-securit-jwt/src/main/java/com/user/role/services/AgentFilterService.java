package com.user.role.services;

import com.user.role.models.travel.Agent;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Service
@Transactional
@Repository
public class AgentFilterService {

    @PersistenceContext
    private EntityManager manager;


    public void message(){
        if(manager.isOpen()){
            List<Agent> employees = manager.createQuery("from Agent ", Agent.class).getResultList();
            List<String> result = employees.stream().map(agent -> agent.getAgentName()).collect(Collectors.toList());
            for (String a:result) {
                System.err.print("GEt But All querys: "+ a);
            }
        }
    }
}
