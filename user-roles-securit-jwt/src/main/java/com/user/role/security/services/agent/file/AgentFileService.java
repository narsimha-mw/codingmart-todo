package com.user.role.security.services.agent.file;

import org.springframework.web.multipart.MultipartFile;

public interface AgentFileService {

    boolean findByUserId(Long userId);
    AgentFile storeFile(MultipartFile file);

    AgentFile getFile(String fileId);
}
