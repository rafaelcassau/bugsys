package br.com.bugsys.userproject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.bugsys.project.Project;
import br.com.bugsys.user.User;

@Entity
@IdClass(UserProjectId.class)
public class UserProject {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk")
	private User user;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_fk")
	private Project project;
	
	private Date startDate;
	private Date endDate;
	
	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Project getProject() {
		if (project == null) {
			project = new Project();
		}
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
