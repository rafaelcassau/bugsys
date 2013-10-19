package br.com.bugsys.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.client.Client;
import br.com.bugsys.client.ClientDAO;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.usecase.UseCaseDAO;
import br.com.bugsys.user.User;
import br.com.bugsys.user.UserDAO;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.Messages;
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
	private UseCaseDAO useCaseDAO;
	private UserDAO userDAO;
	private Result result;
	
	/***
	 * Lógica de negocios referente ao caso de uso de projetos
	 */
	
	public ProjectController(ProjectDAO projectDAO, WorkflowDAO workflowDAO, ClientDAO clientDAO, UseCaseDAO useCaseDAO, UserDAO userDAO, Result result) {
		this.projectDAO = projectDAO;
		this.workflowDAO = workflowDAO;
		this.clientDAO = clientDAO;
		this.useCaseDAO = useCaseDAO;
		this.userDAO = userDAO;
		this.result = result;
		
		List<User> listUsers = this.userDAO.findUsersByIds("34,35,37,38,40");
	}
	
	@Get
	public List<Project> list() {
		
		List<Project> listProjects = this.projectDAO.findAllProjects();
		
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
	public void project(String membersProject) {
		
		List<User> listUsers = this.userDAO.findUsersByIds(membersProject);
	}
	
	@Get("/project/{id}")
	public Project project(Integer id) {
		
		Project project = this.projectDAO.findProjectById(id); 
		
//		List<UseCase> useCaseList = this.useCaseDAO.findUseCasesByProject(id);
		
		return project;
	}
	
	@Get
	public void findUserByNameJSON(String name) {
		
	   List<User> users = this.userDAO.findUsersLikeName(name);
	   
	   this.result.use(Results.json()).withoutRoot().from(users).include("employeeType").serialize();
	}
	
	@Get
	public void getMembersPopulateAutoCompleteJSON(String membersProject) {
	
		List<User> listUsers = this.userDAO.findUsersByIds(membersProject);
		
		this.result.use(Results.json()).withoutRoot().from(listUsers).serialize();
	}
	
	/***
	 * Lógica de negocios referente a persistencia dos casos de uso referente aos projetos
	 */
	
	@Get("/list/{id}")
	public List<UseCase> list(Integer projectID) {
		
		List<UseCase> listUseCases = this.useCaseDAO.findUseCasesByProject(projectID);
		
		return listUseCases;
	}
	
	@Get("/usecase")
	public void usecase() {}
	
	@Get("/usecase/{id}")
	public UseCase usecase(Integer id) {
		
		UseCase useCase = this.useCaseDAO.findUseCaseById(id);
		
		return useCase;
	}
	
	@Post
	public void usecase(String id, String code, String name, String descriptionUseCase, String projectID) {
		
		Integer idProject = Integer.valueOf(projectID);
		
		Project project = this.projectDAO.findProjectById(idProject);
		
		if (id == null || id.trim().isEmpty()) {
			
			UseCase useCase = new UseCase().setCode(code)
										   .setName(name)
										   .setDescription(descriptionUseCase)
										   .setProject(project);
			this.addUseCase(useCase);
			
		} else {
			
			Integer useCaseID = Integer.valueOf(id);
			
			UseCase useCase = new UseCase().setId(useCaseID)
										   .setCode(code)
										   .setName(name)
										   .setDescription(descriptionUseCase)
										   .setProject(project);

			this.updateUseCase(useCase);
		}
	}
	
	@Post
	public void delete(Integer id) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		this.useCaseDAO.deleteUseCaseById(id);
		
		message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private void addUseCase(UseCase useCase) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = addUseCaseIsValid(useCase); 
		
		if(message.isEmpty()) {

			this.useCaseDAO.persistUseCase(useCase);
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private void updateUseCase(UseCase useCase) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = updateUseCaseIsValid(useCase); 
		
		if(message.isEmpty()) {

			this.useCaseDAO.persistUseCase(useCase);
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_UPDATE_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private Map<String, String> addUseCaseIsValid(UseCase useCase) {
		
		Map<String, String> message = new HashMap<String, String>();

		UseCase useCaseByCode = this.useCaseDAO.findUseCaseByCode(useCase.getCode(), useCase.getProject().getId());
		
		if (useCaseByCode != null) {

			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_USECASE_CODE);
			return message;
		}
		
		UseCase useCaseByName = this.useCaseDAO.findUseCaseByName(useCase.getName(), useCase.getProject().getId());

		if (useCaseByName != null) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_USECASE_NAME);
			return message;
		}
		
		return message;
	}
	
	private Map<String, String> updateUseCaseIsValid(UseCase useCase) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		UseCase useCaseByCode = this.useCaseDAO.findUseCaseByCode(useCase.getCode(), useCase.getProject().getId());
		
		if (useCaseByCode != null && !useCase.getId().equals(useCaseByCode.getId())) {

			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_USECASE_CODE);
			return message;
		}
		
		UseCase useCaseByName = this.useCaseDAO.findUseCaseByName(useCase.getName(), useCase.getProject().getId());

		if (useCaseByName != null && !useCase.getId().equals(useCaseByName.getId())) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_USECASE_NAME);
			return message;
		}
		
		return message;
	}
}
