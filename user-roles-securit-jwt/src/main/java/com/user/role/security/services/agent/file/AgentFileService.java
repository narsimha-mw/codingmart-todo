package com.user.role.security.services.agent.file;

import com.user.role.models.travel.Agent;
import com.user.role.models.travel.AgentFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AgentFileService {

    boolean findByUserId(Long userId);
    AgentFile storeFile(MultipartFile file);

    AgentFile getFile(String fileId);
}
