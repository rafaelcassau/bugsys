package br.com.bugsys.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.bugsys.client.Client;
import br.com.bugsys.client.ClientDAO;
import br.com.bugsys.employeeType.EmployeeType;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String mail;
	private String functionality;
	
	@OneToOne
	private Client client;
	
	@OneToOne(cascade = CascadeType.DETACH, optional = true, fetch = FetchType.EAGER)
	private EmployeeType employeeType;
	
	public Integer getId() {
		return id;
	}
	public User setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public String getUsername() {
		return username;
	}
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public String getName() {
		return name;
	}
	public User setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getMail() {
		return mail;
	}
	public User setMail(String mail) {
		this.mail = mail;
		return this;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}
	public User setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
		return this;
	}
	
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	public String getFunctionality() {
		return functionality;
	}

	public Client getClient() {
		if (client == null) {
			client = new ClientDAO().findClientByUserId(this.id);
		}
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
