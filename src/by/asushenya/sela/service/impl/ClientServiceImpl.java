package by.asushenya.sela.service.impl;

import by.asushenya.sela.bean.Good;
import by.asushenya.sela.bean.User;
import by.asushenya.sela.service.service.ClientService;
import by.asushenya.sela.dao.factory.DAOFactory;
import by.asushenya.sela.dao.EquipmentsDAO;
import by.asushenya.sela.dao.UserDAO;
import by.asushenya.sela.dao.exception.DAOException;

import by.asushenya.sela.service.exception.ServiceException;
import by.asushenya.sela.service.factory.ServiceFactory;
public class ClientServiceImpl implements ClientService {
	
	@Override 
	public void registration(User user) throws ServiceException {
		
		if( user.getLogin()    == null ||
		    user.getPassword() == null)
		throw new ServiceException("Incorrect user");
		 
	try{		
		DAOFactory daoFactoryObject = DAOFactory.getInstance();
		UserDAO userDAO = daoFactoryObject.getUserDAO();
		userDAO.registeredNewUser(user);
		
	} catch(DAOException e){
		
			throw new ServiceException(e);
		}
	}
	
	@Override 
	public void rentEquipment(User user, Good good) throws ServiceException {
		
		if(user.getId() == 0 )
		throw new ServiceException("Incorrect user");	
		
		if(good.getId()     == 0    ||
		   good.getAmount() == 0	||
		   good.getPrice()  == 0)
		throw new ServiceException("Incorrect good");		
		
	try{		
		
		DAOFactory daoFactoryObject = DAOFactory.getInstance();
		UserDAO userDAO = daoFactoryObject.getUserDAO();
		userDAO.rentEquipment(user, good);
		
	} catch(DAOException e){
		
			throw new ServiceException(e);
		}
	}
	
	public String signIn (User user) throws ServiceException{
		
		String response = "";
		
		if( user.getLogin()        == null ||
			user.getPassword() 	   == null)
			throw new ServiceException("Incorrect user: incorrect login or password");
		
		try{			
			DAOFactory daoFactoryObject = DAOFactory.getInstance();
			UserDAO    userDAO = daoFactoryObject.getUserDAO();
			
			response = userDAO.signIn(user);
			return response;
			
		} catch(DAOException e){
			
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String getAllEquipment() throws ServiceException {
		
		String response = "";
		
		try{
			DAOFactory daoFactoryObject = DAOFactory.getInstance();
			EquipmentsDAO clientService = daoFactoryObject.getEquipmentsDAO();
			
			response = clientService.getAllEquipment();
			
			return response;
			
		} catch(DAOException e){
			
			throw new ServiceException(e);
		}
		
	}
}
