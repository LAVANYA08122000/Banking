package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentUploadDto {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	

}
