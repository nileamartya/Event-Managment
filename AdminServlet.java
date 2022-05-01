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

import com.eventmanagement.dao.AdminImpl;
import com.eventmanagement.dao.AdminIntf;
import com.eventmanagement.dao.UserIntf;
import com.eventmanagement.dao.Userimpl;
import com.eventmanagement.pojo.VenuePojo;
import com.eventmanagement.pojo.booking;
import com.eventmanagement.utility.EmailUility;
import com.google.gson.Gson;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
		AdminIntf intf = new AdminImpl();
		
		
		if(action!=null && action.equalsIgnoreCase("getType"))
		{
			System.out.println("inside get type");
			List<String> list = new ArrayList<String>();
			list = intf.getType();
			new Gson().toJson(list, response.getWriter());
		}
		
		else if(action!=null && action.equalsIgnoreCase("addVenue"))
		{
			System.out.println("inside venue");
			
			String name = request.getParameter("name");
			String add = request.getParameter("add");
			
			int capacity = Integer.parseInt(request.getParameter("capacity"));
			double cost = Double.parseDouble(request.getParameter("cost"));
			VenuePojo  v = new VenuePojo();
			
			v.setName(name);
			v.setAdd(add);
			
			v.setCapacity(capacity);
			v.setCost(cost);
			
			
			intf.addVenue(v);
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("costGeneration"))
		{
			System.out.println("cost");
			int id = (Integer)session.getAttribute("uid");
			String venue = request.getParameter("bid");
			double cost = Double.parseDouble(request.getParameter("cost"));
			int guest = Integer.parseInt(request.getParameter("guest"));
			String date = request.getParameter("date");
			double extratotal=0.0;
			if(request.getParameterValues("extra[]")!=null)
			{
				String extra[] = request.getParameterValues("extra[]");
				System.out.println("extra"+extra.length);
				for(int i =0; i<extra.length;i++)
				{
					String ext = extra[i];
					double exttotal = intf.getCost(ext);
					extratotal = extratotal+exttotal;
					
					
				}
				System.out.println("extratotal"+extratotal);
			}
			String food = request.getParameter("food");
			String foodtime[] = request.getParameterValues("foodtime[]");
			double totalcost = 0.0;
			String  lunchtype="";
			String dinnertype="";
			double decocost =0.0;
			double themecost = 0.0;
			if(request.getParameter("lunchtype")!=null)
			{
	lunchtype = request.getParameter("lunchtype");
			}
			if(request.getParameter("dinnertype")!=null)
			{
				dinnertype = request.getParameter("dinnertype");
			}
			
			String deco = "";
			String theme="";
					if(request.getParameter("deco")!=null);
					{
						deco = request.getParameter("deco");
						decocost = intf.getDecoCost(deco);
						
					}
			if(request.getParameter("theme")!=null)
			{
				theme = request.getParameter("theme");
				themecost = 2000;
			}
			
			
			double mealtotal =0.0;
			List<Double> list = new ArrayList<Double>();
			
			System.out.println("food"+food);

			System.out.println();
			
			
			
			for(int i =0; i<foodtime.length; i++)
			{
				String foodtype = food;
				String foodTime = foodtime[i];
				if(foodTime.equalsIgnoreCase("Lunch"))
				{
				String type = lunchtype;
				double mtotal =intf.getFoodtype(foodtype,foodTime, type);
				mealtotal = mtotal +mealtotal;
				System.out.println("mealtotal"+mealtotal);
				
				}
			else if(foodTime.equalsIgnoreCase("Dinner"))
					{
					String type = lunchtype;
					double mtotal =intf.getFoodtype(foodtype,foodTime, type);
					mealtotal = mtotal +mealtotal;
					System.out.println("mealtotal"+mealtotal);
					
			
			}
			else
			{
				double mtotal= intf.getMealCost(food, foodTime);
				mealtotal = mtotal +mealtotal;
				System.out.println("mealtotal"+mealtotal);
				
			}
			
		}
			
			System.out.println("mealtotal"+mealtotal);
			System.out.println("deco"+decocost);
			System.out.println("theme"+themecost);
			double meal = mealtotal*guest;
			
			totalcost = extratotal+meal+decocost+themecost+cost;
			System.out.println("toal"+totalcost);
			intf.addUserBooking(id, date, venue, extratotal, meal, decocost, themecost, totalcost, "pending");
	        session.setAttribute("total", totalcost);
	        PrintWriter out = response.getWriter();
	        out.write(String.valueOf(totalcost));
		}
		
		else if(action!=null && action.equalsIgnoreCase("getApproval"))
		{
			System.out.println("inside get");
			List<booking> list = new ArrayList<booking>();
			
			list = intf.getRequest();
			System.out.println("list"+list.size());
			session.setAttribute("list", list);
			
			response.sendRedirect("approve.jsp");
			
			
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("approve"))
		{
			System.out.println("approve");
			
			int id = Integer.parseInt(request.getParameter("id"));
			String requestapp = "approve";
			booking b = new booking();
			
		
			System.out.println("b"+b.getUid());
			intf.updateRequest(id, requestapp);
			b = intf.getBooking(id);
			List<booking> list = new ArrayList<booking>();
			int uid = b.getUid();
			System.out.println("id"+id);
			UserIntf uintf = new Userimpl();
			String email = uintf.getEmail(uid);
			System.out.println("email"+email);
			EmailUility.sendEmail(email, "Approval", "Your event for date:"+b.getDate()+ "has been approved, Concerned person will contact you soon!!!");
			list = intf.getRequest();
			System.out.println("list"+list.size());
			session.setAttribute("list", list);
		    intf.addAdmin(id, b.getDate(), b.getVenue());
			
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("view"))
		{
			System.out.println("view"); 
			List<booking> list = new ArrayList<booking>();
			list = intf.getList();
			response.sendRedirect("view.jsp");
			
			
			
		}
		
		else if(action!=null && action.equalsIgnoreCase("getAdminTime"))
		{
			String date = request.getParameter("date");
			boolean res = intf.checkAdmin(date);
			System.out.println("res"+res);
			if(res)
				
			{
				
			}
			else
			{
			PrintWriter out = response.getWriter();
			out.write("false");
			}
		}

}
}
