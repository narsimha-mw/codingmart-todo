package com.user.role.controllers;

import com.user.role.models.travel.TravelsAgent;
import com.user.role.models.travel.TravelsAgentFile;
import com.user.role.payload.response.UploadFileResponse;
import com.user.role.repository.TravelsAgentRepository;
import com.user.role.services.service.TravelsAgentFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user/{userId}/agent/{agentId}")
@PreAuthorize("hasRole('ADMIN')")
public class TravelsAgentFileController {

    private static final Logger logger = LoggerFactory.getLogger(TravelsAgentFileController.class);

    @Autowired
    private TravelsAgentFileService agentFileService;
    @Autowired
    private TravelsAgentRepository agentRepository;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "agentId") Long agentId,
                                         @RequestParam("file") MultipartFile file) {
        TravelsAgentFile agentFile = agentFileService.storeFile(file, userId, agentId);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(agentFile.getFileName())
                .toUriString();

        String[] fileDownload=fileDownloadUri.split("downloadFile",2);
        String customDownloadURL = fileDownload[0] + "api/user/" + userId + "/agent/"
                                   +agentId +"/downloadFile" + fileDownload[1];
        return new UploadFileResponse(agentFile.getFileName(), customDownloadURL,
                file.getContentType(), Long.toString(file.getSize()/1024));
    }

    @PostMapping("/upload_multi_files")
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable(value = "userId") Long userId,
                                                        @PathVariable(value = "agentId") Long agentId,
                                                        @RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(userId, agentId, file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        TravelsAgentFile agentFile;
             agentFile = agentFileService.getFile(fileId);
             return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(agentFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + agentFile.getFileName() + "\"")
                .body(new ByteArrayResource(agentFile.getData()));
    }


    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<?> deleteUserAgent(@PathVariable (value = "agentId") Long agentId,
                                             @PathVariable (value = "fileId") String fileId) {
        Optional<Object> deletedAgentId = agentRepository.findById(agentId).map(TravelsAgent::getId);

        if(deletedAgentId.isPresent()) {
            agentFileService.deleteAgentFile(fileId);
            return ResponseEntity.ok("Deletd file");
        }
        return null;
}
}