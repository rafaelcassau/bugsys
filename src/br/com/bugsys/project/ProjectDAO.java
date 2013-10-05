package br.com.bugsys.project;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.bugsys.infra.HibernateUtil;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class ProjectDAO {

	private Session session;
	
	public ProjectDAO() {
		
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public Project findProjectById(Integer projectID) {
		
		String hql = "FROM Project p WHERE p.id = :projectID";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("projectID", projectID);
		
		Project project = (Project) query.uniqueResult();
		
		return project;
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByWorkflowID(Integer workflowID) {
		
		String hql = "FROM Project p WHERE p.workflow.id = :workflowID";
		
		Query query = this.session.createQuery(hql)
				.setParameter("workflowID", workflowID);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByUserID(Integer userID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM Project p")
			.append(" JOIN UserProject up")
			.append(" WHERE p.id = up.project.id")
			.append(" AND")
			.append(" up.id = :userID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("userID", userID);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findAllProjects() {
		
		String hql = " FROM Project";
		
		Query query = this.session.createQuery(hql);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}
}
