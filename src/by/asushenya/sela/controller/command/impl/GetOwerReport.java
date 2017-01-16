package by.asushenya.sela.controller.command.impl;

import by.asushenya.sela.controller.command.Command;
import by.asushenya.sela.service.exception.ServiceException;
import by.asushenya.sela.service.factory.ServiceFactory;
import by.asushenya.sela.service.service.ShopService;
import by.asushenya.sela.bean.Equipment;
import by.asushenya.sela.bean.Ower;

import java.util.List;

public class GetOwerReport implements Command {
	
	@Override
	public String execute (String request){
		StringBuilder response = new StringBuilder();
		
		ServiceFactory serviceFactoryObject = ServiceFactory.getInstance();
		ShopService shopService = serviceFactoryObject.getShopService();
		
		try{
			List<Ower> owerList = shopService.getOwerReport();
			
			for(Ower ower : owerList){
				
				response.append(ower.getUser().getLogin() + ": ");
				
					for(Equipment item: ower.getEquipments()){
					
						response.append(" "+item.getName() +" "+ item.getQuantity()+", ");
					}
					
					response.append("\n");
			}
			response.append("owers count: "+ owerList.size());
			
			return new String(response);
			
		} catch(ServiceException e){
			response.append(e.getMessage());
		}
		
		return new String(response);
		}

}
