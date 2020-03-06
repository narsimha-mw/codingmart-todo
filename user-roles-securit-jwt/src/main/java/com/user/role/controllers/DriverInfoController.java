package com.user.role.controllers;

import com.user.role.models.travel.DriverInfo;
import com.user.role.services.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*",allowedHeaders="*")
@RequestMapping("/api/user/{userId}/agent/{agentId}")
@PreAuthorize("hasRole('ADMIN') ")
public class DriverInfoController {

    @Autowired
    private DriverInfoService driverInfoService;

    public DriverInfoController(DriverInfoService driverInfoService) {
        this.driverInfoService=driverInfoService;
       }

//    @GetMapping(path = "/address={agentAddress}")
//    public List<TravelsAgent> getAgents(
////        @PathVariable(value = "agentName") String agentName,
//                     @PathVariable(value = "agentAddress") String agentAddress) {
//    // code here
//    List<TravelsAgent> response = agentService.getAgentNameAndEmail(agentAddress);
//    return response;
//}

//    @GetMapping(value = "/list", produces = "application/json")
//    public List<TravelsAgent> getAllPosts(@PathVariable Long userId)  {
//        boolean validUserId = ValidUserId(userId);
//        if(validUserId) {
//            return agentService.allAgentDetails();
//                }
//    return null;
//    }

    @PostMapping("/driverInfo/add")
    public DriverInfo createUserAgent(@PathVariable (value = "userId") Long userId,
                                 @PathVariable (value = "agentId") Long agentId,
                                 @Valid @RequestBody DriverInfo driverInfo) {

        //boolean response = driverInfoService.saveAgentByDriverInfo(userId, agentId, driverInfo);
        System.err.print("endpoint is called:"+ "response");
        //        return  userRepository.findById(userId).map(a -> {
//            agent.setUser(a);
//            return agentRepository.save(agent);
//        }).orElseThrow(() -> new RecordNotFoundException("UserId " + userId + " not found"));
    return null;
    }

//    @PutMapping(value = ("/{agentId}"), produces = "application/json")
//    public Optional<ResponseEntity<TravelsAgent>> updateAgentDetails(@PathVariable(value = "userId") Long userId,
//                                                              @PathVariable(value = "agentId") Long agentId,
//                                                              @Valid @RequestBody TravelsAgent agentRequest) {
//        Optional<Long> validateId = userRepository.findById(userId).map(User::getId);
//        if (validateId.isPresent()) {
//            return agentRepository.findById(agentId).map(agent -> {
//                agent.setAgentName(agentRequest.getAgentName());
//                agent.setEmail(agentRequest.getEmail());
//                agent.setAddress(agentRequest.getAddress());
//                agent.setAgentMobileNumber(agentRequest.getAgentMobileNumber());
//                return ResponseEntity.ok(agentRepository.save(agent));
//            });
//        }
//        return Optional.empty();
//    }
//
//    @DeleteMapping("/{agentId}")
//    public ResponseEntity<?> deleteUserAgent(@PathVariable (value = "userId") Long userId,
//                                           @PathVariable (value = "agentId") Long agentId) {
//        boolean validUserId = ValidUserId(userId);
//        if(validUserId) {
//               agentRepository.deleteById(agentId);
//           }
//            return ResponseEntity.ok().build();
//    }
//
//    private boolean ValidUserId(@PathVariable("userId") Long userId) {
//        return userRepository.findById(userId).map(User::getId).isPresent();
//    }

}
