package com.user.role.services.serviceImpl;

import com.user.role.models.travel.Agent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgentFilterService {

    @PersistenceContext
    private EntityManager manager;

    public void message(){
        if(manager.isOpen()){
            List<Agent> employees = manager.createQuery("from Agent ", Agent.class).getResultList();
            List<String> result = employees.stream().map(agent -> agent.getAgentName()).collect(Collectors.toList());
            for (String a:result) {
                System.err.println("GEt But All querys: "+ a);
            }
            List<Agent> files = manager.createQuery("select a from Agent a join a.agentFiles f where a.agentName = 'abcd' and f.fileName = 'two.png'", Agent.class).getResultList();
            for (Agent a:files) {
                System.err.print("GEt But All : "+ a.getAgentFiles());
            }
        }
    }

//    public  List<Agent> filterAndSearch(String param){
//        Query query = manager.createNamedQuery("filterAndSearch");
//        query.setParameter("queryParam", param);
//        List results = query.getResultList();
//        return  results;
//    }
}
