package com.epf.RentManager.service;

import com.epf.RentManager.dao.ReservationDao;
import com.epf.RentManager.exception.DaoException;
import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.validator.ReservationValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReservationService {
	
    private ReservationDao reservationDao;
    private boolean test;
    
    public static ReservationService instance;
    public static ReservationService instanceTest = null;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance(false);
    }
    
    private ReservationService(boolean test) {
		this.reservationDao = ReservationDao.getInstance(test);
		this.test = test;
	}
    

    public static ReservationService getInstance(boolean test) {
    	if(test){
			if(instanceTest == null) {
				instanceTest = new ReservationService(true);
			}
			return instanceTest;
		}else {
			if (instance == null) {
				instance = new ReservationService(false);

			}
			return instance;
		}
    }
    
    
    
    

    public long create(Reservation reservation) throws ServiceException {
    	 try {
	    	ReservationValidator.checkDateValidity(reservation);
	    	checkSevenDays(reservation);
	    	checkDisponibility(reservation);
	    	checkThirtyDays(reservation);
       
            return reservationDao.create(reservation);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    
    public long delete(Reservation reservation) throws ServiceException{
        try{
            return reservationDao.delete(reservation);
        } catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    
    public long update(Reservation reservation) throws ServiceException {
    	try {
	    	ReservationValidator.checkDateValidity(reservation);
	    	checkSevenDays(reservation);
        
            return reservationDao.update(reservation);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    
    public Reservation findById(long id) throws ServiceException {
		try{
			Optional<Reservation> optReservation = reservationDao.findById(id);
			Reservation reservation = new Reservation();
			if(optReservation.isPresent()){
				reservation = optReservation.get();
				return reservation;
			} else {
				//throw new ServiceException("ID n'existe pas");
				return reservation;
			}			
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    
    public List<Reservation> findByIdClient(long id) throws ServiceException {
        try{
            List<Reservation> listReservation= reservationDao.findResaByClientId(id);
            if (listReservation.isEmpty()) {
				//throw new ServiceException("Cet ID client n'a pas de réservation");
            	return listReservation;
			} else {
				return listReservation;
			}
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findByIdVehicle(long id) throws ServiceException {
        try{
            List<Reservation> listReservation= reservationDao.findResaByVehicleId(id);
            if (listReservation.isEmpty()) {
				throw new ServiceException("Cet ID véhicule n'a pas de réservation");
			} else {
				return listReservation;
			}
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
    
    

    
    private void checkSevenDays(Reservation reservation) throws ServiceException{
    	Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		long period = ChronoUnit.DAYS.between(debut.toLocalDate(), fin.toLocalDate());
		if(period > 7) {
			throw new ServiceException("La période de réservation ne peut exéder 7 jours");
		}
    }
    
    private void checkDisponibility(Reservation reservation) throws ServiceException{
    	ReservationService reservationService = ReservationService.getInstance(false);
    	
    	Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		Boolean disponibility = true;
		
		List<Reservation> listReservation = reservationService.findByIdVehicle(reservation.getVehicle_id());
		for (Reservation r : listReservation) {
			if (!((debut.compareTo(r.getDebut())<0 && fin.compareTo(r.getDebut())<0)||(debut.compareTo(r.getFin())>0 && fin.compareTo(r.getFin())>0))) {
				disponibility = false;
			}
		}
		if (!disponibility) {
			throw new ServiceException("Le véhicule est déjà réservé durant cette période.");
		}
    }
    
    private void checkThirtyDays(Reservation reservation) throws ServiceException{
    	ReservationService reservationService = ReservationService.getInstance(false);
    	
		long compteur = 0;
		Boolean first = true;
		
				
		List<Reservation> listReservation = new ArrayList<Reservation>();
		listReservation = reservationService.findByIdVehicle(reservation.getVehicle_id());
		listReservation.add(reservation);
		Collections.sort(listReservation);
		
		for (int i =0; i < listReservation.size()-1; i++) {
			long ecart = ChronoUnit.DAYS.between(listReservation.get(i).getFin().toLocalDate(), listReservation.get(i+1).getDebut().toLocalDate());			
			if(ecart == 1) {
				System.out.println("ecart1");
				if (first) {
					compteur = ChronoUnit.DAYS.between(listReservation.get(i).getDebut().toLocalDate(), listReservation.get(i).getFin().toLocalDate());
					first = false;
				}				
				compteur = compteur + ChronoUnit.DAYS.between(listReservation.get(i+1).getDebut().toLocalDate(), listReservation.get(i+1).getFin().toLocalDate());
				System.out.println(compteur);
			}			
		}
		if (compteur > 30) {
			throw new ServiceException("Le véhicule ne peut être réservé 30 jours d'affilé.");
		}
		
	}
			
			
    }
    
    



