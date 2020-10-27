package com.epf.RentManager.vehicleServlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.VehicleService;

@WebServlet("/cars/modify")
public class ModifVehicleServlet extends HttpServlet {

	VehicleService vehicleService = VehicleService.getInstance(false);
	int id = 0;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/modify.jsp");
		id = Integer.parseInt(request.getParameter("id"));
		
		try {
			Vehicle vehicle = new Vehicle();
			vehicle = vehicleService.findById(id);
			request.setAttribute("manufacturer", vehicle.getConstructeur());
			request.setAttribute("modele", vehicle.getModele());
			request.setAttribute("seats", vehicle.getNb_places());
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String constructeur = request.getParameter("manufacturer");
		String modele = request.getParameter("modele");
		int nb_places = Integer.parseInt(request.getParameter("seats"));
		
		Vehicle newVehicle = new Vehicle();
		newVehicle.setId_Vehicle(id);
		newVehicle.setConstructeur(constructeur);
		newVehicle.setModele(modele);
		newVehicle.setNb_places(nb_places);
		
		
		RequestDispatcher dispatcher = null;
		
		try {
			vehicleService.update(newVehicle);
			response.sendRedirect(request.getContextPath() + "/cars");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "erreur :" + e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/cars/modify.jsp");
			dispatcher.forward(request, response);
			//response.sendRedirect(request.getContextPath() + "/users/create");
		}
	}
}
