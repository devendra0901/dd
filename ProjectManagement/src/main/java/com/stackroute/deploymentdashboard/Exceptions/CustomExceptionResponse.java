
package com.stackroute.deploymentdashboard.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class CustomExceptionResponse extends Exception {
    private String errorDescription;
    private String error;
	
	public String getErrorMessage() {
		return errorDescription;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorDescription = errorMessage;
	}
	
	public String getErrors() {
		return error;
	}
	public void setErrors(String error) {
		this.error = error;
	}
	public BindingResult getBindingResult() {
		// TODO Auto-generated method stub
		return null;
	}
}