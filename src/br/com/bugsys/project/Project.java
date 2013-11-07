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
import javax.persistence.Transient;

import br.com.bugsys.client.Client;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.user.User;
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
	
	@Transient
	private String startDateString;
	
	@Transient
	private String estimatedEndDateString;
	
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_FK")
	private Client client;
	
	@OneToMany(mappedBy = "project")
	private List<UserProject> listUserProject;
	
	@OneToMany(mappedBy = "project")
	private List<UseCase> listUseCases;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "workflow_FK")
	private Workflow workflow;
	
	private String description;

	public Integer getId() {
		return id;
	}
	public Project setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public Project setProjectName(String projectName) {
		this.projectName = projectName;
		return this;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public Project setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}
	
	public Date getEstimatedEndDate() {
		return estimatedEndDate;
	}
	public Project setEstimatedEndDate(Date estimatedEndDate) {
		this.estimatedEndDate = estimatedEndDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}
	public Project setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
	
	public Client getClient() {
		if (client == null) {
			client = new Client();
		}
		return client;
	}
	public Project setClient(Client client) {
		this.client = client;
		return this;
	}
	
	public List<UserProject> getListUserProject() {
		if (listUserProject == null) {
			listUserProject = new ArrayList<UserProject>();
		}
		return listUserProject;
	}
	public Project setListUserProject(List<UserProject> listUserProject) {
		this.listUserProject = listUserProject;
		return this;
	}
	
	public List<UseCase> getListUseCases() {
		if (listUseCases == null) {
			listUseCases = new ArrayList<UseCase>();
		}
		return listUseCases;
	}
	public Project setListUseCases(List<UseCase> listUseCases) {
		this.listUseCases = listUseCases;
		return this;
	}
	
	public Workflow getWorkflow() {
		if (workflow == null) {
			workflow = new Workflow();
		}
		return workflow;
	}
	public Project setWorkflow(Workflow workflow) {
		this.workflow = workflow;
		return this;
	}

	public String getDescription() {
		return description;
	}
	public Project setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public String getStartDateString() {
		return startDateString;
	}
	public Project setStartDateString(String startDateString) {
		this.startDateString = startDateString;
		return this;
	}

	public String getEstimatedEndDateString() {
		return estimatedEndDateString;
	}
	public Project setEstimatedEndDateString(String estimatedEndDateString) {
		this.estimatedEndDateString = estimatedEndDateString;
		return this;
	}
}
