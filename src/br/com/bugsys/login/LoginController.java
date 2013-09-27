package br.com.bugsys.login;

import java.util.HashMap;
import java.util.Map;

import br.com.bugsys.interceptors.Public;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.user.User;
import br.com.bugsys.user.UserDAO;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.Messages;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class LoginController {

	private UserDAO userDAO;
	private Result result;
	private UserSession userSession;
	
	public LoginController(UserDAO userDAO, Result result, UserSession userSession) {
		this.userDAO = userDAO;
		this.result = result;
		this.userSession = userSession;
	}
	
	/***
	 *  Action responsável por direncionar para login/login
	 */
	
	@Public
	@Get
	public void login() {}

	
	@Public
	@Post
	public void login(String username, String password) {
		
		Map<String, String> message = new HashMap<String, String>();
		
		User currentUser = this.userDAO.findUserByLogin(new Login(username, password));
		
		if (currentUser == null) {
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_LOGIN_INCORRECT);
		} else {
			message.put(AjaxResponseEnum.SUCCESS.getResponse(), Messages.MSG_EMPTY);
			this.userSession.login(currentUser);
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();	
	}
	
	public void logout() {
		this.userSession.logout();
		result.redirectTo(this).login();
	}
}
