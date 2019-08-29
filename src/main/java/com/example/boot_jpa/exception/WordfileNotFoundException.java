package com.example.boot_jpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WordfileNotFoundException extends RuntimeException {
	public WordfileNotFoundException(String message)
	{
		super(message);
	}
    public WordfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
