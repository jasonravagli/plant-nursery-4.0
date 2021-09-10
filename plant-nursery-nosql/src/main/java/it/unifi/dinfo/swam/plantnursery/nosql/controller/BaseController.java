package it.unifi.dinfo.swam.plantnursery.nosql.controller;

public abstract class BaseController {
	
	private boolean errorOccurred;
	private String errorMessage;
	
	public void cleanErrorFields() {
		errorOccurred = false;
		errorMessage = null;
	}
	
	public boolean isErrorOccurred() {
		return errorOccurred;
	}
	
	public void setErrorOccurred(boolean errorOccurred) {
		this.errorOccurred = errorOccurred;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
