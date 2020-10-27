package com.epf.RentManager.rentServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

@WebServlet("/rents/details")
public class DetailsRentServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance(false);
	VehicleService vehicleService = VehicleService.getInstance(false);
	ReservationService reservationService = ReservationService.getInstance(false);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp"); 
		
		Reservation reservation = new Reservation();
		try {			
			
			int id = Integer.parseInt(request.getParameter("id"));			
			reservation = reservationService.findById(id);
			
			List<Vehicle> listVehicle = new ArrayList<Vehicle>();
			listVehicle.add(reservation.getVehicle());
					
			
			List<Client> listClient = new ArrayList<Client>();
			listClient.add(reservation.getClient());
			
			request.setAttribute("vehicles", listVehicle);
			request.setAttribute("users", listClient);
			
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
