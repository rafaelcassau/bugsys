package br.com.bugsys.util;

public enum AjaxResponseEnum {

	SUCCESS("success"),
	ERROR("error");
	
	private String response;
	
	private AjaxResponseEnum(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}
}
