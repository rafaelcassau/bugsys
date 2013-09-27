package br.com.bugsys.usecase;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.bugsys.infra.HibernateUtil;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class UseCaseDAO {

	private Session session;
	
	public UseCaseDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public UseCase findUseCaseById(Integer id) {
		
		String hql = "FROM UseCase uc WHERE uc.id = :id";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("id", id);
		
		UseCase useCase = (UseCase) query.uniqueResult();
		
		return useCase;
	}
	
	public UseCase findUseCaseByCode(String code, Integer projectID) {
		
		String hql = "FROM UseCase uc WHERE uc.code = :code AND uc.project.id = :projectID";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("code", code)
				      .setParameter("projectID", projectID);
		
		UseCase useCase = (UseCase) query.uniqueResult();
		
		return useCase;
	}
	
	public UseCase findUseCaseByName(String name, Integer projectID) {
		
		String hql = "FROM UseCase uc WHERE uc.name = :name AND uc.project.id = :projectID";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("name", name)
				      .setParameter("projectID", projectID);
		
		UseCase useCase = (UseCase) query.uniqueResult();
		
		return useCase;
	}
	
	@SuppressWarnings("unchecked")
	public List<UseCase> findUseCasesByProject(Integer projectID) {
		
		String hql = "FROM UseCase uc Where uc.project.id = :projectID";
		
		Query query = this.session.createQuery(hql)
				.setParameter("projectID", projectID);
				
		List<UseCase> listUseCases = (List<UseCase>) query.list();
		
		return listUseCases;
	}
	
	public UseCase persistUseCase(UseCase useCase) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		UseCase useCaseReturn = null;
		
		if(useCase.getId() == null){
			
			Integer id = (Integer) this.session.save(useCase);
			useCaseReturn = this.findUseCaseById(id);
			
		} else {
			
			this.session.update(useCase);
			useCaseReturn = useCase;
		}

		transaction.commit();
		
		return useCaseReturn;
	}
	
	public void deleteUseCaseById(Integer id) {

		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		UseCase useCase = this.findUseCaseById(id);
		
		this.session.delete(useCase);
		
		transaction.commit();
	}
}
