package br.com.bugsys.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.client.Client;
import br.com.bugsys.client.ClientDAO;
import br.com.bugsys.project.Project;
import br.com.bugsys.project.ProjectDAO;
import br.com.bugsys.user.User;
import br.com.bugsys.user.UserDAO;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.Messages;

public class UserBusiness {
	
	private UserDAO userDAO;
	private ClientDAO clientDAO;
	private ProjectDAO projectDAO;
	
	public UserBusiness() {
		this.userDAO = new UserDAO();
		this.clientDAO = new ClientDAO();
		this.projectDAO = new ProjectDAO();
	}
	
	public Map<String, String> addUser(User user) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = this.userIsValidAdd(user);
		
		if(message.isEmpty()){
			this.userDAO.persistUser(user);
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);
		}
		
		return message;
	}
	
	public Map<String, String> userIsValidAdd(User user){
		
		Map<String, String> message = new HashMap<String, String>();

		if (this.userDAO.findUserByUsername(user.getUsername()) != null) {
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_USERNAME);
			return message;
		}

		if (this.userDAO.findUserByMail(user.getMail()) != null) {
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_MAIL);
			return message;
		}
		
		return message;
	} 
	
	public Map<String, String> userIsValidUpdate(User user){
		
		Map<String, String> message = new HashMap<String, String>();
		
		User userByNome = this.userDAO.findUserByUsername(user.getUsername()); 
		User userByMail = this.userDAO.findUserByMail(user.getMail()); 
		
		if (userByNome != null && !userByNome.getId().equals(user.getId())) {
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_USERNAME);
			return message;
		}

		if (userByMail != null && !userByMail.getId().equals(user.getId())) {
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_MAIL);
			return message;
		}
		
		return message;
	} 
	
	public User addUserClient(User userParam) {
		
		User user = this.userDAO.persistUser(userParam);
		
		return user;
	}
	
	public User updateUserClient(User userParam) {
		
		User user = this.userDAO.persistUser(userParam);
		
		return user;
	}
	
	public Map<String, String> updateUser(User user) {

		Map<String, String> message = new HashMap<String, String>();
		
		message = this.userIsValidUpdate(user);
		
		if (message.isEmpty()) {
			this.userDAO.persistUser(user);
			
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_UPDATE_SUCCESS);
			
			return message;
		}
		
		return message;
	}
	
	public Map<String, String> deleteUserById(Integer id) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		Client client =  this.clientDAO.findClientByUserId(id);
		
		if (client != null) {
			
			message = this.existClientProject(client.getId());
			
			if (message.isEmpty()) {
				
				this.clientDAO.deleteClientById(client.getId());
				message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
			} 
			
		} else {
			
			message = this.existUserProject(id);
			
			if (message.isEmpty()) {
				
				this.userDAO.deleteUserById(id);
				message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
			} 
		}
		
		return message;
	}
	
	private Map<String, String> existUserProject(Integer userID) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		List<Project> projectList = this.projectDAO.findProjectsByUserID(userID);
		
		if (!projectList.isEmpty())
		
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_USER_PROJECT_VINCULED);
		
		return message;
	}
	
	private Map<String, String> existClientProject(Integer clientID) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		List<Project> projectList = this.projectDAO.findProjectsByClientID(clientID);
		
		if (!projectList.isEmpty())
		
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_CLIENT_PROJECT_VINCULED);
		
		return message;
	} 
}
