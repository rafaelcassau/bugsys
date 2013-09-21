package br.com.bugsys.login;

public class Login {

	private String username;
	private String password;
	
	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public Login setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}
	public Login setPassword(String password) {
		this.password = password;
		return this;
	}
}
