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

@WebServlet("/searchBalance")
public class searchBalance extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("into searchBalance");
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String userid = request.getParameter("userid");
		System.out.println(userid);
		user ul = new user();
		ul.setUsername(userid);
		double result = 0;
		try{
			result = DAOFactory.getUserDAOInstance().searchBalance(ul);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("result:" + result);
		out.println(result);
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

}
