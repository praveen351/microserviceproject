package com.inventory.io.exception;

public class InventoryException {
	
	private String errorMessage;
	private String requestedURI;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getRequestedURI() {
		return requestedURI;
	}
	public void callerURL(String requestedURI) {
		this.requestedURI = requestedURI;
	}

}
