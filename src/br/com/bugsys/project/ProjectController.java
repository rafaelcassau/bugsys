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
import br.com.bugsys.event.EventDAO;
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
	private EventDAO eventDAO;
	private UserSession userSession;
	private Result result;
	
	/***
	 * Lógica de negocios referente ao caso de uso de projetos
	 */
	
	public ProjectController(ProjectDAO projectDAO, WorkflowDAO workflowDAO, ClientDAO clientDAO, UserDAO userDAO, UserSession userSession, EventDAO eventDAO, Result result) {
		this.projectDAO = projectDAO;
		this.workflowDAO = workflowDAO;
		this.clientDAO = clientDAO;
		this.userDAO = userDAO;
		this.eventDAO = eventDAO;
		this.userSession = userSession;
		this.result = result;
	}
	
	@Get
	public List<Project> list() throws ParseException {
		
		this.userSession.setFunctionality(new Functionality(FunctionalityEnum.PROJECTS.getFunctionality()));

		List<Project> listProjects = new ArrayList<Project>();
		
		if (this.userSession.getUser().getClient() != null) {
			
			Integer clientID = this.userSession.getUser().getClient().getId();
			
			listProjects = this.projectDAO.findProjectsByClientID(clientID);
			
		} else {

			Integer userID = this.userSession.getUser().getId();
			
			listProjects = this.projectDAO.findProjectsByUserID(userID);
		}
		
		for (Project project : listProjects) {
			
			populateDateString(project);
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
		
		List<Workflow> workflowList = this.workflowDAO.findAllWorkflows();
		List<Client> clientList = this.clientDAO.findAllClients();
		
		this.result.include("workflowList", workflowList);
		this.result.include("clientList", clientList);
		
		Project project = this.projectDAO.findProjectByID(id); 
		
		populateDateString(project);
		
		return project;
	}
	
	/***
	 * Metodo responsavel por retornar a lista de casos de uso bem como a lista de usuarios relacionados a um projeto
	 * no fluxo de alteração de projeto
	 * 
	 * @param id
	 */
	@Get
	public void populateUseCaseAndUsersProjectUpdateProject(Integer id) {

		Map<String, Object> listData = new HashMap<String, Object>();
		
		List<UseCase> listUseCases = this.projectDAO.findUseCasesByProjectID(id);
		List<User> listUsers = this.projectDAO.findUsersByProjectID(id);
		
		listData.put("listUseCases", listUseCases);
		listData.put("listUsers", listUsers);
	
		this.result.use(Results.json()).withoutRoot().from(listData).serialize();
	}
	
	/***
	 * Metodo executado em background responsavel por popular o storage do browser com os ids dos usuários 
	 * vinculados a um projeto
	 * 
	 * @param membersProject
	 */
	@Get
	public void getMembersPopulateAutoCompleteJSON(String membersProject) {
	
		List<User> listUsers = this.userDAO.findUsersByIds(membersProject);
		
		this.result.use(Results.json()).withoutRoot().from(listUsers).include("employeeType").serialize();
	}
	
	/***
	 * Metodo responsavel por retornar a lista de usuários para popular o autocomplete de vinculação entre
	 * usuarios e projetos
	 *  
	 * @param name
	 */
	@Get
	public void findUserByNameJSON(String name) {
		
	   List<User> users = this.userDAO.findUsersLikeName(name);
	   
	   this.result.use(Results.json()).withoutRoot().from(users).include("employeeType").serialize();
	}
	
	
	@Post
	public void delete(Integer id) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		this.projectDAO.deleteProjectById(id);
		
		message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	
	/***
	 * Metodo responsavel por popular a entidade projeto para posterior persistencia ou alteração
	 * 
	 * @param id
	 * @param projectName
	 * @param startDate
	 * @param estimatedEndDate
	 * @param client
	 * @param workflow
	 * @param description
	 * @return
	 */
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
	
	
	private Map<String, String> projectAddIsValid(Project project) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		Project projectByName = this.projectDAO.findProjectByName(project.getProjectName());
		
		if (projectByName != null) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_PROJECT_NAME);
		}
		
		return message;
	}
	
	
	private Map<String, String> projectUpdateIsValid(Project project, String membersProject, String useCases) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		Project projectByName = this.projectDAO.findProjectByName(project.getProjectName());
		
		if (projectByName != null && !projectByName.getId().equals(projectByName.getId())) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_PROJECT_NAME);
		}
		
		return message;
	}
	
	
	private void saveProject(Project project, String membersProject, String useCases) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = projectAddIsValid(project);
		
		if (message.isEmpty()) {
			
			// salva o projeto
			project = this.projectDAO.persistProject(project);
			
			// vincula os membros ao projeto
			this.addMemberInProject(project, membersProject);

			// vincula casos de uso ao projeto
			List<UseCase> listUseCases = this.convertJsonToUseCaseList(project, useCases);
			this.addUseCasesInProject(listUseCases);
				
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	

	private void updateProject(Project project, String membersProject, String useCases) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = projectUpdateIsValid(project, membersProject, useCases);
		
		if (message.isEmpty()) {
			
			// altera o projeto
			project = this.projectDAO.persistProject(project);
			
			// atualiza lista de membros relacionados ao projeto
			this.updateListMembersInProject(project, membersProject);
			
			// atualiza lista de casos de uso relacionados ao projeto
			List<UseCase> listUseCases = this.convertJsonToUseCaseList(project, useCases);
			this.updateUseCasesInProject(project, listUseCases);
			
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_UPDATE_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}

	/***
	 * Metodo responsavel por vincular os membros ao projeto 
	 * 
	 * @param project
	 * @param membersProject
	 */
	private void addMemberInProject(Project project, String membersProject) {
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
	}
	
	/***
	 * Metodo responsavel por vincular os casos de uso ao projeto
	 * 
	 * @param project
	 * @param useCases
	 */
	private List<UseCase> addUseCasesInProject(List<UseCase> listUseCases) {
		
		List<UseCase> listUseCasesPersisted = new ArrayList<UseCase>();
		
		for (UseCase useCase : listUseCases) {
			
			UseCase useCaseReturn = this.projectDAO.saveUseCasesProject(useCase);
			listUseCasesPersisted.add(useCaseReturn);
		}
		
		return listUseCasesPersisted;
	}
	
	/***
	 * Metodo responsavel por atualizar o vinculo dos casos de uso ao projeto
	 * 
	 * @param project
	 * @param useCases
	 */
	private List<UseCase> updateUseCasesInProject(List<UseCase> listUseCases) {
		
		List<UseCase> listUseCasesUpdate = new ArrayList<UseCase>();
		
		for (UseCase useCase : listUseCases) {
			
			UseCase useCaseReturn = this.projectDAO.updateUseCasesProject(useCase);
			listUseCasesUpdate.add(useCaseReturn);
		}
		
		return listUseCasesUpdate;
	}
	
	/***
	 * Metodo responsavel por atualizar a lista de casos de uso vinculados ao projeto
	 * 
	 * @param project
	 * @param useCases
	 */
	private void updateUseCasesInProject(Project project, List<UseCase> listUseCases) {
		
		List<UseCase> listUseCasePersist = new ArrayList<UseCase>();
		List<UseCase> listUseCaseUpdate = new ArrayList<UseCase>();
		
		for (UseCase useCase : listUseCases) {
			
			if (useCase.getId() == null) {
				listUseCasePersist.add(useCase);
			} else {
				listUseCaseUpdate.add(useCase);
			}
		}
		
		listUseCases.clear();
		
		listUseCases.addAll(this.addUseCasesInProject(listUseCasePersist));
		listUseCases.addAll(this.updateUseCasesInProject(listUseCaseUpdate));
		
		List<UseCase> listAllUseCases = this.projectDAO.findUseCasesByProjectID(project.getId());
		List<UseCase> listUseCaseRemove = new ArrayList<UseCase>();
		
		for (UseCase useCaseVinculed : listAllUseCases) {
			
			boolean isExist = false;
			
			for (UseCase currentUseCase : listUseCases) {
				
				if (currentUseCase.getId().equals(useCaseVinculed.getId())) {
					isExist = true;
					break;
				}
			}
			
			if (!isExist) {
				listUseCaseRemove.add(useCaseVinculed);
			}
		}
		
		for (UseCase useCaseRemove : listUseCaseRemove) {
			
			this.projectDAO.deleteUseCaseByID(useCaseRemove.getId());
		}
	}

	
	/***
	 * Metodo responsavel por converter um JSON em uma lista de objetos Casos de Uso 
	 * 
	 * @param project
	 * @param useCases
	 */
	private List<UseCase> convertJsonToUseCaseList(Project project, String useCases) {
		
		List<UseCase> listUseCasesProject = new ArrayList<UseCase>();

		try {

			JSONArray listUseCasesJSON = new JSONArray("[" + useCases + "]");
			
			for (int i = 0; i < listUseCasesJSON.length(); i++) {
				
				JSONObject jsonObject = listUseCasesJSON.getJSONObject(i);
				
				UseCase useCase = new UseCase();

				if (!jsonObject.isNull("id")) {
					useCase.setId(jsonObject.getInt("id"));
				}
				
				useCase.setCode(jsonObject.getString("code"));
				useCase.setName(jsonObject.getString("name"));
				useCase.setDescription(jsonObject.getString("description"));
				useCase.setProject(project);
				
				listUseCasesProject.add(useCase);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return listUseCasesProject;
	}
	
	/***
	 * Metodo responsavel por atualizar a lista de membros de um projeto
	 * 
	 * @param project
	 * @param membersProject
	 */
	private void updateListMembersInProject(Project project, String membersProject) {
		
		// Adiciona membros a um projeto
		List<User> listUsers = addMembersInUpdateProject(project, membersProject);
		
		// Remove membros de um projeto
		List<User> listUsersProjectVinculed = this.projectDAO.findUsersByProjectID(project.getId());
		this.removeMembersInUpdateProject(project, listUsers, listUsersProjectVinculed);
	}

	
	private List<User> addMembersInUpdateProject(Project project,
			String membersProject) {
		
		List<User> listUsers = this.userDAO.findUsersByIds(membersProject);
		List<UserProject> listUserAddInProject = new ArrayList<UserProject>();
		
		for (User user : listUsers) {
			
			if (this.projectDAO.findUserProjectByProjectIDUserID(project.getId(), user.getId()) == null) {
				
				UserProject userProject = new UserProject()
				.setProject(project)
				.setUser(user)
				.setStartDate(new Date());
				
				listUserAddInProject.add(userProject);
			}
		}
		this.projectDAO.persistListUserProject(listUserAddInProject);
		
		return listUsers;
	}
	
	private void removeMembersInUpdateProject(
				Project project,
				List<User> listUsers, 
				List<User> listUsersProjectVinculed) {
		
		List<User> listUsersRemoveProject = new ArrayList<User>();
		
		for (User userVinculed : listUsersProjectVinculed) {
			
			boolean isExist = false;
			
			for (User user : listUsers) {
				
				if (user.getId().equals(userVinculed.getId())) {
					
					isExist = true;
					break;
				}
			}
			
			if (!isExist) {
				listUsersRemoveProject.add(userVinculed);
			}
		}
		
		for (User user : listUsersRemoveProject) {
			this.projectDAO.deleteUserProjectByProjectIDUserID(project.getId(), user.getId());
		}
	}

	/***
	 * Metodo responsavel por popular os atributos data de exibição para a lista e para o fluxo de 
	 * alteração de projeto
	 * 
	 * @param project
	 */
	private void populateDateString(Project project) {
		if (project.getStartDate() != null && project.getEstimatedEndDate() != null) {

			String startDateString = new SimpleDateFormat("dd/MM/yyyy").format(project.getStartDate());
			project.setStartDateString(startDateString);
			
			String estimatedEndDateString = new SimpleDateFormat("dd/MM/yyyy").format(project.getEstimatedEndDate());
			project.setEstimatedEndDateString(estimatedEndDateString);
		}
	}
}
