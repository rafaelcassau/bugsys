package br.com.bugsys.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.eventStatus.Status;
import br.com.bugsys.interceptors.Functionality;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.project.Project;
import br.com.bugsys.project.ProjectDAO;
import br.com.bugsys.step.Step;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.user.User;
import br.com.bugsys.util.FunctionalityEnum;
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
		
		contextValues.put("listUsersProject", listUsersProject);
		contextValues.put("workflow", workflow);
		contextValues.put("listSteps", listSteps);
		contextValues.put("listUseCases", listUseCases);
		contextValues.put("listStatus", listStatus);
		
		this.result.use(Results.json()).withoutRoot().from(contextValues).serialize();
	}
	
	@Post
	public void event(String id) {
		
	}
	
	@Get
	public void delegateEvent() {
		
	}
	
	@Get
	public void closeEvent() {
		
	}
	
	@Get
	public void reOpenEvent() {
		
	}
}
