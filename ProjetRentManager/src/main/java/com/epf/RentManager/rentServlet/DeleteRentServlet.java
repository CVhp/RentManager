package com.epf.RentManager.rentServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.service.ReservationService;

@WebServlet("/rents/delete")
public class DeleteRentServlet extends HttpServlet{
	ReservationService reservationService = ReservationService.getInstance(false);
	
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
		
		Reservation deleteReservation = new Reservation();
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			deleteReservation = reservationService.findById(id);
			reservationService.delete(deleteReservation);
			response.sendRedirect(request.getContextPath() + "/rents");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	
}
