package com.epf.RentManager.clientServlet;

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
import com.epf.RentManager.service.ClientService;

@WebServlet("/users/modify")
public class ModifClientServlet extends HttpServlet{
	ClientService clientService = ClientService.getInstance(false);
	int id = 0;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/modify.jsp");
		id = Integer.parseInt(request.getParameter("id"));
		
		try {
			Client client = new Client();
			client = clientService.findById(id);
			request.setAttribute("last_name", client.getNom());
			request.setAttribute("first_name", client.getPrenom());
			request.setAttribute("email", client.getEmail());
			request.setAttribute("birthdate", client.getNaissance());
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String last_name = request.getParameter("last_name");
		String first_name = request.getParameter("first_name");
		String email = request.getParameter("email");
		Date birthdate = Date.valueOf(request.getParameter("birthdate"));
		
		Client newClient = new Client();
		newClient.setId(id);
		newClient.setNom(last_name);
		newClient.setPrenom(first_name);
		newClient.setEmail(email);
		newClient.setNaissance(birthdate);
		
		RequestDispatcher dispatcher = null;

		try {
			clientService.update(newClient);
			response.sendRedirect(request.getContextPath() + "/users");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/modify.jsp");
			dispatcher.forward(request, response);
			//response.sendRedirect(request.getContextPath() + "/users/create");
		}
		
	}
}
