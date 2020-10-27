package com.epf.RentManager.dao;

import com.epf.RentManager.exception.DaoException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDao {

	private static ReservationDao instance = null;
	private static ReservationDao instanceTest = null;
	private boolean test;
	
	private ReservationDao(boolean test) {
		this.test = test;
	}
	
	private ReservationDao(){}
	
	public static ReservationDao getInstance(boolean test) {
		if(test){
			if(instanceTest == null) {
				instanceTest = new ReservationDao(true);
			}
			return instanceTest;
		}else {
			if (instance == null) {
				instance = new ReservationDao(false);
			}
			return instance;
		}
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
		
	public long create(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(CREATE_RESERVATION_QUERY);){

			statement.setLong(1, reservation.getClient_id());
			statement.setLong(2, reservation.getVehicle_id());
			statement.setDate(3, reservation.getDebut());
			statement.setDate(4, reservation.getFin());

			long result = statement.executeUpdate();
			return result;
		} catch (SQLException e){
			throw new DaoException("Erreur lors de la création de la réservation : " + e.getMessage());
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(DELETE_RESERVATION_QUERY);){

			statement.setLong(1, reservation.getReservation_id());
			long result = statement.executeUpdate();

			return result;

		} catch (SQLException e){
			throw new DaoException("Erreur lors de la suppression de la réservation : " + e.getMessage());
		}
	}
	
	public long update(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(UPDATE_RESERVATION_QUERY);){

			statement.setLong(1, reservation.getClient_id());
			statement.setLong(2, reservation.getVehicle_id());
			statement.setDate(3, reservation.getDebut());
			statement.setDate(4, reservation.getFin());
			
			statement.setLong(5, reservation.getReservation_id());

			long result = statement.executeUpdate();
			return result;
		} catch (SQLException e){
			throw new DaoException("Erreur lors de l'update de la réservation : " + e.getMessage());
		}
	}
	
	
	public Optional<Reservation> findById(long id) throws DaoException {
		Reservation reservation = new Reservation();
		
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_RESERVATION_QUERY);){

			statement.setLong(1, id);

			ResultSet resultSet = statement.executeQuery();

			if(resultSet.next()) {
				reservation.setReservation_id((long)id);
				reservation.setClient_id(resultSet.getLong(2));
				reservation.setVehicle_id(resultSet.getLong(3));
				reservation.setDebut(resultSet.getDate(4));
				reservation.setFin(resultSet.getDate(5));
			}
			Optional<Reservation> optReservation = Optional.of(reservation);
			return optReservation;
						

		} catch (SQLException e){
			System.out.println("Erreur pour trouver un client : " + e.getMessage());
			return Optional.empty();
		}	
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservationList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);){

			statement.setLong(1, clientId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()){
				Reservation reservation = new Reservation();
				reservation.setReservation_id(resultSet.getLong(1));
				reservation.setClient_id((long) clientId);
				reservation.setVehicle_id(resultSet.getLong(3));
				reservation.setDebut(resultSet.getDate(4));
				reservation.setFin(resultSet.getDate(5));

				reservationList.add(reservation);
			}
			return reservationList;

		} catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche des réservations par client : " + e.getMessage());
		}		
	}
	
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);){

			statement.setLong(1, vehicleId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()){
				Reservation reservation = new Reservation();
				reservation.setVehicle_id((int)vehicleId);
				reservation.setReservation_id(resultSet.getInt(1));
				reservation.setClient_id(resultSet.getInt(2));
				reservation.setDebut(resultSet.getDate(3));
				reservation.setFin(resultSet.getDate(4));

				resultList.add(reservation);
			}
			return resultList; //---------------------------------------------------------------------------------------------mieux de le mettre dans le try ou en dehors ?

		} catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche des réservations par véhicule : " + e.getMessage());
		}
		
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_QUERY);){

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()){
				Reservation reservation = new Reservation();
				reservation.setReservation_id(resultSet.getLong(1));
				reservation.setClient_id(resultSet.getLong(2));
				reservation.setVehicle_id(resultSet.getLong(3));
				reservation.setDebut(resultSet.getDate(4));
				reservation.setFin(resultSet.getDate(5));

				resultList.add(reservation);
			}

		} catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche des réservations : " + e.getMessage());
		}
		return resultList;
	}
	
	
}
