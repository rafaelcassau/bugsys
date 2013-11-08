package br.com.bugsys.event;

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

import br.com.bugsys.descriptionEvent.DescriptionEvent;
import br.com.bugsys.eventStatus.Status;
import br.com.bugsys.eventType.EventType;
import br.com.bugsys.project.Project;
import br.com.bugsys.step.Step;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.user.User;
import br.com.bugsys.workflow.Workflow;

@Entity
public class Event {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_creator_FK")
	private User userCreator;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_responsible_FK")
	private User userResponsible;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_FK")
	private Project project;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usecase_FK")
	private UseCase useCase;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "workflow_FK")
	private Workflow workflow;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "step_workflow_FK")
	private Step stepWorkflow;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_FK")
	private Status currentStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_type_FK")
	private EventType eventType;
	
	@OneToMany(mappedBy="event", fetch = FetchType.EAGER)
	private List<DescriptionEvent> descriptionEventList;
	
	private Date creationDate;
	
	private Date finallyDate;

	
	public Integer getId() {
		return id;
	}
	public Event setId(Integer id) {
		this.id = id;
		return this;
	}

	public User getUserCreator() {
		if (userCreator == null) {
			userCreator = new User();
		}
		return userCreator;
	}
	public Event setUserCreator(User userCreator) {
		this.userCreator = userCreator;
		return this;
	}

	public User getUserResponsible() {
		if (userResponsible == null) {
			userResponsible = new User();
		}
		return userResponsible;
	}
	public Event setUserResponsible(User userResponsible) {
		this.userResponsible = userResponsible;
		return this;
	}

	public Project getProject() {
		if (project == null) {
			project = new Project();
		}
		return project;
	}
	public Event setProject(Project project) {
		this.project = project;
		return this;
	}

	public UseCase getUseCase() {
		if (useCase == null) {
			useCase = new UseCase();
		}
		return useCase;
	}
	public Event setUseCase(UseCase useCase) {
		this.useCase = useCase;
		return this;
	}

	public Workflow getWorkflow() {
		if (workflow == null) {
			workflow = new Workflow();
		}
		return workflow;
	}
	public Event setWorkflow(Workflow workflow) {
		this.workflow = workflow;
		return this;
	}

	public Step getStepWorkflow() {
		if (stepWorkflow == null) {
			stepWorkflow = new Step();
		}
		return stepWorkflow;
	}

	public Event setStepWorkflow(Step stepWorkflow) {
		this.stepWorkflow = stepWorkflow;
		return this;
	}

	public Status getCurrentStatus() {
		if (currentStatus == null) {
			currentStatus = new Status();
		}
		return currentStatus;
	}
	public Event setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
		return this;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	public Event setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public Date getFinallyDate() {
		return finallyDate;
	}
	public Event setFinallyDate(Date finallyDate) {
		this.finallyDate = finallyDate;
		return this;
	}

	public List<DescriptionEvent> getDescriptionEventList() {
		if (descriptionEventList == null) {
			descriptionEventList = new ArrayList<DescriptionEvent>();
		}
		return descriptionEventList;
	}
	public Event setDescriptionEventList(List<DescriptionEvent> descriptionEventList) {
		this.descriptionEventList = descriptionEventList;
		return this;
	}

	public EventType getEventType() {
		if (eventType == null) {
			eventType = new EventType();
		}
		return eventType;
	}
	public Event setEventType(EventType eventType) {
		this.eventType = eventType;
		return this;
	}
}
