package br.com.bugsys.employeeType;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.bugsys.infra.HibernateUtil;

public class EmployeeTypeDAO {

	private Session session;
	
	public EmployeeTypeDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public List<EmployeeType> findAllEmployeeTypes() {
		
		String hql = "FROM EmployeeType";
		
		Query query = session.createQuery(hql);
		
		List<EmployeeType> listEmployeeType = (List<EmployeeType>) query.list();
		
		return listEmployeeType;
	}
	
	public EmployeeType findEmployeeTypeById(Integer id) {
		
		String hql = "FROM EmployeeType et WHERE et.id = :id";
		
		Query query = this.session.createQuery(hql)
				.setParameter("id", id);
		
		EmployeeType employeeType = (EmployeeType) query.uniqueResult();
		
		return employeeType;
	}
}
