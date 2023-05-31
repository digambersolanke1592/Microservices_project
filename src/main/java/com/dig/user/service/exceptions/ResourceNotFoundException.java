package com.dig.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("Resource not found server !!");
	}
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

