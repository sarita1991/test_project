package com.example.boot_jpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileStorageException extends RuntimeException {
	public FileStorageException(String message)
	{
		super(message);
	}
	   public FileStorageException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
