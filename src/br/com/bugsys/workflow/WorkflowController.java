package br.com.bugsys.workflow;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;


@Resource
public class WorkflowController {
	
	private Result result;
	
	public WorkflowController(Result result) {
		this.result = result;
	}

	@Get
	public List<Workflow> list() {
		List<Workflow> listWorkflow = new ArrayList<Workflow>();
		return listWorkflow;
	}
	
	@Get("/workflow")
	public void workflow() {
		
	}
}
