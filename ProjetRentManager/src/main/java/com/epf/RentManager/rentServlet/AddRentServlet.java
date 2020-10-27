package com.epf.RentManager.rentServlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

@WebServlet("/rents/create")
public class AddRentServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance(false);
	VehicleService vehicleService = VehicleService.getInstance(false);
	ClientService clientService = ClientService.getInstance(false);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
		try{
			request.setAttribute("vehicles", vehicleService.findAll());
			request.setAttribute("users", clientService.findAll());
		} catch (ServiceException e){
			request.setAttribute("errorMessage", "Une erreur est survenue");
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int client_id = Integer.parseInt(request.getParameter("client"));
		int vehicle_id = Integer.parseInt(request.getParameter("car"));
		Date debut = Date.valueOf(request.getParameter("begin"));
		Date fin = Date.valueOf(request.getParameter("end"));
		
		
		Reservation newReservation = new Reservation();
		newReservation.setClient_id(client_id);
		newReservation.setVehicle_id(vehicle_id);
		newReservation.setDebut(debut);
		newReservation.setFin(fin);
		
		RequestDispatcher dispatcher = null;

		try {
			reservationService.create(newReservation);
			response.sendRedirect(request.getContextPath() + "/rents");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
			
			//response.sendRedirect(request.getContextPath() + "/rents/create");
			
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");		
			try {
				request.setAttribute("vehicles", vehicleService.findAll());
				request.setAttribute("users", clientService.findAll());
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			dispatcher.forward(request, response);
		}
		
	}
}
