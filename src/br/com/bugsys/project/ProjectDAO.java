package br.com.bugsys.project;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.bugsys.infra.HibernateUtil;
import br.com.bugsys.user.User;
import br.com.bugsys.userproject.UserProject;
import br.com.bugsys.workflow.Workflow;
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
	
	public Project findProjectByName(String projectName) {
		
		String hql = "FROM Project p WHERE p.projectName = :projectName";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("projectName", projectName);
		
		Project project = (Project) query.uniqueResult();
		
		return project;
	}
	
	public UserProject findUserProjectById(Integer projectID, Integer userID) {
		
		String hql = "FROM UserProject up WHERE up.user.id = :userID AND up.project.id projectID";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("projectID", projectID)
				      .setParameter("userID", userID);
		
		UserProject userProject = (UserProject) query.uniqueResult();
		
		return userProject;
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
	public List<Project> findProjectsByUser(Integer userID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM Project p")
			.append(" JOIN UserProject up")
			.append(" WHERE p.id = up.project.id")
			.append(" AND")
			.append(" up.user.id = :userID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("userID", userID);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsersByProject(Integer projectID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM User u")
			.append(" JOIN UserProject up")
			.append(" WHERE u.id = up.user.id")
			.append(" AND")
			.append(" up.project.id = :projectID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID);
		
		List<User> users = (List<User>) query.list();
		
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findAllProjects() {
		
		String hql = " FROM Project";
		
		Query query = this.session.createQuery(hql);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}
	
	public Project persistProject(Project project) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		Project projectReturn = null;
		
		if(project.getId() == null){
			
			Integer id = (Integer) this.session.save(project);
			
			projectReturn = this.findProjectById(id);
			
		} else {
			
			this.session.update(project);
			projectReturn = project;
		}

		transaction.commit();
		
		return projectReturn;
	}
	
	public UserProject persistUserProject(UserProject userProject) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		UserProject userProjectReturn = null;
			
		this.session.save(userProject);
			
		userProjectReturn = this.findUserProjectById(userProject.getProject().getId(), userProject.getUser().getId());
			
		transaction.commit();
		
		return userProjectReturn;
	}
	
	public void deleteProjectById(Integer projectID) {
	
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();

		this.removeUseCasesByProjectID(projectID);
		this.removeUsersProjectByProjectID(projectID);
		this.removeProjectByID(projectID);
		
		transaction.commit();
	}
	
	private void removeProjectByID(Integer projectID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" DELETE FROM Project p")
			.append(" WHERE p.id = :projectID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID);
		
		query.executeUpdate();
	}
	
	public void deleteUsersProjectByProjectID(Integer projectID) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		this.removeUsersProjectByProjectID(projectID);
		
		transaction.commit();
	}
	
	private void removeUsersProjectByProjectID(Integer projectID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" DELETE FROM UserProject up")
			.append(" WHERE up.project.id = :projectID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID);
		
		query.executeUpdate();
	}
	
	public void deleteUserCasesByProjectID(Integer projectID) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		this.removeUseCasesByProjectID(projectID);
		
		transaction.commit();
	}
	
	private void removeUseCasesByProjectID(Integer projectID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" DELETE FROM UseCase uc")
			.append(" WHERE uc.project.id = :projectID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID);
		
		query.executeUpdate();
	}
}
