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

@WebServlet("/users/delete")
public class DeleteClientServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance(false);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/list.jsp");
		
		Client deleteClient = new Client();
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			deleteClient = clientService.findById(id);
			clientService.delete(deleteClient);
			response.sendRedirect(request.getContextPath() + "/users");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/list.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}

}
