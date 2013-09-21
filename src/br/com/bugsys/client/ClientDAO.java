package br.com.bugsys.client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.bugsys.infra.HibernateUtil;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class ClientDAO {

	private Session session;
	
	public ClientDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public Client findClientById(Integer id) {
		
		String hql = "FROM Client c WHERE c.id = :id";
		
		Query query = this.session.createQuery(hql)
				.setParameter("id", id);
		
		return (Client) query.uniqueResult();
	}
	
	public Client findClientByIE(String ie){
		
		String hql = "FROM Client c WHERE c.stateRegistration = :ie";
		
		Query query = this.session.createQuery(hql)
						  .setParameter("ie", ie);
		
		return (Client) query.uniqueResult();
	}
	
	public Client findClientByCorporateName(String corporate) {
		
		String hql = "FROM Client c WHERE c.corporateName = :corporate";
		
		Query query = this.session.createQuery(hql)
						  .setParameter("corporate", corporate);
		
		return (Client) query.uniqueResult();
	}
	
	public Client findClientByFancyName(String fancyName){
		
		String hql = "FROM Client c WHERE c.fancyName = :fancyName";
		
		Query query = this.session.createQuery(hql)
						  .setParameter("fancyName", fancyName);
		
		return (Client) query.uniqueResult();
	}

	public Client findClientByCNPJ(String CNPJ) {
		
		String hql = "FROM Client c WHERE c.CNPJ = :CNPJ";
		
		Query query = this.session.createQuery(hql)
				.setParameter("CNPJ", CNPJ);
		
		return (Client) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> findAllClients() {
		
		String hql = "FROM Client";
		
		Query query = this.session.createQuery(hql);
		
		List<Client> listUsers = (List<Client>) query.list();
		
		return listUsers;
	}
	
	public void persistClient(Client client) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		this.session.saveOrUpdate(client);
		
		transaction.commit();
	}
	
	public void deleteClientById(Integer id) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		Client client = findClientById(id);
		
		this.session.delete(client);
		
		transaction.commit();
	}
	
	public Client findClientByUserId(Integer id) {
		
		String hql = "FROM Client c WHERE c.user.id = :id";
		
		Query query = this.session.createQuery(hql)
				.setParameter("id", id);
		
		return (Client) query.uniqueResult();
	}
}
