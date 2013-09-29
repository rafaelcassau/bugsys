package br.com.bugsys.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.bugsys.client.Client;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.userproject.UserProject;
import br.com.bugsys.workflow.Workflow;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String projectName;
	
	private Date startDate;
	
	private Date estimatedEndDate;
	
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_FK")
	private Client client;
	
	@OneToMany(mappedBy = "project")
	private List<UserProject> listUserProject;
	
	@OneToMany(mappedBy = "project")
	private List<UseCase> listUseCases;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_FK")
	private Workflow workflow;
	
	private String description;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEstimatedEndDate() {
		return estimatedEndDate;
	}
	public void setEstimatedEndDate(Date estimatedEndDate) {
		this.estimatedEndDate = estimatedEndDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Client getClient() {
		if (client == null) {
			client = new Client();
		}
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<UserProject> getListUserProject() {
		if (listUserProject == null) {
			listUserProject = new ArrayList<UserProject>();
		}
		return listUserProject;
	}
	public void setListUserProject(List<UserProject> listUserProject) {
		this.listUserProject = listUserProject;
	}
	
	public List<UseCase> getListUseCases() {
		if (listUseCases == null) {
			listUseCases = new ArrayList<UseCase>();
		}
		return listUseCases;
	}
	public void setListUseCases(List<UseCase> listUseCases) {
		this.listUseCases = listUseCases;
	}
	
	public Workflow getWorkflow() {
		if (workflow == null) {
			workflow = new Workflow();
		}
		return workflow;
	}
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
