package controller;

import dao.VideosDAO;
import dao.CustomerDAO;
import dao.StaffDAO;
import model.Videos;
import model.Customer;
import model.LoginException;
import model.Staff;

import java.util.List;

public class SystemController implements ControllerInterface {
	private static SystemController INSTANCE = new SystemController();

	private SystemController() {

	}

	public static SystemController getInstance() {
		return INSTANCE;
	}

	public static Auth currentAuth = null;

	@Override
	public void login(String username, String password) throws LoginException {
		currentAuth = StaffDAO.getInstance().login(username, password);
		if (currentAuth == Auth.INVALID) {
			throw new LoginException("Fail to login!");
		}
	}
	@Override
	public void logout(){
		currentAuth = null;
	}
//
	@Override
	public List<Staff> getAllStaff() {
		return StaffDAO.getInstance().getAllStaff();
	}

	@Override
	public List<Customer> getAllCustomer() {
		return CustomerDAO.getInstance().getAllCustomers();
	}

	@Override
	public List<Videos> getAllVideos() {
		return VideosDAO.getInstance().getAllVideos();
	}

	@Override
	public boolean updateStaff(Staff staff) {
		return StaffDAO.getInstance().updateWithoutPassword(staff);
	}

	@Override
	public boolean deleteStaff(long id) {
		return StaffDAO.getInstance().delete(id);
	}

	@Override
	public boolean addStaff(Staff staff) {
		return StaffDAO.getInstance().register(staff);
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		return CustomerDAO.getInstance().update(customer);
	}

	@Override
	public boolean deleteCustomer(long id) {
		return CustomerDAO.getInstance().delete(id);
	}

	@Override
	public boolean addCustomer(Customer customer) {
		return CustomerDAO.getInstance().create(customer);
	}

	@Override
	public boolean updateVideo(Videos vdo) {
		return VideosDAO.getInstance().update(vdo);
	}

	@Override
	public boolean deleteVideo(int id) {
		return VideosDAO.getInstance().delete(id);
	}

	@Override
	public boolean addVideo(Videos vdo) {
		return VideosDAO.getInstance().create(vdo);
	}

}
