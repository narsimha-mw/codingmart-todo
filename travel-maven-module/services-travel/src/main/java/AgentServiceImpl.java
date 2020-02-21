import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
@ComponentScan(basePackages = {"com.travel.*"})
public class AgentServiceImpl implements AgentService {
    @Autowired
    AgentRepository agentRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Agents> allAgentDetails(Long userId) {

        System.err.println("++++++++++++++++++");
 if(entityManager instanceof  EntityManager){
     System.err.println("Entity manager created obj");
 }
        CriteriaBuilder cBuilder=entityManager.getCriteriaBuilder();
        //System.err.println("cBuilder"+ cBuilder);
        CriteriaQuery<Agents> agentQuery = cBuilder.createQuery(Agents.class);
       // System.err.println("agentQuery"+ agentQuery);
        Root<Agents> rootQuery = agentQuery.from(Agents.class);
        //System.err.println("rootQuery"+ rootQuery);
        agentQuery.select(rootQuery);
        List<Agents> result = entityManager.createQuery(agentQuery).getResultList();
        for (Agents a:result) {
            System.err.println("Agent email is: "+ a.getEmail());
        }

        if(result instanceof  Agents){
            System.err.println("**(*(*(*(*"+((Agents) result).getEmail());
        }else if(result==null){
            System.err.println("Is not get result in null agent");
        }
        else{
            System.err.println("Is not get result in agetn object");
        }
//        result.forEach(System.err::println);

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
    public void saveAgentDetails(Long userId, Agents agents) {

//        Optional<Object> result = userRepository.findById(userId)
//                                                .map(a->agents.setUser(a););
            agentRepository.save(agents);
    }
}
