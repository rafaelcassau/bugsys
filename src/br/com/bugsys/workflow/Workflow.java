package br.com.bugsys.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.bugsys.step.Step;

@Entity
public class Workflow {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String title;
	private String description;
	
	@OneToMany(mappedBy = "workflow")
	private List<Step> listSteps;
		
	public Integer getId() {
		return id;
	}
	public Workflow setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public String getTitle() {
		return title;
	}
	public Workflow setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getDescription() {
		return description;
	}
	public Workflow setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public List<Step> getListSteps() {
		if (listSteps == null) {
			listSteps = new ArrayList<Step>();
		}
		return listSteps;
	}
	public Workflow setListSteps(List<Step> listSteps) {
		this.listSteps = listSteps;
		return this;
	}
}
