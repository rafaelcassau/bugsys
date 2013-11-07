package br.com.bugsys.dashboard;

import br.com.bugsys.interceptors.Functionality;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.util.FunctionalityEnum;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;

@Resource
public class DashboardController {
	
	private UserSession userSession;
	
	public DashboardController(UserSession userSession) {
		this.userSession = userSession;
	}
	
	@Get
	public void dashboard() {
		
		this.userSession.setFunctionality(new Functionality(FunctionalityEnum.HOME.getFunctionality()));
	}
}
