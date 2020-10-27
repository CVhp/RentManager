package com.epf.RentManager.clientServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;


/**
 * 
 * @author cecile
 * Servlet implementation class HomeServlet
 *
 */
@WebServlet("/users/details")
public class DetailsUserServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance(false);
	VehicleService vehicleService = VehicleService.getInstance(false);
	ReservationService reservationService = ReservationService.getInstance(false);
	
			
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp"); 
		
		Client client = new Client();
		try {			
			int id = Integer.parseInt(request.getParameter("id"));
			
			client = clientService.findById(id);
			
									
			request.setAttribute("nbReservations", reservationService.findByIdClient((long)id).size());												
			request.setAttribute("nbVehicules", client.getListVehicle(client).size());
			
			request.setAttribute("rents", reservationService.findByIdClient((long)id));
			request.setAttribute("vehicles", client.getListVehicle(client));
			
			request.setAttribute("client", client);
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
