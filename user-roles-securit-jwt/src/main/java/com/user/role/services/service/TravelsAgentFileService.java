package com.user.role.services.service;

import com.user.role.models.travel.TravelsAgentFile;
import org.springframework.web.multipart.MultipartFile;

public interface TravelsAgentFileService {

    TravelsAgentFile storeFile(MultipartFile file, Long userId, Long agentId);

    TravelsAgentFile getFile(String fileId);

    void deleteAgentFile(String fileId);
}
