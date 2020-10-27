package com.epf.RentManager.controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;


/**
 * 
 *
 * Servlet implementation class HomeServlet
 *
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance(false);
	VehicleService vehicleService = VehicleService.getInstance(false);
	ReservationService reservationService = ReservationService.getInstance(false);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		try {
			request.setAttribute("nbUtilisateurs", clientService.findAll().size());
			request.setAttribute("nbVehicules", vehicleService.findAll().size());
			request.setAttribute("nbReservations", reservationService.findAll().size());
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
