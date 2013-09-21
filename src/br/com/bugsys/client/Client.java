package br.com.bugsys.client;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.bugsys.user.User;

@Entity
public class Client {

	@Id
	@GeneratedValue
	private Integer id;
	@OneToOne(cascade = CascadeType.REMOVE)
	private User user;
	private String CNPJ;
	private String stateRegistration;
	private String corporateName;
	private String fancyName;
	private String phone;
	private String mobile;
	private String address;
	
	public Integer getId() {
		return id;
	}
	
	public Client setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}
	
	public Client setUser(User user) {
		this.user = user;
		return this;
	}
	
	public String getCNPJ() {
		return CNPJ;
	}
	
	public Client setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
		return this;
	}
	
	public String getStateRegistration() {
		return stateRegistration;
	}
	public Client setStateRegistration(String stateRegistration) {
		this.stateRegistration = stateRegistration;
		return this;
	}
	
	public String getCorporateName() {
		return corporateName;
	}
	
	public Client setCorporateName(String corporateName) {
		this.corporateName = corporateName;
		return this;
	}
	
	public String getFancyName() {
		return fancyName;
	}
	
	public Client setFancyName(String fancyName) {
		this.fancyName = fancyName;
		return this;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public Client setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	
	public String getMobile() {
		return mobile;
	}
	public Client setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}
	
	public String getAddress() {
		return address;
	}
	public Client setAddress(String address) {
		this.address = address;
		return this;
	}
}
