package com.m2s.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	 	@ExceptionHandler(Exception.class)
	    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
	        List<String> details = new ArrayList<>();
	        details.add(ex.getLocalizedMessage());
	        ErrorDetails error = new ErrorDetails(new Date(),details.get(0), details);
	        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	        List<String> details = new ArrayList<>();
	        details.add(ex.getLocalizedMessage());
	        ErrorDetails error = new ErrorDetails(new Date(),"Record Not Found", details);
	        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	    }
	 
	    @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        List<String> details = new ArrayList<>();
	        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
	            details.add(error.getDefaultMessage());
	        }
	        ErrorDetails error = new ErrorDetails(new Date(),"Validation Failed", details);
	        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	    }
	    
	    @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    	List<String> details = new ArrayList<>();    
	    	ErrorDetails error = new ErrorDetails(new Date(),"Invalid Data",details);
		        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
}
