package com.epf.RentManager.validator;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Vehicle;

public class VehicleValidator {

	public static void checkConstructeur(Vehicle vehicle) throws ServiceException {
		if(vehicle.getConstructeur().isEmpty()){
			throw new ServiceException("Le constructeur du v�hicule doit etre sp�cifi�");
		}
	}
	public static void checkModele(Vehicle vehicle) throws ServiceException {
		if(vehicle.getModele().isEmpty()){
			throw new ServiceException("Le modele du v�hicule doit etre sp�cifi�");
		}
	}
}
