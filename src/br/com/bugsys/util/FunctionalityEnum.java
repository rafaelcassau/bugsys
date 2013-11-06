package br.com.bugsys.util;

public enum FunctionalityEnum {
	
	DASHBOARD("Dashboard"),
	CLIENTS("Clientes"),
	PROJECTS("Projetos"),
	WORKFLOWS("Workflows"),
	USERS("Usu�rios"),
	EVENT("Ocorr�ncias"),
	REPORTS("Relat�rios"),
	CONFIGURATION("Configura��es");
	
	private String Functionality;
	
	private FunctionalityEnum(String Functionality) {
		this.Functionality = Functionality;
	}

	public String getFunctionality() {
		return Functionality;
	}
}
