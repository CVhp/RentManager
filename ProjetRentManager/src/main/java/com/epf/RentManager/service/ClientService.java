package com.epf.RentManager.service;

import com.epf.RentManager.dao.ClientDao;
import com.epf.RentManager.exception.DaoException;
import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.validator.ClientValidator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class ClientService {

	private ClientDao clientDao;
	private boolean test;

	public static ClientService instance;
	private static ClientService instanceTest = null;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance(false);
	}

	private ClientService(boolean test) {
		this.clientDao = ClientDao.getInstance(test);
		this.test = test;
	}
	
	public static ClientService getInstance(boolean test) {
		if(test){
			if(instanceTest == null) {
				instanceTest = new ClientService(true);
			}
			return instanceTest;
		}else {
			if (instance == null) {
				instance = new ClientService(false);

			}
			return instance;
		}
	}
	
	
	
	public long create(Client client) throws ServiceException {
		try {
			ClientValidator.checkEmpty(client);
			ClientValidator.checkSize(client);
			ClientValidator.checkEmail(client);
			checkUnique(client);		
			checkAge(client);			
		
			return clientDao.create(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(Client client) throws ServiceException{
		try{
			ReservationService reservationService = ReservationService.getInstance(false);
			try {
				List<Reservation> listReservation = reservationService.findByIdClient(client.getId());
				for (Reservation reservationAssociee : listReservation) {
					reservationService.delete(reservationAssociee);
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return clientDao.delete(client);
		} catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long update(Client client) throws ServiceException {
		try {
			ClientValidator.checkEmpty(client);
			ClientValidator.checkSize(client);
			ClientValidator.checkEmail(client);
			checkAge(client);	
			checkUnique(client);
		
			return clientDao.update(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Client findById(long id) throws ServiceException {
		try{
			Optional<Client> optClient = clientDao.findById(id);
			Client c = new Client();
			if(optClient.isPresent()){
				c = optClient.get();
				return c;
			} else {
				throw new ServiceException("ID n'existe pas");
			}			
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
	

	
	private void checkAge (Client client) throws ServiceException{
		long age = ChronoUnit.YEARS.between(client.getNaissance().toLocalDate(), LocalDate.now());
		if (age<18){
			throw new ServiceException("Le client doit avoir 18 ans. L'age entré est de : " + age + "ans.");
		}
	}
	
	public void checkUnique(Client client) throws ServiceException {
		List<Client> listClient = this.findAll();
		for (Client clientTrouve : listClient) {
			if (clientTrouve.getEmail().equals(client.getEmail())) {
				throw new ServiceException("Un compte associé à l'adresse e-mail : " + client.getEmail() + " existe déjà.");
			}
		}
	}

	
	
}
