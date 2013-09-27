package br.com.bugsys.usecase;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.bugsys.project.Project;

@Entity
public class UseCase {

	@Id
	@GeneratedValue
	private Integer id;
	private String code;
	private String name;
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_FK")
	private Project project;
	
	public Integer getId() {
		return id;
	}
	public UseCase setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public String getCode() {
		return code;
	}
	public UseCase setCode(String code) {
		this.code = code;
		return this;
	}
	
	public String getName() {
		return name;
	}
	public UseCase setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getDescription() {
		return description;
	}
	public UseCase setDescription(String description) {
		this.description = description;
		return this;
	}

	public Project getProject() {
		if (project == null) {
			project = new Project();
		}
		return project;
	}
	public UseCase setProject(Project project) {
		this.project = project;
		return this;
	}
}
