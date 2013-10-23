package br.com.bugsys.eventStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Status {

	@Id
	@GeneratedValue
	private Integer id;
	private String status;
	
	public Integer getId() {
		return id;
	}
	public Status setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getStatus() {
		return status;
	}
	public Status setStatus(String status) {
		this.status = status;
		return this;
	}
}
