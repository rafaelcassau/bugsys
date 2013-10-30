package br.com.bugsys.userproject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.bugsys.project.Project;
import br.com.bugsys.user.User;

@Entity
public class UserProject {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_fk")
	private Project project;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk")
	private User user;
	
	private Date startDate;
	
	private Date endDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject() {
		if (project == null) {
			project = new Project();
		}
		return project;
	}
	public UserProject setProject(Project project) {
		this.project = project;
		return this;
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}
	public UserProject setUser(User user) {
		this.user = user;
		return this;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public UserProject setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}
	public UserProject setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
}
