package com.user.role.payload.response;

public class UploadFileResponse {
    private String  fileName;
    private String  fileDownloadUri;
    private String fileType;
    private Long fileSize;

    public UploadFileResponse(String fileName, String fileDownloadUri, String contentType, long size) {

        this.fileName=fileName;
        this.fileDownloadUri=fileDownloadUri;
        this.fileType=contentType;
        this.fileSize=size;
    }
}
