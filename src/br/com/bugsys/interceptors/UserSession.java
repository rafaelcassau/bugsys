package br.com.bugsys.interceptors;

import br.com.bugsys.user.User;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class UserSession {

	private User user;
	private Functionality functionality;
	private boolean hasPermission;
	
	public void setUser(User user) {
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
	
	public boolean isHasPermission() {
		return this.user.getClient() == null;
	}

	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
	}

	public Functionality getFunctionality() {
		return functionality;
	}

	public void setFunctionality(Functionality functionality) {
		this.functionality = functionality;
	}
}
