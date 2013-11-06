package br.com.bugsys.event;

import java.util.List;

import br.com.bugsys.interceptors.Functionality;
import br.com.bugsys.interceptors.UserSession;
import br.com.bugsys.util.FunctionalityEnum;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class EventController {

	private EventDAO eventDAO;
	private UserSession userSession;
	private Result result;
	
	public EventController(EventDAO eventDAO, UserSession userSession, Result result) {
		this.eventDAO = eventDAO;
		this.userSession = userSession;
		this.result = result;
	}
	
	@Get
	public List<Event> list() {
		
		this.userSession.setFunctionality(new Functionality(FunctionalityEnum.EVENT.getFunctionality()));
		
		List<Event> listEvents = this.eventDAO.findEventByUserID(this.userSession.getUser().getId());
				
		return listEvents;
	}
	
	@Get
	public void event() {
		
	}
	
	@Post
	public void event(String id) {
		
	}
	
	@Get
	public void delegateEvent() {
		
	}
	
	@Get
	public void closeEvent() {
		
	}
	
	@Get
	public void reOpenEvent() {
		
	}
}
