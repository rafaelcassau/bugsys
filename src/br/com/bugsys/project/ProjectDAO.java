package br.com.bugsys.project;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.bugsys.infra.HibernateUtil;
import br.com.bugsys.usecase.UseCase;
import br.com.bugsys.user.User;
import br.com.bugsys.userproject.UserProject;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class ProjectDAO {

	private Session session;
	
	public ProjectDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public Project findProjectByID(Integer projectID) {
		
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
	
	public UserProject findUserProjectByID(Integer userProjectID) {
		
		String hql = "FROM UserProject up WHERE up.id = :userProjectID";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("userProjectID", userProjectID);
		
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
	public List<Project> findProjectsByUserID(Integer userID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" SELECT up.project ")
			.append(" FROM UserProject up")
			.append(" WHERE up.user.id = :userID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("userID", userID);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByClientID(Integer clientID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" SELECT p ")
			.append(" FROM Project p")
			.append(" WHERE p.client.id = :clientID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("clientID", clientID);
		
		List<Project> projects = (List<Project>) query.list();
		
		return projects;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsersByProjectID(Integer projectID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" SELECT up.user FROM UserProject up")
			.append(" WHERE up.project.id = :projectID");
		
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
	
	@SuppressWarnings("unchecked")
	public List<UseCase> findUseCasesByProjectID(Integer projectID) {
		
		String hql = " FROM UseCase uc WHERE uc.project.id = :projectID";
		
		Query query = this.session.createQuery(hql)
				.setParameter("projectID", projectID);
		
		List<UseCase> listUseCases = (List<UseCase>) query.list();
		
		return listUseCases;
	}
	
	public UseCase findUseCaseByID(Integer useCaseID) {
		
		String hql = "FROM UseCase uc WHERE uc.id = :useCaseID";
		
		Query query = session.createQuery(hql)
			.setParameter("useCaseID", useCaseID);
		
		UseCase useCase = (UseCase) query.uniqueResult();
		
		return useCase;
	}
	
	public UserProject findUserProjectByProjectIDUserID(Integer projectID, Integer userID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM UserProject up")
			.append(" WHERE up.project.id = :projectID")
			.append(" AND")
			.append(" up.user.id = :userID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID)
				.setParameter("userID", userID);
		
		UserProject userProject = (UserProject) query.uniqueResult();
		
		return userProject;
	}
	
	public User findUserByProjectIDUserID(Integer projectID, Integer userID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" SELECT up.user FROM UserProject up")
			.append(" WHERE up.project.id = :projectID")
			.append(" AND")
			.append(" up.user.id = :userID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID)
				.setParameter("userID", userID);
		
		User user = (User) query.uniqueResult();
		
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserProject> findUserProjectByProjectID(Integer projectID) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM UserProject up")
			.append(" WHERE up.project.id = :projectID");
		
		Query query = this.session.createQuery(hql.toString())
				.setParameter("projectID", projectID);
	
		List<UserProject> listUserProject = (List<UserProject>) query.list();
		
		return listUserProject;
	}
	
	public Project persistProject(Project project) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		Project projectReturn = null;
		
		if(project.getId() == null){
			
			Integer id = (Integer) this.session.save(project);
			
			projectReturn = this.findProjectByID(id);
			
		} else {
			
			this.session.update(project);
			projectReturn = project;
		}

		transaction.commit();
		
		return projectReturn;
	}
	
	public void persistListUserProject(List<UserProject> listUserProject) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		for (UserProject userProject : listUserProject) {
			
			this.session.save(userProject);
		}
			
		transaction.commit();
		
	}
	
	public UseCase saveUseCasesProject(UseCase useCase) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
			
		int id = (Integer) this.session.save(useCase);
		
		transaction.commit();
		
		UseCase useCaseReturn = this.findUseCaseByID(id);
		
		return useCaseReturn;
	}
	
	public UseCase updateUseCasesProject(UseCase useCase) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		this.session.update(useCase);
		
		transaction.commit();
		
		return useCase;
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
	
	public void deleteUseCasesByProjectID(Integer projectID) {
		
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

	public void deleteUserProjectByProjectIDUserID(Integer projectID, Integer userID) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		StringBuilder hql = new StringBuilder()
			.append(" DELETE FROM UserProject up")
			.append(" WHERE up.project.id = :projectID")
			.append(" AND")
			.append(" up.user.id = :userID");
	
		Query query = this.session.createQuery(hql.toString())
			.setParameter("projectID", projectID)
			.setParameter("userID", userID);
	
		query.executeUpdate();
		
		transaction.commit();
	}

	public void deleteUseCaseByID(Integer useCaseID) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		String hql = "DELETE FROM UseCase uc WHERE uc.id = :useCaseID";
		
		Query query = this.session.createQuery(hql)
			.setParameter("useCaseID", useCaseID);
		
		query.executeUpdate();
		
		transaction.commit();
	}
}
