package br.com.bugsys.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

import br.com.bugsys.step.Step;


@Entity
public class Workflow {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String description;
	
	private List<Step> listSteps;
		
	public Integer getId() {
		return id;
	}
	public Workflow setId(Integer id) {
		this.id = id;
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
