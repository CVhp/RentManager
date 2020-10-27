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

@WebServlet("/cars/delete")
public class DeleteVehicleServlet extends HttpServlet{
	VehicleService vehicleService = VehicleService.getInstance(false);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cars/list.jsp");
		
		Vehicle deleteVehicle = new Vehicle();
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			deleteVehicle = vehicleService.findById(id);
			vehicleService.delete(deleteVehicle);
			response.sendRedirect(request.getContextPath() + "/cars");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/cars/list.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
