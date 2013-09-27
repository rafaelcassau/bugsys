package br.com.bugsys.userproject;

import java.io.Serializable;

public class UserProjectId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer user;
	private Integer project;
	
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
}
