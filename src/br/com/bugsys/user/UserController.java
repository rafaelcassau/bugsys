package br.com.bugsys.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.business.UserBusiness;
import br.com.bugsys.employeeType.EmployeeType;
import br.com.bugsys.employeeType.EmployeeTypeDAO;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.Messages;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class UserController {
	
	private Result result;
	private UserDAO userDAO;
	private EmployeeTypeDAO employeeTypeDAO;
	private UserBusiness userBusiness = new UserBusiness();
	
	public UserController(UserDAO userDAO, Result result, EmployeeTypeDAO employeeTypeDAO) {
		this.userDAO = userDAO;
		this.result = result;
		this.employeeTypeDAO = employeeTypeDAO;
	}
	
	@Get
	public List<User> list() {
		
		List<User> listUsers = new ArrayList<User>();
		
		listUsers = this.userDAO.findAllUsers();
		
		return listUsers;
	}

	@Get("/user")
	public void user() {
		
		List<EmployeeType> employeeTypeList = this.employeeTypeDAO.findAllEmployeeTypes();
		
		this.result.include("employeeTypeList", employeeTypeList);
	};
	
	@Post
	public void user(String id, String username, String password, String name, String mail, String employeeType) {

		Integer employeeTypeId = Integer.valueOf(employeeType);
		
		EmployeeType employeeTypeEntity = this.employeeTypeDAO.findEmployeeTypeById(employeeTypeId);
		
		if (id == null || id.trim().isEmpty()) {
			this.addUser(new User().setUsername(username).setPassword(password).setName(name).setMail(mail).setEmployeeType(employeeTypeEntity));
		} else {
			Integer userID = Integer.valueOf(id);
			this.updateUser(new User().setId(userID).setUsername(username).setPassword(password).setName(name).setMail(mail).setEmployeeType(employeeTypeEntity));
		}
	}
	
	@Get("/user/{id}")
	public User user(int id) {
		
       User user = this.userDAO.findUserById(id);
		
	   List<EmployeeType> employeeTypeList = this.employeeTypeDAO.findAllEmployeeTypes();
	   
	   this.result.include("employeeTypeList", employeeTypeList);
	   
	   return user;
	}
	
	@Post
	public void delete(Integer id){
		
		Map<String, String> message = new HashMap<String, String>();

		message = this.userBusiness.deleteUserById(id);

		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private void addUser(User user) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		message = this.userBusiness.addUser(user);
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private void updateUser(User user) {

		Map<String, String> message = new HashMap<String, String>();
		
		message = this.userBusiness.updateUser(user); 
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
}