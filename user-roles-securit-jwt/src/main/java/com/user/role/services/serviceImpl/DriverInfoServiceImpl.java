package com.user.role.services.serviceImpl;

import com.user.role.models.travel.DriverInfo;
import com.user.role.repository.TravelsAgentRepository;
import com.user.role.repository.DriverInfoRepository;
import com.user.role.services.service.DriverInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverInfoServiceImpl implements DriverInfoService {


    @Autowired
    private TravelsAgentRepository agentRepository;
    @Autowired
    private DriverInfoRepository driverInfoRepository;

    public DriverInfoServiceImpl(TravelsAgentRepository agentRepository, DriverInfoRepository driverInfoRepository) {
        this.agentRepository = agentRepository;
        this.driverInfoRepository = driverInfoRepository;
    }

    @Override
    public boolean saveAgentByDriverInfo(Long userId, Long agentId, DriverInfo driverInfo) {
        // boolean b=agentRepository.findUserIdByAgentId(agentId, userId);
//         System.err.print("b: "+ b);
//         if(b){
//           driverInfoRepository.save(driverInfo);
//           return true;
//         }
         return  false;
    }
}
