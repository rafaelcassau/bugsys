package br.com.bugsys.user;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.bugsys.infra.HibernateUtil;
import br.com.bugsys.login.Login;

public class UserDAO {

	private Session session;
	
	public UserDAO() {
		this.session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public User findUserByLogin(Login login) {

		String hql = "FROM User u WHERE u.username = :username AND u.password = :password";

		Query query = this.session.createQuery(hql)
			.setParameter("username", login.getUsername())
			.setParameter("password", login.getPassword());

		return (User) query.uniqueResult();
	}

	public User findUserById(Integer id) {
		
		String hql = "FROM User u WHERE u.id = :id";
		
		Query query = this.session.createQuery(hql)
				      .setParameter("id", id);
		
		User user = (User) query.uniqueResult();
		
		return user;
	}
	
	public User findUserByUsername(String username) {
		
		String hql = "FROM User u WHERE u.username = :username";
		
		Query query = this.session.createQuery(hql)
				.setParameter("username", username);
		
		User user = (User) query.uniqueResult();
		
		return user;
	}

	public User findUserByMail(String mail) {
		
		String hql = "FROM User u WHERE u.mail = :mail";
		
		Query query = this.session.createQuery(hql)
				.setParameter("mail", mail);
		
		User user = (User) query.uniqueResult();
		
		return user;
	}
	

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		
		String hql = "FROM User u WHERE u.id NOT IN (SELECT c.user.id FROM Client c) ";

		Query query = this.session.createQuery(hql);
		
		List<User> listUsers = (List<User>) query.list();
		
		return listUsers;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsersLikeName(String name) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM User u")
			.append(" WHERE")
			.append(" u.id NOT IN (SELECT c.user.id FROM Client c)")
			.append(" AND u.name")
			.append(" LIKE " + "'%" + name + "%'");

		Query query = this.session.createQuery(hql.toString());
		
		List<User> listUsers = (List<User>) query.list();
		
		return listUsers;
	}
	
	public List<User> findUsersByIds(String userIds) {
		
		StringBuilder hql = new StringBuilder()
			.append(" FROM User u")
			.append(" WHERE")
			.append(" u.id IN (:userIds)");

		Query query = this.session.createQuery(hql.toString())
				.setParameter("userIds", userIds);
		
		List<User> listUsers = (List<User>) query.list();
		
		return listUsers;
	}
	
	public User persistUser(User user) {
		
		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		User userReturn = null;
		
		if(user.getId() == null){
			
			int id = (Integer) this.session.save(user);
			userReturn = this.findUserById(id);
			
		} else {
			
			this.session.update(user);
			userReturn = user;
		}
		
		transaction.commit();
		
		return userReturn;
	}

	public void deleteUserById(Integer id) {

		this.session.clear();
		
		Transaction transaction = this.session.beginTransaction();
		
		User user = findUserById(id);
		
		this.session.delete(user);
		
		transaction.commit();
	}
}
