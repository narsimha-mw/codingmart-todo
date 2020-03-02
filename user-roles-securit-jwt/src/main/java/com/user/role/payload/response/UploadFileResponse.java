package com.user.role.payload.response;

import lombok.Data;

@Data
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private String size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, String size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size + " KB";
    }
}
