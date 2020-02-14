package com.user.role.controllers;

import com.user.role.models.travel.AgentFile;
import com.user.role.payload.response.UploadFileResponse;
import com.user.role.security.services.agent.file.AgentFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/user/{userId}/agent/{agentId}")
public class AgentFileController {
    private static final Logger logger = LoggerFactory.getLogger(AgentFileController.class);

    @Autowired
    AgentFileService dbFileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "agentId") Long agentId) {
        System.err.println();
      //  System.err.println("file: "+ file);
        AgentFile agentFile = dbFileStorageService.storeFile(file);
      //  System.err.println("agentFile: "+ agentFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(agentFile.getId())
                .toUriString();
        ArrayList<String> linkFile=new ArrayList<String>();

            for (String realDownloadLink: fileDownloadUri.split("downloadFile", 2)) {
                linkFile.add(realDownloadLink);
            }
            StringBuilder chars = new StringBuilder();
            final int[] count = {0};
            linkFile.forEach(l ->{
                    if(count[0] ==0){
                        chars.append(l);
                        count[0]++;
                    }
                    else if(count[0] >0) {
                        System.err.println(userId+"++++"+agentId);
                        chars.append("api/userId/`${userId}`/agent/`${agentId}`/downloadFile" + l);
                    }
            });
            System.err.println(chars.toString());
        UploadFileResponse reseultOfAgetnFile = new UploadFileResponse(agentFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
        System.err.println("agentFile reseultOfAgetnFile: "+ reseultOfAgetnFile);
        return  reseultOfAgetnFile;
    }

    @PostMapping("/multiple_uplodeFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        System.err.println("uploadMultipleFiles MultipartFile: "+ files);

        List<UploadFileResponse> uploadMultipleFiles = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,null,null))
                .collect(Collectors.toList());
        System.err.println("uploadMultipleFiles uploadMultipleFiles: "+ uploadMultipleFiles);

        return  uploadMultipleFiles;
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        AgentFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}
