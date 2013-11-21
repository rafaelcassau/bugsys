package br.com.bugsys.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.descriptionEvent.DescriptionEvent;
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
import br.com.bugsys.util.Utils;
import br.com.bugsys.workflow.Workflow;
import br.com.bugsys.workflow.WorkflowDAO;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class EventController {

	// constantes que representam os status da ocorrencia
	
	private static final int ABERTO 		= 1;
	private static final int FECHADO 		= 2;
	private static final int ACEITO 		= 3;
	private static final int REJEITADO 		= 4;
	private static final int CORRIGIDO 		= 5; 
	private static final int VALIDADO 		= 6;
	private static final int NAO_VALIDADO 	= 7;
	private static final int HOMOLOGADO 	= 8;
	private static final int SUSPENSO 		= 9;
	
	private static final int FALHA	 				= 3;
	private static final int SUGESTAO 				= 5;
	private static final int SOLICITACAO_MUDANCA 	= 6;
	
	private static final int GERENTE_PROJETOS 		= 9;

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
	public void populateComboProjectAdd() {
		
		List<Project> listProjects = findProjectsByUserOrClientID();
		
		this.result.use(Results.json()).withoutRoot().from(listProjects).serialize();
	}

	@Get
	public void populateComboProjectEdit(Integer id) {
		
		Project project = this.eventDAO.findEventByID(Integer.valueOf(id)).getProject();

		List<Project> listProjects = new ArrayList<Project>();
		listProjects.add(project);
		
		this.result.use(Results.json()).withoutRoot().from(listProjects).serialize();
	}

	@Get
	public void getOptionsByProject(Integer projectID) {
		
		Map<String, Object> contextValues = new HashMap<String, Object>();
		
		List<User> listUsersProject = new ArrayList<User>(); 
		List<EventType> listEventType = new ArrayList<EventType>();
		
		if (this.userSession.getUser().getClient() == null) {
			
			listEventType = this.eventDAO.findAllEventType();
			
			listUsersProject = this.projectDAO.findUsersByProjectID(projectID);
			
		} else {
			listEventType = this.eventDAO.findListEventTypeByID(FALHA, SUGESTAO, SOLICITACAO_MUDANCA);
			
			User userProject = this.projectDAO.findUserByProjectAndByEmployeeTypeID(projectID, GERENTE_PROJETOS);
		
			listUsersProject.add(userProject);
		}

		// Popula o objeto transient para a exibição do tipo de usuário
		for (User user : listUsersProject) {
			user.setEmployeeTypeString(user.getEmployeeType().getEmployeeType());
		}
		
		Project project = this.projectDAO.findProjectByID(projectID);
		Workflow workflow = project.getWorkflow();
		List<Step> listSteps = this.workflowDAO.findStepsByWorkflow(workflow.getId()); 
		List<UseCase> listUseCases = this.projectDAO.findUseCasesByProjectID(projectID);
		List<Status> listStatus = this.findListStatusAdd();
		
		
		contextValues.put("listUsersProject", listUsersProject);
		contextValues.put("workflow", workflow);
		contextValues.put("listSteps", listSteps);
		contextValues.put("listUseCases", listUseCases);
		contextValues.put("listStatus", listStatus);
		contextValues.put("listEventType", listEventType);
		
		this.result.use(Results.json()).withoutRoot().from(contextValues).serialize();
	}
	
	@Get("/event/{id}")
	public Event event(Integer id) {
		
		Event event = this.eventDAO.findEventByID(id);
		
		Project project = this.projectDAO.findProjectByID(event.getProject().getId());

		Workflow workflow = project.getWorkflow();
		
		List<Project> projectList = new ArrayList<Project>();
		List<Step> stepList = new ArrayList<Step>();
		List<UseCase> useCaseList = new ArrayList<UseCase>();
		List<EventType> eventTypeList = new ArrayList<EventType>();
		List<Status> currentStatusList = this.findListStatusUpdate(event.getCurrentStatus());
		List<User> userResponsibleList = new ArrayList<User>();
		
		if (this.userSession.getUser().getClient() != null) {
			
			projectList.add(project);
			
			stepList.add(event.getStepWorkflow());
			
			useCaseList.add(event.getUseCase());
			
			eventTypeList.add(event.getEventType());
			
			User userProject = this.projectDAO.findUserByProjectAndByEmployeeTypeID(project.getId(), GERENTE_PROJETOS);
			
			userResponsibleList.add(userProject);
			
		} else if (currentStatusList.size() > 1) {

			Integer currentUserID = this.userSession.getUser().getId();
			
			projectList = this.projectDAO.findProjectsByUserID(currentUserID);
			
			stepList = this.workflowDAO.findStepsByWorkflow(workflow.getId());
			
			useCaseList = this.projectDAO.findUseCasesByProjectID(project.getId());
			
			eventTypeList = this.eventDAO.findAllEventType();
			
			userResponsibleList = this.projectDAO.findUsersByProjectID(project.getId());
			
		} else {

			projectList.add(project);
			
			stepList.add(event.getStepWorkflow());
			
			useCaseList.add(event.getUseCase());
			
			eventTypeList.add(event.getEventType());
			
			userResponsibleList.add(event.getUserResponsible());
			
		}
		
		// Popula o objeto transient para a exibição do tipo de usuário
		for (User user : userResponsibleList) {
			user.setEmployeeTypeString(user.getEmployeeType().getEmployeeType());
		}
		
		this.result.include("projectList", projectList);
		this.result.include("workflow", workflow);
		this.result.include("eventTypeList", eventTypeList);
		this.result.include("userResponsibleList", userResponsibleList);
		this.result.include("stepList", stepList);
		this.result.include("useCaseList", useCaseList);
		this.result.include("currentStatusList", currentStatusList);
		
		return event;
	}
	
	private List<Status> findListStatusAdd() {
		
		List<Status> statusList = this.eventDAO.findAllStatus();
		List<Status> listAux = new ArrayList<Status>();
		
		for (Status status : statusList) {
			
			if (status.getStatus().equals("Aberto")) {
				listAux.add(status);
			}
		}
		
		return listAux;
	}

	private List<Status> findListStatusUpdate(Status currentStatus) {
		
		List<Status> statusList = new ArrayList<Status>();
		
		if (currentStatus.getStatus().equals("Fechado") || currentStatus.getStatus().equals("Suspenso")) {

			statusList.add(currentStatus);
			
		} else if (currentStatus.getStatus().equals("Homologado")) {
			
			statusList = this.eventDAO.findListStatusByID(HOMOLOGADO, FECHADO, SUSPENSO);
			
		} else if (currentStatus.getStatus().equals("Aberto")){

			statusList = this.eventDAO.findListStatusByID(ABERTO, ACEITO, REJEITADO, SUSPENSO);
			
		} else if (currentStatus.getStatus().equals("Aceito")){
		
			statusList = this.eventDAO.findListStatusByID(ACEITO, CORRIGIDO, SUSPENSO);
		
		}  else if (currentStatus.getStatus().equals("Rejeitado")) {
			
			statusList = this.eventDAO.findListStatusByID(REJEITADO, ACEITO, NAO_VALIDADO, SUSPENSO);
			
		} else if (currentStatus.getStatus().equals("Validado")) {
			
			statusList = this.eventDAO.findListStatusByID(VALIDADO, HOMOLOGADO, SUSPENSO);
			
		} else if (currentStatus.getStatus().equals("Não Validado")) {
			
			statusList = this.eventDAO.findListStatusByID(NAO_VALIDADO, ACEITO, REJEITADO, SUSPENSO);
			
		} else if (currentStatus.getStatus().equals("Corrigido")) {
			
			statusList = this.eventDAO.findListStatusByID(CORRIGIDO, NAO_VALIDADO, VALIDADO, SUSPENSO);
			
		}
		
		return statusList;
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
			String currentStatus,
			String description) {
		
		Map<String, String> message = new HashMap<String, String>();
		
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
		
		event = this.persistEvent(event);
		
		DescriptionEvent descriptionEvent = new DescriptionEvent()
			.setCreationDate(Utils.convertDateToString(new Date()))
			.setDescription(description)
			.setEvent(event)
			.setUserDescription(this.userSession.getUser());
		
		this.eventDAO.persistDescriptionEvent(descriptionEvent);
		
		message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
		
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
		
		User userCreator = null;
		
		if (eventID == null) {
			userCreator = this.userSession.getUser();
		} else {
			userCreator = this.eventDAO.findEventByID(eventID).getUserCreator();
		}
		
		
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
	
	private Event persistEvent(Event event) {

		Event eventReturn = this.eventDAO.persistEvent(event);
		
		return eventReturn;
	}
	
	private List<Project> findProjectsByUserOrClientID() {
		List<Project> listProjects = new ArrayList<Project>();

		if (this.userSession.getUser().getClient() != null) {
			
			Integer clientID = this.userSession.getUser().getClient().getId();
			listProjects = this.projectDAO.findProjectsByClientID(clientID);
			
		} else {
			
			Integer userID = this.userSession.getUser().getId();
			listProjects = this.projectDAO.findProjectsByUserID(userID);
		}
		return listProjects;
	}
}
