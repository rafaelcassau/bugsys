package br.com.bugsys.workflow;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.bugsys.infra.HibernateUtil;
import br.com.bugsys.step.Step;

public class WorkflowDAO {

	private Session session;
	
	public WorkflowDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public Workflow findWorkflowById(Integer id) {
		
		String hql = "FROM Workflow w WHERE w.id = :id";

		Query query = this.session.createQuery(hql)
			.setParameter("id", id);

		Workflow workflow = (Workflow) query.uniqueResult(); 
		
		return workflow;
	}
	
	@SuppressWarnings("unchecked")
	public List<Step> findStepsByWorkflow(Integer workflowID) {
		
		String hql = "FROM Step s WHERE s.workflow.id = :workflowID";
		
		Query query = this.session.createQuery(hql)
				.setParameter("workflowID", workflowID);
		
		List<Step> listSteps = (List<Step>) query.list();
		
		return listSteps;
	}
	
	public Workflow findWorkflowByTitle(String title) {
		
		String hql = "FROM Workflow w WHERE w.title = :title";

		Query query = this.session.createQuery(hql)
			.setParameter("title", title);

		Workflow workflow = (Workflow) query.uniqueResult();
		
		return workflow;
	}
	
	@SuppressWarnings("unchecked")
	public List<Workflow> findAllWorkflows() {
		
		String hql = "FROM Workflow";
		
		Query query = this.session.createQuery(hql);
		
		List<Workflow> list = (List<Workflow>) query.list();
		
		return list;
	}
	
	public Workflow persistWorkflow(Workflow workflow) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		Workflow workflowReturn = null;
		
		if(workflow.getId() == null){
			
			Integer id = (Integer) this.session.save(workflow);
			
			this.persistListSteps(workflow.getListSteps(), workflow);
			
			workflowReturn = this.findWorkflowById(id);
			
		} else {
			
			this.session.update(workflow);
			workflowReturn = workflow;
		}

		transaction.commit();
		
		return workflowReturn;
	}
	
	public void deleteWorkflowById(Integer id) {

		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		Workflow workflow = this.findWorkflowById(id);
		
		this.deleteStepsByIdWorkflow(workflow.getId());
		
		this.session.delete(workflow);
		
		transaction.commit();
	}
	
	private void persistListSteps(List<Step> listSteps, Workflow workflow) {
		
		for (Step step : listSteps) {
			
			step.setWorkflow(workflow);
			this.session.save(step);
		}
	}
	
	private void deleteStepsByIdWorkflow(Integer workflow_FK) {
		
		String sql = "DELETE FROM step WHERE workflow_FK = :workflow_FK";
		
		Query query = this.session.createSQLQuery(sql)
				.setParameter("workflow_FK", workflow_FK);
		
		query.executeUpdate();
	}

	public Step findStepByID(Integer stepID) {
		
		String hql = "FROM Step s WHERE s.id = :stepID";

		Query query = this.session.createQuery(hql)
			.setParameter("stepID", stepID);

		Step step = (Step) query.uniqueResult();
		
		return step;
	}
}
