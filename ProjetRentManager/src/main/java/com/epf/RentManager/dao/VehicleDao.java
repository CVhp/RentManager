package com.epf.RentManager.dao;


import com.epf.RentManager.exception.DaoException;
import com.epf.RentManager.model.Vehicle;
import com.epf.RentManager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private static VehicleDao instanceTest = null;
	private boolean test;
	
	private VehicleDao(boolean test) {
		this.test = test;
	}
	
	private VehicleDao() {}
	
	public static VehicleDao getInstance(boolean test) {
		if(test){
			if(instanceTest == null) {
				instanceTest = new VehicleDao(true);
			}
			return instanceTest;
		}else {
			if (instance == null) {
				instance = new VehicleDao(false);
			}
			return instance;
		}
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(CREATE_VEHICLE_QUERY);){

			statement.setString(1, vehicle.getConstructeur());
			statement.setString(2, vehicle.getModele());
			statement.setInt(3, vehicle.getNb_places());

			long result = statement.executeUpdate();
			return result;
		} catch (SQLException e){
			throw new DaoException("Erreur lors de la création d'un véhicule: " + e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(DELETE_VEHICLE_QUERY);){

			statement.setLong(1, vehicle.getId_Vehicle());
			long result = statement.executeUpdate();

			return result;

		} catch (SQLException e){
			throw new DaoException("Erreur lors de la suppression d'un véhicule: " + e.getMessage());
		}
	}
	
	public long update(Vehicle vehicle) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(UPDATE_VEHICLE_QUERY);){

			statement.setString(1, vehicle.getConstructeur());
			statement.setString(2, vehicle.getModele());
			statement.setInt(3, vehicle.getNb_places());
			
			statement.setLong(4, vehicle.getId_Vehicle());

			long result = statement.executeUpdate();
			return result;
		} catch (SQLException e){
			throw new DaoException("Erreur lors de l'update d'un véhicule: " + e.getMessage());
		}
	}
	
	
	

	public Optional<Vehicle> findById(long id) throws DaoException {
		Vehicle vehicule = new Vehicle();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_VEHICLE_QUERY);){

			statement.setLong(1, id);

			ResultSet resultSet = statement.executeQuery();

			if(resultSet.next()) {
			vehicule.setId_Vehicle(resultSet.getInt(1));
			vehicule.setConstructeur(resultSet.getString(2));
			vehicule.setModele(resultSet.getString(3));
			vehicule.setNb_places(resultSet.getInt(4));
			}
			Optional<Vehicle> optVehicle = Optional.of(vehicule);
			return optVehicle;

		} catch (SQLException e){
			System.out.println("Erreur pour trouver un véhicule: " + e.getMessage());
			return Optional.empty();
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_VEHICLES_QUERY);){

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()){
				Vehicle vehicule = new Vehicle();
				vehicule.setId_Vehicle(resultSet.getInt(1));
				vehicule.setConstructeur(resultSet.getString(2));
				vehicule.setModele(resultSet.getString(3));
				vehicule.setNb_places(resultSet.getInt(4));

				resultList.add(vehicule);
			}
			return resultList;
		} catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche de la liste des clients : " + e.getMessage());
		}
	}
	
	public static void main (String... args) {
		VehicleDao dao = VehicleDao.getInstance(false);
		try {
			List<Vehicle> list = dao.findAll();
			for (Vehicle c : list) {  //== (int i=0; i<list.size();i++)
				System.out.println(c);
			}
		} catch (DaoException e) {
			System.out.println("Erreur lors du Select All" + e.getMessage());
		}
		

	}
	

}
