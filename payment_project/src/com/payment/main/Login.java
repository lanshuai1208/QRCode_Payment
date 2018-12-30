package com.payment.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.payment.factory.DAOFactory;
import com.payment.vo.user;

public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username"); 
		String password = request.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		user ul = new user();
		ul.setUsername(username);
		ul.setPassword(password);
		String result = "";
		boolean flag = false;
		try{
			flag = DAOFactory.getUserDAOInstance().login(ul);
			if(flag){
				result = username;
				out.println(result);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(flag); 
		
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}
}
