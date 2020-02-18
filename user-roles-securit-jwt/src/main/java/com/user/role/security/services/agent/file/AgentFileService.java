package com.user.role.security.services.agent.file;

import com.user.role.models.travel.AgentFile;
import org.springframework.web.multipart.MultipartFile;

public interface AgentFileService {

    AgentFile storeFile(MultipartFile file, Long userId, Long agentId);

    AgentFile getFile(AgentFile fileId);
}
