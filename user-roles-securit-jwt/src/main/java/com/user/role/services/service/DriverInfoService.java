package com.user.role.services.service;

import com.user.role.models.travel.DriverInfo;

public interface DriverInfoService {

    boolean saveAgentByDriverInfo(Long userId, Long agentId, DriverInfo driverInfo);
}
