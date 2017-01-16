package by.asushenya.sela.controller.command.impl;

import by.asushenya.sela.controller.command.Command;
import by.asushenya.sela.service.exception.ServiceException;
import by.asushenya.sela.service.factory.ServiceFactory;
import by.asushenya.sela.service.service.ClientService;

public class GetAllEquipment implements Command {

	@Override 
	public String execute(String request){
		
		String response = "";
		
		ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();		
		ClientService clientService = serviceFactoryObject.getClientService();
		
		try{
			response = clientService.getAllEquipment();
			return response;
			
		} catch(ServiceException e){
			response = e.getMessage();
		}
		
		return response;
	}
}
