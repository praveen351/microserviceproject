package com.inventory.io.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {
	
	//Resource not found exception
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody InventoryException handleResponseNotFound(final ResourceNotFoundException
			exception, final HttpServletRequest request) {
		
		InventoryException error = new InventoryException();
		error.setErrorMessage(exception.getMessage());
        error.callerURL(request.getRequestURI());
        return error;
	}
	
	//Generic error handle case
	
		@ExceptionHandler(Exception.class)
	    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	    public @ResponseBody InventoryException handleException(final Exception exception,
	            final HttpServletRequest request) {
			InventoryException error = new InventoryException();
	        error.setErrorMessage(exception.getMessage());
	        error.callerURL(request.getRequestURI());
	        return error;
	    }
	

}
