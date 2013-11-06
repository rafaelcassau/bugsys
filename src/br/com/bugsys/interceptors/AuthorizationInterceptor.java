package br.com.bugsys.interceptors;

import java.util.Arrays;

import br.com.bugsys.login.LoginController;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.ValidationMessage;

 //@Intercepts
public class AuthorizationInterceptor implements Interceptor{

	private final UserSession userSession;
	private Result result;
	
	public AuthorizationInterceptor(UserSession userSession, Result result) {
		this.userSession = userSession;
		this.result = result;
	}
	
	public boolean accepts(ResourceMethod method) {
		return !method.containsAnnotation(Public.class);
	}
	
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		
		if (userSession.getUser() == null) {
			result.include("errors", Arrays.asList(new ValidationMessage("Favor realizar a autenticação no sistema.", "user")));
			result.redirectTo(LoginController.class).login();
		} else {
			stack.next(method, resourceInstance);
		}
	}
}
