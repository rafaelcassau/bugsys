package br.com.bugsys.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	@OneToOne(cascade = CascadeType.DETACH, optional = true)
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
}
