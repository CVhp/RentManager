package com.epf.RentManager.dao;

import com.epf.RentManager.dao.ClientDao;
import com.epf.RentManager.exception.DaoException;
import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.persistence.ConnectionManager;
import com.epf.RentManager.service.ReservationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDao {
	
	private static ClientDao instance = null;
	private static ClientDao instanceTest = null;
	private boolean test;
	
	private ClientDao(){}
	
	private ClientDao(boolean test) {
		this.test = test;
	}
	
	public static ClientDao getInstance(boolean test) {
		if(test){
			if(instanceTest == null) {
				instanceTest = new ClientDao(true);
			}
			return instanceTest;
		}else {
			if (instance == null) {
				instance = new ClientDao(false);
			}
			return instance;
		}
	}
	
	// à garder ? ----------------------------------------------------------------------------------------------------------------------------------------------
	/*public static ClientDao getInstance() {
		return getInstance(false);
	}*/
	// à garder ? ----------------------------------------------------------------------------------------------------------------------------------------------
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	
	public long create(Client client) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);){ 

			statement.setString(1, client.getNom().toUpperCase());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail());
			statement.setDate(4, client.getNaissance());

			long result = statement.executeUpdate();
			return result;
			
		} catch (SQLException e){
			throw new DaoException("Erreur lors de la création du client : " + e.getMessage());
		}
	}
	
	public long delete(Client client) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(DELETE_CLIENT_QUERY);){

			statement.setLong(1, client.getId());
			
			long result = statement.executeUpdate();
				
			
			
			return result;

		} catch (SQLException e){
			throw new DaoException("Erreur lors de la suppression du client : " + e.getMessage());
		}
	}
	
	
	public long update(Client client) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(UPDATE_CLIENT_QUERY);){ 

			statement.setString(1, client.getNom().toUpperCase());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail());
			statement.setDate(4, client.getNaissance());
			
			statement.setLong(5, client.getId());
			
			long result = statement.executeUpdate();
			return result;
			
		} catch (SQLException e){
			throw new DaoException("Erreur lors de l'update du client : " + e.getMessage());
		}
	}
	
	

	public Optional<Client> findById(long id) throws DaoException {
		Client client = new Client();
		
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_CLIENT_QUERY);){

			statement.setLong(1, id);

			ResultSet resultSet = statement.executeQuery();

			if(resultSet.next()) {
				client.setId((long)id);
				client.setNom(resultSet.getString(1));
				client.setPrenom(resultSet.getString(2));
				client.setEmail(resultSet.getString(3));
				client.setNaissance(resultSet.getDate(4));
			}
			Optional<Client> optClient = Optional.of(client);
			return optClient;
						

		} catch (SQLException e){
			System.out.println("Erreur pour trouver un client : " + e.getMessage());
			return Optional.empty();
		}	
	}

	
	/**
	 * Récupère tous les clients de la base
	 * @return liste de clients
	 * @throws DaoException si une SQLException est levée durant l'éxecution
	 */
	public List<Client> findAll() throws DaoException {
		List<Client> resultList = new ArrayList<>();
		try (Connection conn = test ? ConnectionManager.getConnectionForTest() : ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_CLIENTS_QUERY);){
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()){
				Client client = new Client();
				client.setId(resultSet.getInt(1));
				client.setNom(resultSet.getString(2));
				client.setPrenom(resultSet.getString(3));
				client.setEmail(resultSet.getString(4));
				client.setNaissance(resultSet.getDate(5));

				resultList.add(client);
			}
			return resultList;
		} catch (SQLException e){
			throw new DaoException("Erreur lors de la récupération de la recherche des clients : " + e.getMessage());
		}
	}

	
	/**
	 * Affiche la liste donnée en paramètre dans la console
	 * @param liste de clients
	 */
	public static void main(String... args) {
		ClientDao dao = ClientDao.getInstance(false); //ClientDao dao = ClientDao.getInstance(); //creation d'un client dao 

		try{
			List<Client> list = dao.findAll();	
			for (Client c : list){
				System.out.println(c.toString());
			}
		} catch (DaoException e){
			System.out.println("Erreur lors du select all des clients : " + e.getMessage());
		}
	}


}
