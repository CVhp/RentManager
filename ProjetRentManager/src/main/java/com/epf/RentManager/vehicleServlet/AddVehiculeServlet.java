package com.epf.RentManager.vehicleServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Vehicle;
import com.epf.RentManager.service.VehicleService;


/**
 * 
 * @author cecile
 * Servlet implementation class HomeServlet
 *
 */
@WebServlet("/cars/create")
public class AddVehiculeServlet extends HttpServlet{
	
	VehicleService vehicleService = VehicleService.getInstance(false);
			
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String constructeur = request.getParameter("manufacturer");
		String modele = request.getParameter("modele");
		int nb_places = Integer.parseInt(request.getParameter("seats"));
		
		Vehicle newVehicle = new Vehicle();
		newVehicle.setConstructeur(constructeur);
		newVehicle.setModele(modele);
		newVehicle.setNb_places(nb_places);
		
		
		RequestDispatcher dispatcher = null;

		try {
			vehicleService.create(newVehicle);
			response.sendRedirect(request.getContextPath() + "/cars");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
			dispatcher.forward(request, response);
			//response.sendRedirect(request.getContextPath() + "/users/create");
		}
		
	}
}
