package com.eventmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.eventmanagement.pojo.User;
import com.eventmanagement.pojo.VenuePojo;
import com.eventmanagement.pojo.booking;
import com.eventmanagement.utility.MyConnection;

public class Userimpl implements UserIntf{

	
	Connection conn =  null;
	PreparedStatement ps = null;
	public boolean reg(User u) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("insert into tbl_user (name, email, mob, password, confirm) values(?,?,?,PASSWORD('?'),PASSWORD('?'))");
		
	      ps.setString(1, u.getName());
	      ps.setString(2, u.getEmail());
	      ps.setString(3, u.getMob());
	      ps.setString(4, u.getPass());
	      ps.setString(5, u.getConfirm());
	      ps.executeUpdate();
	      conn.close();
		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		return false;
	}
	public int checkLogin(String email, String pass) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select id from tbl_user where email=? and password=PASSWORD('?')");
		
	      ps.setString(1, email);
	      ps.setString(2, pass);
	      
	      ResultSet rs = ps.executeQuery();
	    while(rs.next())
	    {
	    	int id = rs.getInt("id");
	    	return id;
	    }
	      conn.close();
		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	public List<VenuePojo> getVenues() {
		try
		{
			List<VenuePojo> list = new ArrayList<VenuePojo>();
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_venue");
		
	      
	      ResultSet rs = ps.executeQuery();
	    while(rs.next())
	    { 
	    	VenuePojo v= new VenuePojo();
	    	v.setId(rs.getInt("id"));
	    	v.setName(rs.getString("name"));
	    	v.setCapacity(rs.getInt("capacity"));
	    	v.setAdd(rs.getString("address"));
	    	v.setCost(rs.getDouble("cost"));
	    	
	    	 list.add(v);
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
	public VenuePojo getVenueDetails(int id) {
		try
		{

			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_venue where id=?");
		ps.setInt(1, id);
	      
	      ResultSet rs = ps.executeQuery();
	    while(rs.next())
	    { 
	    	VenuePojo v= new VenuePojo();
	
	    	v.setName(rs.getString("name"));
	    	v.setCapacity(rs.getInt("capacity"));
	    	v.setAdd(rs.getString("address"));
	    	v.setCost(rs.getDouble("cost"));
	    	v.setId(rs.getInt("id"));
	    	
	    	return v;
	    }
	      conn.close();
	      
		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
		return null;
	}
	public boolean checkUser(String email) {
		try
		{

			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_user where email=?");
		ps.setString(1, email);
	      
	      ResultSet rs = ps.executeQuery();
	    while(rs.next())
	    { 
	    	return true;
	    }
	      conn.close();
		return false;
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;

}
	public boolean addOtp(int uid, String otp) {
		try
		{
			
			
				conn  = MyConnection.getConnectionObj();
				ps = conn.prepareStatement("insert into tbl_otp (uid, otp) values(?,?)");
				ps.setInt(1, uid);
				ps.setString(2, otp);
				
				ps.executeUpdate();
			
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String getEmail(int uid) {
		try
		{
		conn = MyConnection.getConnectionObj();
		ps =conn.prepareStatement("select email from tbl_user where id=?");
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
		String email =	rs.getString("email");
		return email;
		}
		
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	public String getOtp(int uid) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select otp  from tbl_otp where uid=?  order by  id desc limit 1");
		   ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			String otp  = rs.getString("otp");
			return otp;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	public List<booking> getList(int uid) {
		try
		{List<booking> list = new ArrayList<booking>();
			
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_user_booking where uid=?");
			
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				booking b = new booking();
		    	  b.setVenue(rs.getString("venue"));
		    	  b.setDate(rs.getString("date"));
		    	  b.setUid(rs.getInt("uid"));
		    	  b.setTotal(rs.getDouble("total"));
		    	  b.setId(rs.getInt("id"));
		    	  b.setRequest(rs.getString("request"));
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
	public booking getInvoice(int uid) {
	
		try
		{
			
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_user_booking where uid=? order by id  desc limit 1");
			
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				booking b = new booking();
		    	  b.setVenue(rs.getString("venue"));
		    	  b.setDate(rs.getString("date"));
		    	  b.setUid(rs.getInt("uid"));
		    	  b.setTotal(rs.getDouble("total"));
		    	  b.setId(rs.getInt("id"));
		    	 
		    	return b;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		
		
	}
	public User getUser(int uid) {
		try
		{
			conn = MyConnection.getConnectionObj();
			ps = conn.prepareStatement("select * from tbl_user where id=?");
		
	      ps.setInt(1, uid);
	     
	      
	      ResultSet rs = ps.executeQuery();
	    while(rs.next())
	    {
	    	String mob = rs.getString("mob");
	    	String name= rs.getString("name");
	    	User u = new User();
	    	
	    	
	    }
	      conn.close();
		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	
		return null;
	}
}
