package br.com.bugsys.project;

import java.util.List;

import br.com.bugsys.client.Client;
import br.com.bugsys.client.ClientDAO;
import br.com.bugsys.workflow.Workflow;
import br.com.bugsys.workflow.WorkflowDAO;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class ProjectController {

	private ProjectDAO projectDAO;
	private WorkflowDAO workflowDAO;
	private ClientDAO clientDAO;
	private Result result;
	
	public ProjectController(ProjectDAO projectDAO, WorkflowDAO workflowDAO, ClientDAO clientDAO, Result result) {
		this.projectDAO = projectDAO;
		this.workflowDAO = workflowDAO;
		this.clientDAO = clientDAO;
		this.result = result;
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
	public void project(String id) {
		
	}
	
	@Get("/project/{id}")
	public Project project(Integer id) {
		
		Project project = this.projectDAO.findProjectById(id); 
		
		return project;
	}
}
