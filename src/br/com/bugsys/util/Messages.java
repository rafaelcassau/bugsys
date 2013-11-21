package br.com.bugsys.util;

public interface Messages {
	
	String MSG_EMPTY            = 			"";
	String MSG_LOGIN_INCORRECT  = 			"Usu&aacute;rio ou Senha inv&aacute;lidos!";
	String MSG_INSERT_SUCCESS   = 			"Registro Cadastrado com Sucesso.";
	String MSG_UPDATE_SUCCESS   = 			"Registro Alterado com Sucesso.";
	String MSG_DELETE_SUCCESS   = 			"Registro Exclu�do com Sucesso.";
	String MSG_EXISTS_USERNAME  = 			"J&aacute; existe um registro cadastrado com o username informado.";
	String MSG_EXISTS_MAIL      = 			"J&aacute; existe um registro cadastrado com o e-mail informado.";
	String MSG_EXISTS_IE		= 			"J&aacute; existe um registro cadastrado com a Inscri��o Estadual informada.";
	String MSG_EXISTS_CORPORATE	= 			"J&aacute; existe um registro cadastrado com o Nome da Empresa informado.";
	String MSG_EXISTS_FANCY		= 			"J&aacute; existe um registro cadastrado com o Nome Fantasia informado.";
	String MSG_EXISTS_CNPJ		= 			"J&aacute; existe um registro cadastrado com o CNPJ informado.";
	String MSG_EXISTS_USECASE_NAME = 		"J&aacute; existe um caso de uso cadastrado com o Nome informado.";
	String MSG_EXISTS_USECASE_CODE = 		"J&aacute; existe um caso de uso cadastrado com o C�digo informado.";
	String MSG_EXISTS_WORKFLOW_TITLE = 		"J&aacute; existe um workflow cadastrado com o titulo informado.";
	String MSG_WORKFLOW_BOUND_PROJECT = 	"O workflow n�o pode ser exclu�do pois o mesmo est� vinculado a um ou mais projetos.";
	String MSG_EXISTS_PROJECT_NAME =		"J&aacute; existe um registro cadastrado com o Nome do Projeto informado.";
	String MSG_USER_PROJECT_VINCULED = 		"O usu�rio n�o pode ser exclu�do, pois o mesmo est� vinculado a um ou mais projetos.";
	String MSG_CLIENT_PROJECT_VINCULED =	"O cliente n�o pode ser exclu�do, pois o mesmo est� vinculado a um ou mais projetos.";
	String MSG_PROJECT_EVENT_VINCULED =		"O projeto n�o pode ser exclu�do, pois o mesmo est� vinculado a uma ou mais ocorr�ncias.";
}
