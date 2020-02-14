package com.user.role.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponse {

    private String fileName;
    private String downloadLink;
    private String fileType;
    private long size;

}
