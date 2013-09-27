package br.com.bugsys.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.project.Project;
import br.com.bugsys.project.ProjectDAO;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.Messages;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class UsecaseController {

	private UseCaseDAO useCaseDAO;
	private ProjectDAO projectDAO;
	private Result result;
	
	public UsecaseController(UseCaseDAO useCaseDAO, ProjectDAO projectDAO, Result result) {
		this.useCaseDAO = useCaseDAO;
		this.projectDAO = projectDAO;
		this.result = result;
	}
	
	/*@Get("/list/{id}")
	public List<UseCase> list(Integer projectID) {
		
		List<UseCase> listUseCases = this.useCaseDAO.findUseCasesByProject(projectID);
		
		return listUseCases;
	}*/
	
	@Get
	public List<UseCase> list() {
		
		List<UseCase> listUseCases = this.useCaseDAO.findUseCasesByProject(1);
		
		return listUseCases;
	}
	
	@Get("/usecase")
	public void usecase() {}
	
	@Post
	public void usecase(String id, String code, String name, String description, String projectID) {
		
		Integer idProject = Integer.valueOf(projectID);
		
		Project project = this.projectDAO.findProjectById(idProject);
		
		if (id == null || id.trim().isEmpty()) {
			
			UseCase useCase = new UseCase().setCode(code)
										   .setName(name)
										   .setDescription(description)
										   .setProject(project);
			this.addUseCase(useCase);
			
		} else {
			
			Integer useCaseID = Integer.valueOf(id);
			
			UseCase useCase = new UseCase().setId(useCaseID)
										   .setCode(code)
										   .setName(name)
										   .setDescription(description)
										   .setProject(project);

			this.updateUseCase(useCase);
		}
	}
	
	@Get("/usecase/{id}")
	public UseCase usecase(Integer id) {
		
		UseCase useCase = this.useCaseDAO.findUseCaseById(id);
		
		return useCase;
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
