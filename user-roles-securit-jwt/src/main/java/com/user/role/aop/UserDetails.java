package com.user.role.aop;

import com.user.role.repository.UserRepository;
import com.user.role.security.jwt.AuthEntryPointJwt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class UserDetails {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Before("execution(* com.user.role.services.agent.AgentServiceImpl..*(..)))")
    public void userIdReturning(JoinPoint joinPoint) {
        System.err.println("+++++++++++++++++");
        logger.info(" ###### Returning for class : {} ; Method : {} ", joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
        //Optional<Long> validateId =  userRepository.findById(userId).map(user -> user.getId());
//        if () {
//            logger.info(" ###### with value : {}", result.toString());
//        } else{
//            logger.info(" ###### with null as return value.");
//        }
        System.err.println("VFBVKJNDNMFXVNFDN GDSKJNDGKJDNGK");
    }
}
