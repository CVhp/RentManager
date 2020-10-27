package com.epf.RentManager.validator;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;

public class ClientValidator {
	
	public static void checkEmpty(Client client) throws ServiceException {
		if(client.getNom().isEmpty()){
			throw new ServiceException("Veuillez renseigner le nom du client.");
		} else if (client.getPrenom().isEmpty()){
			throw new ServiceException("Veuillez renseigner le prenom du client.");
		} else if (client.getEmail().isEmpty()) {
			throw new ServiceException("Veuillez renseigner l'email du client.");			
		} else if (client.getNaissance().toString().isEmpty()) {
			throw new ServiceException("Veuillez renseigner la date de naissance du client.");			
		}
	}
	
	public static void checkSize(Client client) throws ServiceException {
		if(client.getNom().length() < 3) {
			throw new ServiceException("Le nom du client doit au moins avoir 3 caractères.");
		} else if(client.getPrenom().length() < 3) {
			throw new ServiceException("Le prenom du client doit au moins avoir 3 caractères.");
		}
	}
	
	public static void checkEmail(Client client) throws  ServiceException {
		if((client.getEmail()==null) || client.getEmail().trim().length()<1 || !client.getEmail().matches("^[a-zA-Z0-9\\.\\-\\_]+@([a-zA-Z0-9\\-\\_\\.]+\\.)+([a-zA-Z]{2,4})$")){
			throw new ServiceException("Vous n'avez pas saisi d'email ou sa forme n'est pas valide");
		} 
	}
	
}
