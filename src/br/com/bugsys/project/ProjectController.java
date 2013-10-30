package br.com.bugsys.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.bugsys.client.Client;
import br.com.bugsys.client.ClientDAO;
import br.com.bugsys.interceptors.Functionality;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.user.User;
import br.com.bugsys.user.UserDAO;
import br.com.bugsys.userproject.UserProject;
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
public class ProjectController {

	private ProjectDAO projectDAO;
	private WorkflowDAO workflowDAO;
	private ClientDAO clientDAO;
	private UserDAO userDAO;
	private UserSession userSession;
	private Result result;
	
	/***
	 * Lógica de negocios referente ao caso de uso de projetos
	 */
	
	public ProjectController(ProjectDAO projectDAO, WorkflowDAO workflowDAO, ClientDAO clientDAO, UserDAO userDAO, UserSession userSession, Result result) {
		this.projectDAO = projectDAO;
		this.workflowDAO = workflowDAO;
		this.clientDAO = clientDAO;
		this.userDAO = userDAO;
		this.userSession = userSession;
		this.result = result;
	}
	
	@Get
	public List<Project> list() throws ParseException {
		
		this.userSession.setFunctionality(new Functionality(FunctionalityEnum.PROJECTS.getFunctionality()));

		List<Project> listProjects = this.projectDAO.findAllProjects();
		
		for (Project project : listProjects) {
			
			if (project.getStartDate() != null && project.getEstimatedEndDate() != null) {

				String startDateString = new SimpleDateFormat("dd/MM/yyyy").format(project.getStartDate());
				project.setStartDateString(startDateString);
				
				String estimatedEndDateString = new SimpleDateFormat("dd/MM/yyyy").format(project.getEstimatedEndDate());
				project.setEstimatedEndDateString(estimatedEndDateString);
			}
		}
		
		return listProjects;
	}
	
	@Get
	public void project() {
		
		List<Workflow> workflowList = this.workflowDAO.findAllWorkflows();
		List<Client> clientList = this.clientDAO.findAllClients();
		
		this.result.include("workflowList", workflowList);
		this.result.include("clientList", clientList);
	}
	
	@Post
	public void project(
			String id, 
			String projectName, 
			String startDate,
			String estimatedEndDate,
			String client,
			String workflow,
			String description,
			String membersProject,
			String useCases) {
		
		Project project = this.populateProject(id, projectName, startDate, estimatedEndDate, client, workflow, description);
		
		if (project.getId() == null) {
			
			this.saveProject(project, membersProject, useCases);
			
		} else {
			
			this.updateProject(project, membersProject, useCases);
		}
		
	}
	
	@Get("/project/{id}")
	public Project project(Integer id) {
		
		Project project = this.projectDAO.findProjectById(id); 
		
		return project;
	}
	
	@Post
	public void delete(Integer id) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		this.projectDAO.deleteProjectById(id);
		
		message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	@Get
	public void getMembersPopulateAutoCompleteJSON(String membersProject) {
	
		List<User> listUsers = this.userDAO.findUsersByIds(membersProject);
		
		this.result.use(Results.json()).withoutRoot().from(listUsers).include("employeeType").serialize();
	}
	
	private Map<String, String> projectAddIsValid(Project project) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		Project projectByName = this.projectDAO.findProjectByName(project.getProjectName());
		
		if (projectByName != null) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_PROJECT_NAME);
		}
		
		return message;
	}
	
	private Map<String, String> projectUpdateIsValid(Project project) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		Project projectByName = this.projectDAO.findProjectByName(project.getProjectName());
		
		if (projectByName != null && !projectByName.getId().equals(projectByName.getId())) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_PROJECT_NAME);
		}
		
		return message;
	}
	
	@Get
	public void findUserByNameJSON(String name) {
		
	   List<User> users = this.userDAO.findUsersLikeName(name);
	   
	   this.result.use(Results.json()).withoutRoot().from(users).include("employeeType").serialize();
	}
	
	private Project populateProject(
			String id, 
			String projectName, 
			String startDate,
			String estimatedEndDate,
			String client,
			String workflow,
			String description) {
		
		Integer projectID = null;
		
		if (id != null && !id.trim().isEmpty()) {
			projectID = Integer.valueOf(id);
		}

		Integer clientID = Integer.valueOf(client);
		Integer workflowID = Integer.valueOf(workflow);
		
		Client entityClient = this.clientDAO.findClientById(clientID);
		Workflow entityWorkflow = this.workflowDAO.findWorkflowById(workflowID);
		
		Date dStartDate = Utils.convertDateStringToDate(startDate);
		Date dEstimatedEndDate = Utils.convertDateStringToDate(estimatedEndDate);
		
		Project project = new Project()
			.setId(projectID)
			.setStartDate(dStartDate)
			.setEstimatedEndDate(dEstimatedEndDate)
			.setProjectName(projectName)
			.setDescription(description)
			.setClient(entityClient)
			.setWorkflow(entityWorkflow);
		
		return project;
	}
	
	private void saveProject(Project project, String membersProject, String useCases) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = projectAddIsValid(project);
		
		if (message.isEmpty()) {
			
			project = this.projectDAO.persistProject(project);
			
			List<User> listUsersAddInProject = this.userDAO.findUsersByIds(membersProject);
			
			List<UserProject> listUserProject = new ArrayList<UserProject>();
			
			for (User user : listUsersAddInProject) {
				
				UserProject userProject = new UserProject()
					.setProject(project)
					.setUser(user)
					.setStartDate(new Date());
				
				listUserProject.add(userProject);
			}
			
			this.projectDAO.persistListUserProject(listUserProject);

			try {
				
				JSONArray listUseCasesJSON = new JSONArray("[" + useCases + "]");
				
				List<UseCase> listUseCasesProject = new ArrayList<UseCase>();
				
				for (int i = 0; i < listUseCasesJSON.length(); i++) {
					
					JSONObject jsonObject = listUseCasesJSON.getJSONObject(i);
					
					UseCase useCase = new UseCase()
						.setCode(jsonObject.getString("code"))
						.setName(jsonObject.getString("name"))
						.setDescription(jsonObject.getString("description"))
						.setProject(project);
					
					listUseCasesProject.add(useCase);
				}
				
				this.projectDAO.persistListUseCasesProject(listUseCasesProject);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
				
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private void updateProject(Project project, String membersProject, String useCases) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = projectUpdateIsValid(project);
		
		if (message.isEmpty()) {
			
			project = this.projectDAO.persistProject(project);
			
			List<User> listUsersAddInProject = this.userDAO.findUsersByIds(membersProject);

			this.projectDAO.deleteUsersProjectByProjectID(project.getId());
			
			List<UserProject> listUserProject = new ArrayList<UserProject>();
			
			for (User user : listUsersAddInProject) {
				
				UserProject userProject = new UserProject()
				.setProject(project)
				.setUser(user)
				.setStartDate(new Date());
				
				listUserProject.add(userProject);
				
			}
			
			this.projectDAO.persistListUserProject(listUserProject);
				
			this.projectDAO.deleteUserCasesByProjectID(project.getId());
				
//				for (UseCase useCase : listUseCases) {
//					this.useCaseDAO.persistUseCase(useCase);
//				}
			
			
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_UPDATE_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
}
