package br.com.bugsys.interceptors;

import br.com.bugsys.user.User;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class UserSession {

	private User user;
	private String functionality = "Dashboard";
	
	public void login(User user) {
		this.user = user;
	}
	
	public void logout() {
		this.user = null;
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean isLogado() {
		return user != null;
	}
	
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	
	public String getFunctionality(String functionality) {
		return this.functionality;
	}
}
