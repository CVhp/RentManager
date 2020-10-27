package com.epf.RentManager.rentServlet;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.service.ReservationService;

@WebServlet("/rents")
public class RentListServlet extends HttpServlet {
	
	ReservationService reservationservice = ReservationService.getInstance(false);
	
	private static final long serialVersionUID=1L;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
		
		
		try{
			request.setAttribute("rents", reservationservice.findAll());
		} catch (ServiceException e){
			request.setAttribute("rents", "Une erreur est survenue");
		}
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		doGet(request,response);
	}

}
