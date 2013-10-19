package br.com.bugsys.util;

public enum FunctionalityEnum {
	
	DASHBOARD("Dashboard"),
	CLIENTS("Clientes"),
	PROJECTS("Projetos"),
	WORKFLOWS("Workflows"),
	USERS("Usuários"),
	REPORTS("Relatórios"),
	CONFIGURATION("Configurações");
	
	private String Functionality;
	
	private FunctionalityEnum(String Functionality) {
		this.Functionality = Functionality;
	}

	public String getFunctionality() {
		return Functionality;
	}
}
