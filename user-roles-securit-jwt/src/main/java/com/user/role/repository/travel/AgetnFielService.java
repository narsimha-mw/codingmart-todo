package com.user.role.repository.travel;

import com.user.role.models.travel.AgentFile;
import org.springframework.web.multipart.MultipartFile;

public interface AgetnFielService {
    AgentFile storeFile(MultipartFile file);
}
