package com.eventmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.eventmanagement.pojo.VenuePojo;
import com.eventmanagement.pojo.booking;
import com.eventmanagement.utility.MyConnection;

public class AdminImpl implements AdminIntf{
	
	
	Connection conn =  null;
	PreparedStatement ps = null;

	public boolean addVenue(VenuePojo v) {
		
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("insert into tbl_venue ( capacity, cost, name, address) values(?,?,?,?)");
			
			
			ps.setInt(1, v.getCapacity());
			ps.setDouble(2, v.getCost());
			ps.setString(3, v.getName());
			ps.setString(4, v.getAdd());
			
			ps.executeUpdate();
			
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getType() {
		
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_type");
			List<String> list = new ArrayList<String>();
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
			list.add(rs.getString("type"));
			}
			conn.close();
			return list;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	public double getCost(String extra) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select cost from tbl_extra where name=?");
			ps.setString(1, extra);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
			double cost = rs.getDouble("cost");
			return cost;
			}
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public double getFoodtype(String foodtype, String time, String type) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select cost from tbl_meal where type=? and timing=? and subtype=?");
			ps.setString(1, foodtype);
			ps.setString(2, time);
			ps.setString(3, type);
			ResultSet rs =ps.executeQuery();
			while(rs.next())
			{
				double cost = rs.getDouble("cost");
				return cost;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public double getMealCost(String foodtype, String time) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select cost from tbl_meal where type=? and timing=?");
			ps.setString(1, foodtype);
			ps.setString(2, time);
			
			ResultSet rs =ps.executeQuery();
			while(rs.next())
			{
				double cost = rs.getDouble("cost");
				return cost;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public double getDecoCost(String deco) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select cost from tbl_deco where type=?");
			ps.setString(1, deco);
			
			
			ResultSet rs =ps.executeQuery();
			while(rs.next())
			{
				double cost = rs.getDouble("cost");
				return cost;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public boolean addUserBooking(int uid, String date, String venue,
			double extra, double mealcost, double decocost, double themecost,
			double total, String request) {
		
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("insert into tbl_user_booking(uid, date, venue, extracost, mealcost, decocost, theme, total, request, payment) values(?,?,?,?,?,?,?,?,?,?)");
		    ps.setInt(1, uid);
		    ps.setString(2, date);
		    ps.setString(3, venue);
		    ps.setDouble(4, extra);
		    ps.setDouble(5, mealcost);
		    ps.setDouble(6, decocost);
		    ps.setDouble(7, themecost);
		    ps.setDouble(8, total);
		    ps.setString(9, request);
		    ps.setString(10, "pending");
		ps.executeUpdate();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return false;
	}

	public List<booking> getRequest() {
		// TODO Auto-generated method stub
		try
		{
			List<booking> list =new ArrayList<booking>();
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_user_booking where request!=?");
	       ps.setString(1, "approve");
	       ResultSet rs = ps.executeQuery();
	       while(rs.next())
	       {
	    	   booking b = new booking();
	    	  b.setVenue(rs.getString("venue"));
	    	  b.setDate(rs.getString("date"));
	    	  b.setUid(rs.getInt("uid"));
	    	  b.setTotal(rs.getDouble("total"));
	    	  b.setId(rs.getInt("id"));
	    	  list.add(b);
	    	  
	    	  
	       }
	       return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateRequest(int id, String request) {
		try
		{
			conn =MyConnection.getConnectionObj();
			ps = conn.prepareStatement("update tbl_user_booking set request=? where id=?");
			ps.setString(1, request);
			ps.setInt(2, id);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public List<booking> getList() {
		try
		{
			List<booking> list =new ArrayList<booking>();
			conn = MyConnection.getConnectionObj();
			ps= conn.prepareStatement("select * from tbl_user_booking where request=? and payment=?");
		    ps.setString(1, "approve");
		    ps.setString(2, "done");
		    
		    ResultSet rs = ps.executeQuery();
		    while(rs.next())
		    {
		    	 booking b = new booking();
		    	  b.setVenue(rs.getString("venue"));
		    	  b.setDate(rs.getString("date"));
		    	  b.setUid(rs.getInt("uid"));
		    	  b.setTotal(rs.getDouble("total"));
		    	  b.setId(rs.getInt("id"));
		    	  list.add(b);
		    	
		    }
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean addAdmin(int id, String date, String venue) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("insert into tbl_admin_booking(bid, date, venue) values(?,?,?)");
		    ps.setInt(1, id);
		    ps.setString(2, date);
		    ps.setString(3, venue);
		   
		  
		ps.executeUpdate();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		return false;
	}

	

	public booking getBooking(int id) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_user_booking where id=? and request=?");
			ps.setInt(1, id);
			ps.setString(2, "approve");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				booking b = new booking();
				b.setDate(rs.getString("date"));
				b.setVenue(rs.getString("venue"));
				b.setUid(rs.getInt("uid"));
				return b;
			}
			conn.close();
	
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean checkAdmin(String date) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_admin_booking where date=?");
			
			ps.setString(1, "date");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				
				return true;
			}
			conn.close();
	
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	
	
	
}
