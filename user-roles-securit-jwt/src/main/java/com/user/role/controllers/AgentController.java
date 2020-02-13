package com.user.role.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user.role.models.travel.Agent;
import com.user.role.payload.response.MessageResponse;
import com.user.role.repository.UserRepository;
import com.user.role.repository.travel.AgentRepository;
import com.user.role.security.services.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*",allowedHeaders="*")
@RequestMapping("/api/user/{userId}/agent")
@PreAuthorize("hasRole('ADMIN')")
public class AgentController {

    @Autowired
    AgentService agentService;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/msg")
    public String message(){
        return  "Admin access this controller";
    }

    @GetMapping(value = "/list", produces = "application/json")
    public List<Agent> getAllPosts(@PathVariable Long userId) throws JsonProcessingException {

        List<Agent> result = agentService.allAgentDetails(userId);
//        System.err.println( "  getAllPosts: "+ result.stream().map(agent -> agent.getEmail()));
        return  result;
    }
    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<?> createNewAgent( @PathVariable Long userId, @RequestBody Agent agent) {
//        ModelMapper modelMapper = new ModelMapper();

        agentService.saveAgentDetails(userId, agent);
        return ResponseEntity.ok(new MessageResponse("Agent registered successfully!"));
    }

    @PutMapping(value = ("/{agentId}"), produces = "application/json")
    public Optional<ResponseEntity<Agent>> updateAgentDetails(@PathVariable(value = "userId") Long userId,
                                                              @PathVariable(value = "agentId") Long agentId,
                                                              @Valid @RequestBody Agent agentRequest) {
        Optional<Long> validateId = userRepository.findById(userId).map(user -> user.getId());
        if (validateId.isPresent()) {
            return agentRepository.findById(agentId).map(agent -> {
                agent.setAgentName(agentRequest.getAgentName());
//                agent.setEmail(agentRequest.getEmail());
//                agent.setAgentMobileNumber(agentRequest.getAgentMobileNumber());
                return ResponseEntity.ok(agentRepository.save(agent));
            });
        }
        return null;
    }
}
