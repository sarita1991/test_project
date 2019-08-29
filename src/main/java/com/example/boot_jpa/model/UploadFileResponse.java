package com.example.boot_jpa.model;

public class UploadFileResponse {
	private String fileName;
	private String fileType;
	private String fileDownloadUri;
	private long size;

	public UploadFileResponse(String fileName,String fileDownloadUri,  String fileType, long size) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.size = size;
		this.fileDownloadUri=fileDownloadUri;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
	

}
