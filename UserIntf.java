package com.eventmanagement.dao;

import java.util.List;

import com.eventmanagement.pojo.User;
import com.eventmanagement.pojo.VenuePojo;
import com.eventmanagement.pojo.booking;

public interface UserIntf {

	public boolean reg(User u);
	public int checkLogin(String email, String pass);
	public List<VenuePojo>getVenues();
	public VenuePojo getVenueDetails(int id);
	public boolean checkUser(String email);
	public boolean addOtp(int uid, String otp);
	public String getEmail(int uid);
	public String getOtp(int uid);
	public List<booking> getList(int uid);
	public booking  getInvoice(int uid);
	public User getUser(int uid);
}
