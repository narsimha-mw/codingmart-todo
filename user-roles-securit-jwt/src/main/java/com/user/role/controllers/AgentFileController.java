package com.user.role.controllers;

import com.user.role.models.travel.AgentFile;
import com.user.role.payload.response.UploadFileResponse;
import com.user.role.services.AgentFileService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user/{userId}/agent/{agentId}")
@PreAuthorize("hasRole('ADMIN')")
public class AgentFileController {

    private static final Logger logger = LoggerFactory.getLogger(AgentFileController.class);

    @Autowired
    AgentFileService agentFileService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        Long userId=5L;
        Long agentId = 4L;

        AgentFile agentFile = agentFileService.storeFile(file, userId, agentId);
        if(agentFile instanceof AgentFile){
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(agentFile.getFileName())
//                .port(agentFile.getId().toString())
                .toUriString();

        String[] fileDownload=fileDownloadUri.split("downloadFile",2);
        StringBuilder builder=new StringBuilder();
        builder.append(fileDownload[0])
                .append("api/user/")
                .append(userId)
                .append("/agent/")
                .append(agentId)
                .append("/downloadFile")
                .append(fileDownload[1]);

        return new UploadFileResponse(agentFile.getFileName(), builder.toString(),
                file.getContentType(), file.getSize()/(1204*1204));}

    @PostMapping("/upload_multi_files")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        AgentFile agentFile;
             agentFile = agentFileService.getFile(fileId);
             return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(agentFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + agentFile.getFileName() + "\"")
                .body(new ByteArrayResource(agentFile.getData()));
    }
}