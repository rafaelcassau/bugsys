package br.com.bugsys.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bugsys.business.UserBusiness;
import br.com.bugsys.user.User;
import br.com.bugsys.util.AjaxResponseEnum;
import br.com.bugsys.util.Messages;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class ClientController {

	private ClientDAO clientDAO;
	private Result result;
	private UserBusiness userBusiness = new UserBusiness();
	
	public ClientController(ClientDAO clientDAO, Result result) {
		this.clientDAO = clientDAO;
		this.result = result;
	}

	@Get
	public List<Client> list() {
		
		List<Client> listClients = new ArrayList<Client>();
		
		listClients = this.clientDAO.findAllClients();
		
		return listClients;
	}
	
	@Get("/client")
	public void client() {}
	
	@Get("/client/{id}")
	public Client client(Integer id) {
		Client client = this.clientDAO.findClientById(id);
		return client;
	}

	@Post 
	public void client(
					String idClient,
					String corporate,
					String fancyName,
					String cnpj, 
					String ie,
					String address,
					String phone, 
					String mobile,
					String idUser,
					String name,
					String mail,
					String username,
					String password
					) {
		
		if(idClient == null || idClient.isEmpty()){
			this.addClient(new Client().setCorporateName(corporate)
									   .setFancyName(fancyName)
									   .setCNPJ(cnpj)
									   .setStateRegistration(ie)
									   .setAddress(address)
									   .setPhone(phone)
									   .setMobile(mobile)
									   .setUser(new User().setName(name)
									   					  .setMail(mail)
									   					  .setUsername(username)
											   			  .setPassword(password)));
		} else {
			
			Integer clientID = Integer.valueOf(idClient);
			Integer userID = Integer.valueOf(idUser);
			
			this.updateClient(new Client().setId(clientID)
			 							  .setCorporateName(corporate)
										  .setFancyName(fancyName)
										  .setCNPJ(cnpj)
										  .setStateRegistration(ie)
										  .setAddress(address)
										  .setPhone(phone)
										  .setMobile(mobile)
										  .setUser(new User().setId(userID)
												  			 .setName(name)
									   					     .setMail(mail)
									   					     .setUsername(username)
											   			     .setPassword(password)));
		}
	}
	
	@Post
	public void delete(Integer id) {
		
		Map<String, String> message = new HashMap<String, String>();

		this.clientDAO.deleteClientById(id);

		message.put(AjaxResponseEnum.SUCESS.getResponse(), Messages.MSG_DELETE_SUCCESS);
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}
	
	private void addClient(Client client)
	{
		Map<String, String> message = new HashMap<String, String>();
		
		if(this.clientDAO.findClientByCNPJ(client.getCNPJ()) != null){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_CNPJ);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		if(this.clientDAO.findClientByCorporateName(client.getCorporateName()) != null){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_CORPORATE);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		if(this.clientDAO.findClientByFancyName(client.getFancyName()) != null){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_FANCY);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		if(this.clientDAO.findClientByIE(client.getStateRegistration()) != null){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_IE);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		message = this.userBusiness.userIsValidAdd(client.getUser());
		
		if(message.isEmpty()){
			
			User userClient = this.userBusiness.addUserClient(client.getUser());
			
			client.setUser(userClient);
			
			this.clientDAO.persistClient(client);
			
			message.put(AjaxResponseEnum.SUCESS.getResponse(), Messages.MSG_INSERT_SUCCESS);	
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}

	private void updateClient(Client client)
	{
		Map<String, String> message = new HashMap<String, String>();
		
		Client clientByID = null;
		
		clientByID = this.clientDAO.findClientByCNPJ(client.getCNPJ());
		
		if(clientByID != null && !client.getId().equals(clientByID.getId())){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_CNPJ);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		clientByID = this.clientDAO.findClientByCorporateName(client.getCorporateName());
		
		if(clientByID != null && !client.getId().equals(clientByID.getId())){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_CORPORATE);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		clientByID = this.clientDAO.findClientByFancyName(client.getFancyName());
		
		if(clientByID != null && !client.getId().equals(clientByID.getId())){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_FANCY);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		clientByID = this.clientDAO.findClientByIE(client.getStateRegistration());
		
		if(clientByID != null && !client.getId().equals(clientByID.getId())){
			message.put(AjaxResponseEnum.ERROR.getResponse(), Messages.MSG_EXISTS_IE);
			this.result.use(Results.json()).withoutRoot().from(message).serialize();
			return;
		}
		
		message = this.userBusiness.userIsValidUpdate(client.getUser());
		
		if(message.isEmpty()){
			
			User userClient = this.userBusiness.updateUserClient(client.getUser());
			
			client.setUser(userClient);
			
			this.clientDAO.persistClient(client);
			
			message.put(AjaxResponseEnum.SUCESS.getResponse(), Messages.MSG_UPDATE_SUCCESS);	
		}
		
		this.result.use(Results.json()).withoutRoot().from(message).serialize();
	}	
}
