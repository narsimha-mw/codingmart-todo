package com.user.role.services.serviceImpl;

import com.user.role.exception.FileStorageException;
import com.user.role.exception.RecordNotFoundException;
import com.user.role.models.User;
import com.user.role.models.travel.TravelsAgent;
import com.user.role.models.travel.TravelsAgentFile;
import com.user.role.repository.TravelsAgentFileRepository;
import com.user.role.repository.TravelsAgentRepository;
import com.user.role.repository.UserRepository;
import com.user.role.services.service.TravelsAgentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class travelsAgentFileServiceImpl implements TravelsAgentFileService {

    @Autowired
    TravelsAgentFileRepository agentFileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelsAgentRepository agentRepository;

    private boolean findByUserIdAndAgentId(Long userId) {
        Optional<Long> validateId = userRepository.findById(userId).map(User::getId);
        if (validateId.isPresent())
            return true;
        return false;
    }

    @Override
    public TravelsAgentFile storeFile(MultipartFile file, Long userId, Long agentId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            TravelsAgentFile dbFile = new TravelsAgentFile(fileName, file.getContentType(), file.getBytes());
            if(findByUserIdAndAgentId(userId)) {
                Optional<TravelsAgent> getAgentDetails = agentRepository.findById(agentId);
            getAgentDetails.map(agent -> { dbFile.setAgent(agent);
                return dbFile;
            });
            }
            return agentFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }catch (NumberFormatException n){
            return null;
        }
    }

    @Override
    public TravelsAgentFile getFile(String fileId) {
        return  agentFileRepository.findById(fileId)
                .orElseThrow(()->new RecordNotFoundException("File not found with id " + fileId));
        }

    @Override
    public void deleteAgentFile(String fileId) {
         agentFileRepository.deleteById(fileId);
    }
}
