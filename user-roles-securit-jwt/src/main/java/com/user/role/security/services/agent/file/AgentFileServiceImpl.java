package com.user.role.security.services.agent.file;

import com.user.role.exception.FileStorageException;
import com.user.role.exception.MyFileNotFoundException;
import com.user.role.models.travel.Agent;
import com.user.role.models.travel.AgentFile;
import com.user.role.repository.UserRepository;
import com.user.role.repository.travel.agent.AgentRepository;
import com.user.role.repository.travel.agent.file.AgentFileRepository;
import com.user.role.security.services.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AgentFileServiceImpl implements AgentFileService {

    @Autowired
    AgentFileRepository agentFileRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean findByUserId(Long userId) {
        Optional<Long> validateId =  userRepository.findById(userId).map(user -> user.getId());
        if(validateId.isPresent())
            return  true;
        else
            return false;
    }

    @Override
    public AgentFile storeFile(MultipartFile file) {
      //  System.err.println("MultipartFile serviceImpl: "+ file);
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
     //   System.err.println("MultipartFile  fileName serviceImpl: "+ fileName);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                System.err.println("file name is not found....");
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            AgentFile dbFile = new AgentFile(fileName, file.getContentType(), file.getBytes());
      //      System.err.println("file name is found...."+ dbFile);
            return agentFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public AgentFile getFile(String fileId) {
        return agentFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
