package com.user.role.repository.travel;

import com.user.role.exception.FileStorageException;
import com.user.role.models.travel.AgentFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AgentFileServiceImpl implements  AgetnFielService{

    @Autowired
    AgentFileRepository agentFileRepository;

    @Override
    public AgentFile storeFile(MultipartFile file) {

            // Normalize file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try {
                // Check if the file's name contains invalid characters
                if(fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                AgentFile dbFile = new AgentFile(fileName, file.getContentType(), file.getBytes());

                return agentFileRepository.save(dbFile);
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        }
}
