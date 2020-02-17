package com.user.role.controllers;

import com.user.role.models.travel.AgentFile;
import com.user.role.payload.response.UploadFileResponse;
import com.user.role.repository.travel.AgetnFielService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/user/{userId}/agent/{agentId}")
public class AgentFileController {

    private static final Logger logger = LoggerFactory.getLogger(AgentFileController.class);

    @Autowired
    AgetnFielService agetnFielService;

    @GetMapping("/hello")
    public String msg(@PathVariable(value = "userId") Long userId,
                      @PathVariable(value = "agentId") Long agentId){
        System.err.println(userId+","+ agentId);
        logger.debug("file action cointroler is called",userId, agentId );
        return  "nmbhj b hj";
    }

        @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        AgentFile agentFile = agetnFielService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(agentFile.getId())
                .toUriString();

        System.err.println(fileDownloadUri);
        String[] splitDownloadLink = fileDownloadUri.split("downloadFile", 2);
        int count=0;
        StringBuilder builder=new StringBuilder();
        for (String result:splitDownloadLink ) {
            if(count==0) {
                builder.append(result).append("/api/user/3/agent/3/downloadFile");
                count++;
            }else if(count>0){
                builder.append(result);
            }
        }
        UploadFileResponse listresultFile = new UploadFileResponse(agentFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
        System.err.println("listresultFile: "+ listresultFile);

        return listresultFile;
    }
}
