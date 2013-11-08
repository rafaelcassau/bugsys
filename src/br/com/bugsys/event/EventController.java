package br.com.bugsys.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.eventStatus.Status;
import br.com.bugsys.eventType.EventType;
import br.com.bugsys.interceptors.Functionality;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.project.Project;
import br.com.bugsys.project.ProjectDAO;
import br.com.bugsys.step.Step;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.user.User;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.FunctionalityEnum;
import br.com.bugsys.util.Messages;
import br.com.bugsys.workflow.Workflow;
import br.com.bugsys.workflow.WorkflowDAO;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class EventController {

	private WorkflowDAO workflowDAO;
	private ProjectDAO projectDAO;
	private EventDAO eventDAO;
	private UserSession userSession;
	private Result result;
	
	public EventController(WorkflowDAO workflowDAO, ProjectDAO projectDAO, EventDAO eventDAO, UserSession userSession, Result result) {
		this.eventDAO = eventDAO;
		this.workflowDAO = workflowDAO;
		this.projectDAO = projectDAO;
		this.userSession = userSession;
		this.result = result;
	}
	
	@Get
	public List<Event> list() {
		
		this.userSession.setFunctionality(new Functionality(FunctionalityEnum.EVENT.getFunctionality()));
		
		List<Event> listEvents = this.eventDAO.findEventByUserID(this.userSession.getUser().getId());
				
		return listEvents;
	}
	
	@Get
	public void event() {}
	
	@Get
	public void populateProject() {
		
		List<Project> listProjects = new ArrayList<Project>();

		if (this.userSession.getUser().getClient() != null) {
			
			Integer clientID = this.userSession.getUser().getClient().getId();
			listProjects = this.projectDAO.findProjectsByClientID(clientID);
			
		} else {
			
			Integer userID = this.userSession.getUser().getId();
			listProjects = this.projectDAO.findProjectsByUserID(userID);
		}
		
		this.result.use(Results.json()).withoutRoot().from(listProjects).serialize();
	}
	
	@Get
	public void getOptionsByProject(Integer projectID) {
		
		Map<String, Object> contextValues = new HashMap<String, Object>();
		
		List<User> listUsersProject = this.projectDAO.findUsersByProjectID(projectID);
		
		// Popula o objeto transient para a exibição do tipo de usuário
		for (User user : listUsersProject) {
			user.setEmployeeTypeString(user.getEmployeeType().getEmployeeType());
		}
		
		Project project = this.projectDAO.findProjectByID(projectID);
		Workflow workflow = project.getWorkflow();
		List<Step> listSteps = this.workflowDAO.findStepsByWorkflow(workflow.getId()); 
		List<UseCase> listUseCases = this.projectDAO.findUseCasesByProjectID(projectID);
		List<Status> listStatus = this.eventDAO.findAllStatus();
		List<EventType> listEventType = this.eventDAO.findAllEventType();
		
		contextValues.put("listUsersProject", listUsersProject);
		contextValues.put("workflow", workflow);
		contextValues.put("listSteps", listSteps);
		contextValues.put("listUseCases", listUseCases);
		contextValues.put("listStatus", listStatus);
		contextValues.put("listEventType", listEventType);
		
		this.result.use(Results.json()).withoutRoot().from(contextValues).serialize();
	}
	
	@Post
	public void event(
			String id, 
			String eventType,
			String project, 
			String userResponsible, 
			String workflowTitle, 
			String step,
			String useCase,
			String currentStatus) {
		
		Integer eventID;

		if (id.trim().isEmpty()) {
			eventID = null;
		} else {
			eventID = Integer.valueOf(id);
		}
		
		Integer eventTypeID = Integer.valueOf(eventType);
		Integer projectID = Integer.valueOf(project);
		Integer userResponsibleID = Integer.valueOf(userResponsible);
		Workflow workflow = this.workflowDAO.findWorkflowByTitle(workflowTitle);
		Integer stepID = Integer.valueOf(step);
		Integer useCaseID = Integer.valueOf(useCase);
		Integer currentStatusID = Integer.valueOf(currentStatus);
		
		
		Event event  = this.populateEvent(
				eventID,
				eventTypeID,
				projectID, 
				userResponsibleID, 
				workflow, 
				stepID, 
				useCaseID, 
				currentStatusID);
		
		this.persistEvent(event);
		
	}
	
	private Event populateEvent(
			Integer eventID, 
			Integer eventTypeID,
			Integer projectID,
			Integer userResponsibleID,
			Workflow workflow,
			Integer stepID,
			Integer useCaseID,
			Integer currentStatusID) {
		
		Project project = this.projectDAO.findProjectByID(projectID);
		EventType eventType = this.eventDAO.findEventTypeByID(eventTypeID);
		User userResponsible = this.projectDAO.findUserByProjectIDUserID(projectID, userResponsibleID);
		Step step = this.workflowDAO.findStepByID(stepID);
		UseCase useCase = this.projectDAO.findUseCaseByID(useCaseID);
		Status currentStatus = this.eventDAO.findStatusByID(currentStatusID);
		User userCreator = this.userSession.getUser();
		
		Event event = new Event()
			.setId(eventID)
			.setProject(project)
			.setUserResponsible(userResponsible)
			.setCurrentStatus(currentStatus)
			.setEventType(eventType)
			.setWorkflow(workflow)
			.setStepWorkflow(step)
			.setUseCase(useCase)
			.setUserCreator(userCreator)
			.setCreationDate(new Date());
		
		return event;
	}
	
	private void persistEvent(Event event) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		this.eventDAO.persistEvent(event);
		
		message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
}
