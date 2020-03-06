package com.user.role.controllers;

import com.user.role.exception.RecordNotFoundException;
import com.user.role.models.User;
import com.user.role.models.travel.TravelsAgent;
import com.user.role.repository.TravelsAgentRepository;
import com.user.role.repository.UserRepository;
import com.user.role.services.TravelsAgentService;
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
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TravelsAgentController {

    @Autowired
    private TravelsAgentService agentService;

    @Autowired
    private TravelsAgentRepository agentRepository;

    @Autowired
    private  UserRepository userRepository;

    public TravelsAgentController(UserRepository userRepository, TravelsAgentService agentService, TravelsAgentRepository agentRepository) {
        this.userRepository=userRepository;
        this.agentService = agentService;
        this.agentRepository = agentRepository;
    }

    @GetMapping(path = "/address={agentAddress}")
public List<TravelsAgent> getAgents(@PathVariable(value = "agentAddress") String agentAddress) {
    // code here
    List<TravelsAgent> response = agentService.getAgentNameAndEmail(agentAddress);
    return response;
}

    @GetMapping(value = "/list", produces = "application/json")
    public List<TravelsAgent> getAllPosts(@PathVariable Long userId)  {
        boolean validUserId = ValidUserId(userId);
        if(validUserId) {
            return agentService.allAgentDetails();
                }
    return null;
    }

    @PostMapping("/add")
    public TravelsAgent createUserAgent(@PathVariable (value = "userId") Long userId,
                                        @Valid @RequestBody TravelsAgent agent) {
        return  userRepository.findById(userId).map(a -> {
            agent.setUser(a);
            return agentRepository.save(agent);
        }).orElseThrow(() -> new RecordNotFoundException("UserId " + userId + " not found"));
    }

    @PutMapping(value = ("/{agentId}"), produces = "application/json")
    public Optional<ResponseEntity<TravelsAgent>> updateAgentDetails(@PathVariable(value = "userId") Long userId,
                                                                     @PathVariable(value = "agentId") Long agentId,
                                                                     @Valid @RequestBody TravelsAgent agentRequest) {
        Optional<Long> validateId = userRepository.findById(userId).map(User::getId);
        if (validateId.isPresent()) {
            return agentRepository.findById(agentId).map(agent -> {
                agent.setAgentName(agentRequest.getAgentName());
                agent.setEmail(agentRequest.getEmail());
                agent.setAddress(agentRequest.getAddress());
                agent.setAgentMobileNumber(agentRequest.getAgentMobileNumber());
                return ResponseEntity.ok(agentRepository.save(agent));
            });
        }
        return Optional.empty();
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<?> deleteUserAgent(@PathVariable (value = "userId") Long userId,
                                           @PathVariable (value = "agentId") Long agentId) {
        boolean validUserId = ValidUserId(userId);
        if(validUserId) {
               agentRepository.deleteById(agentId);
           }
            return ResponseEntity.ok().build();
    }

    private boolean ValidUserId(@PathVariable("userId") Long userId) {
        return userRepository.findById(userId).map(User::getId).isPresent();
    }
}
