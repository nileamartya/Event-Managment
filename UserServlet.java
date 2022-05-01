package com.eventmanagement.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eventmanagement.dao.UserIntf;
import com.eventmanagement.dao.Userimpl;
import com.eventmanagement.pojo.User;
import com.eventmanagement.pojo.VenuePojo;
import com.eventmanagement.pojo.booking;
import com.eventmanagement.utility.EmailUility;
import com.eventmanagement.utility.RandomOTP;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String action= request.getParameter("action");
		System.out.println("action:"+action);
		HttpSession session = request.getSession();
		UserIntf intf =new Userimpl();
		
		if(action!=null && action.equalsIgnoreCase("submit"))
		{
			System.out.println("inside submit");
			
			String name = request.getParameter("username");
			String phone  = request.getParameter("mob");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String cnfpass = request.getParameter("cnfpass");
			
			User reg = new User();
			reg.setName(name);
			reg.setMob(phone);
			reg.setEmail(email);
			reg.setPass(password);
			reg.setConfirm(cnfpass);
			
			intf.reg(reg);
			
			response.sendRedirect("Login1.jsp");
			
			
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("Login"))
		{
                System.out.println("inside login");
			
			String email = request.getParameter("email");
			String pass  = request.getParameter("password");
			int uid = intf.checkLogin(email, pass);
			if(uid>0)
			{
				session.setAttribute("uid", uid);
				
				
				response.sendRedirect("bookevent.jsp");
			}
			
			else
			{
				System.out.println("nhh");
				
				if(email.equalsIgnoreCase("admin@gmail.com") || pass.equalsIgnoreCase("Admin@123"))
				{
					
					System.out.println("nhh");
					response.sendRedirect("addvenue.jsp");
				}
				
				else
				{
					
					session.setAttribute("msg", "Invalid email or password");
					response.sendRedirect("Login1.jsp");
				}
			}
			
			
			
			
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("getVenue"))
		{
			 System.out.println("inside get venue");
			 List<VenuePojo> list = new ArrayList<VenuePojo>();
		   list =	 intf.getVenues();
			 System.out.println("list"+list.size());
			session.setAttribute("venuelist", list); 
			session.setAttribute("flag", "flag");
			response.sendRedirect("bookevent.jsp");
			
			 
		}
		
		else if(action!=null && action.equalsIgnoreCase("venueDetails"))
		{
			System.out.println("venueDetails");
			int id = Integer.parseInt(request.getParameter("id"));
			VenuePojo v = new VenuePojo();
			v = intf.getVenueDetails(id);
			session.setAttribute("venue", v);
			response.sendRedirect("details.jsp");
			
			
			
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("checkUser"))
		{
		String email = request.getParameter("email");
		PrintWriter out =  response.getWriter();
		boolean res =intf.checkUser(email);
		if(res)
		{
			out.write("true");
		}
		}
		
		else if(action!=null && action.equalsIgnoreCase("Add"))
		{
			System.out.println("add");
			
			
			String otp = 	RandomOTP.getAlphaNumbericRandom(6);
			System.out.println("otp is"+otp);
			int uid=(Integer)session.getAttribute("uid");
			intf.addOtp(uid, otp);
			String email = intf.getEmail(uid);
			
			System.out.println("mail"+email);
			EmailUility.sendEmail(email, "booking", otp);
			response.sendRedirect("otp.jsp");
				
		}
		else if(action!=null && action.equalsIgnoreCase("check"))
		{
			String otp = request.getParameter("otp");
			int uid = (Integer)session.getAttribute("uid");
			String otpp = intf.getOtp(uid);
			
			if(otp.equals(otpp))
			{
				String payment = "done";
			
				
				
				response.sendRedirect("UserServlet?action=invoice");
			}
				
		}
		
		else if(action!= null && action.equalsIgnoreCase("booked"))
		{
			System.out.println("booked");
			List<booking>  list = new ArrayList<booking>();
			int uid=(Integer)session.getAttribute("uid");
			list = intf.getList(uid);
			session.setAttribute("booklist", list);
			response.sendRedirect("booking.jsp");
			
		}
		
		else if(action!= null && action.equalsIgnoreCase("invoice"))
		{
			int uid=(Integer)session.getAttribute("uid");
			booking b = new booking();
			b = intf.getInvoice(uid);
			session.setAttribute("invoice", b);
			response.sendRedirect("invoice.jsp");
		}
	
	}
}
