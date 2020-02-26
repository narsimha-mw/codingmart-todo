package com.user.role.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user.role.models.travel.Agent;
import com.user.role.exception.ResourceNotFoundException;
import com.user.role.repository.AgentRepository;
import com.user.role.repository.UserRepository;
import com.user.role.services.AgentFilterService;
import com.user.role.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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

    @Autowired
    AgentFilterService agentFilterService;

    @GetMapping(value = "/list", produces = "application/json")
    public List<Agent> getAllPosts(@PathVariable Long userId) throws JsonProcessingException {
        List<Agent> result = agentService.allAgentDetails(userId);

    agentFilterService.message();

    return  result;
    }

    @PostMapping("/add")
    public Agent createUserAgent(@PathVariable (value = "userId") Long userId,
                                 @Valid @RequestBody Agent agent) {
        return  userRepository.findById(userId).map(a -> {
            agent.setUser(a);
            return agentRepository.save(agent);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }

    @PutMapping(value = ("/{agentId}"), produces = "application/json")
    public Optional<ResponseEntity<Agent>> updateAgentDetails(@PathVariable(value = "userId") Long userId,
                                                              @PathVariable(value = "agentId") Long agentId,
                                                              @Valid @RequestBody Agent agentRequest) {
        Optional<Long> validateId = userRepository.findById(userId).map(user -> user.getId());
        if (validateId.isPresent()) {
            return agentRepository.findById(agentId).map(agent -> {
                agent.setAgentName(agentRequest.getAgentName());
                agent.setEmail(agentRequest.getEmail());
                agent.setAgentMobileNumber(agentRequest.getAgentMobileNumber());
                return ResponseEntity.ok(agentRepository.save(agent));
            });
        }
        return null;
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<?> deleteUserAgent(@PathVariable (value = "userId") Long userId,
                                           @PathVariable (value = "agentId") Long agentId) {
        return agentRepository.findByIdAndUserId(agentId, userId).map(agent -> {
            agentRepository.delete((Agent) agent);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + agentId + " and userId " + userId));
    }
}
