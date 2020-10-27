package com.epf.RentManager.validator;

import java.sql.Date;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Reservation;

public class ReservationValidator {

    public static void checkDateValidity(Reservation reservation) throws ServiceException{
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		if(debut.compareTo(fin)>0) {
			throw new ServiceException("La date de fin doit être supérieure à la date de début");
		}
	}

}
