package br.com.bugsys.step;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Entity;

import br.com.bugsys.workflow.Workflow;

@Entity
public class Step {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_workflow")
	private Workflow workflow;

	public Integer getId() {
		return id;
	}

	public Step setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Step setDescription(String description) {
		this.description = description;
		return this;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public Step setWorkflow(Workflow workflow) {
		this.workflow = workflow;
		return this;
	}
}
