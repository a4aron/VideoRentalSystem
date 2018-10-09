package controller;

import model.Videos;
import model.Customer;
import model.LoginException;
import model.Staff;
//
//import java.awt.print.Book;
//import java.util.HashMap;
import java.util.List;


public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	void logout();
	// Staff
	List<Staff> getAllStaff();
	boolean updateStaff(Staff staff);
	boolean deleteStaff(long id);
	boolean addStaff(Staff staff);

	List<Customer> getAllCustomer();
	boolean updateCustomer(Customer customer);
	boolean deleteCustomer(long id);
	boolean addCustomer(Customer customer);

	List<Videos> getAllVideos();
	boolean updateVideo(Videos car);
	boolean deleteVideo(int id);
	boolean addVideo(Videos car);


}
