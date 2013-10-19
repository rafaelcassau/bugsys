package br.com.bugsys.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.interceptors.Functionality;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.project.Project;
import br.com.bugsys.project.ProjectDAO;
import br.com.bugsys.step.Step;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.FunctionalityEnum;
import br.com.bugsys.util.Messages;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;


@Resource
public class WorkflowController {
	
	private Result result;
	private WorkflowDAO workflowDAO;
	private ProjectDAO projectDAO;
	private UserSession userSession;
	
	public WorkflowController(WorkflowDAO workflowDAO, ProjectDAO projectDAO, UserSession userSession, Result result) {
		this.workflowDAO = workflowDAO;
		this.projectDAO = projectDAO;
		this.userSession = userSession;
		this.result = result;
	}

	@Get
	public List<Workflow> list() {
		
		this.userSession.setFunctionality(new Functionality(FunctionalityEnum.WORKFLOWS.getFunctionality()));
		
		List<Workflow> listWorkflows = this.workflowDAO.findAllWorkflows();
		
		return listWorkflows;
	}
	
	@Get("/workflow")
	public void workflow() {}
	
	@Post
	public void workflow(String id, String title, String description, String steps) {
		
		if (id == null || id.trim().isEmpty()) {
			
			Workflow workflow = new Workflow();
			List<Step> listSteps = convertToListSteps(steps);
			
			workflow.setTitle(title)
					.setDescription(description)
					.setListSteps(listSteps);
			
			this.saveWorkflow(workflow);
		}
	}
	
	private List<Step> convertToListSteps(String steps) {
		
		List<Step> listSteps = new ArrayList<Step>();
		
		String arrayAux[] = steps.split(",");
		
		for (String step : arrayAux) {
			
			listSteps.add(new Step().setTitle(step));
		}
		
		return listSteps;
	}
	
	public void saveWorkflow(Workflow workflow) {
		
		Map<String, String> message = new HashMap<String, String>();

		message = addWorkflowIsValid(workflow);
		
		if (message.isEmpty()) {
			
			this.workflowDAO.persistWorkflow(workflow);
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private Map<String, String> addWorkflowIsValid(Workflow workflow) {
	
		Map<String, String> message = new HashMap<String, String>();
		
		Workflow workflowByName = this.workflowDAO.findWorkflowByTitle(workflow.getTitle());
		
		if (workflowByName != null) {
			
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_WORKFLOW_TITLE);
		}
		
		return message;
	}
	
	@Post
	public void delete(Integer id) {
		
		Map<String, String> message = new HashMap<String, String>();

		if (!this.workflowIsBoundProject(id)) {

			this.workflowDAO.deleteWorkflowById(id);
			
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
			
		} else {
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_WORKFLOW_BOUND_PROJECT);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private boolean workflowIsBoundProject(Integer id) {
		
		List<Project> projects = this.projectDAO.findProjectsByWorkflowID(id);
		
		return projects.size() > 0;
	}
}
