package com.eventmanagement.dao;

import java.util.List;

import com.eventmanagement.pojo.VenuePojo;
import com.eventmanagement.pojo.booking;

public interface AdminIntf {
	
	public boolean addVenue(VenuePojo v);
	public List<String> getType();
	public double getCost(String extra);
	public double getFoodtype(String foodtype, String time, String type);
	public double getMealCost(String foodtype, String time);
	public double getDecoCost(String deco);
	public boolean addUserBooking(int uid, String date, String venue, double extra, double mealcost,double decocost, double themecost, double total, String request);
	public List<booking> getRequest();
	public boolean updateRequest(int id,String request);
	public List<booking> getList();
	public boolean addAdmin(int id, String date, String venue);
	public booking getBooking(int id);
	public boolean checkAdmin(String date);

}
