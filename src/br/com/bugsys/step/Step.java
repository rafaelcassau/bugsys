package br.com.bugsys.step;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import br.com.bugsys.workflow.Workflow;

@Entity
public class Step {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_FK")
	private Workflow workflow;
	
	public Integer getId() {
		return id;
	}
	public Step setId(Integer id) {
		this.id = id;
		return this;
	}

	public Step setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getTitle() {
		return title;
	}

	public Workflow getWorkflow() {
		return workflow;
	}
	public Step setWorkflow(Workflow workflow) {
		this.workflow = workflow;
		return this;
	}
}
