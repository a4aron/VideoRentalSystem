package dao;

import dbconnection.DatabaseException;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DAO{
    // singleton + lazy init
    private static CustomerDAO instance;
    public static CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }

    private CustomerDAO() {
        super();
    }

    public boolean create(Customer customer) {
        return databaseConnection.insert(
                "INSERT INTO `customer`(`first_name`, `last_name`, `phone`, `address`) " +
                        "VALUES (?, ?, ?, ?)",
                new Object[] {customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getAddress()}
        ).size() == 1;
    }

    public boolean update(Customer customer) {
        return databaseConnection.update(
                "UPDATE `customer` SET `first_name`=?,`last_name`=?,`phone`=?,`address`=? WHERE `id` = ?",
                new Object[] {
                        customer.getFirstName(), customer.getLastName(),
                        customer.getPhone(), customer.getAddress(), customer.getId()
                }
        ) == 1;
    }

    public boolean delete(long id) {
        return databaseConnection.delete(
                "DELETE FROM `customer` WHERE `id` = ?",
                new Object[]{id}
        ) == 1;
    }

    public Customer getCustomer(long id) {
        ResultSet rs = databaseConnection.select("select `id`, `first_name`, `last_name`, `phone`, `address` from `customer` where `id` = ?", new Object[]{id});
        try {
            if (rs.next()) {
                return new Customer(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("getCustomer: " + e);
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("select `id`, `first_name`, `last_name`, `phone`, `address` from `customer`", new Object[]{});
        try {
            while(rs.next()) {
                ret.add(new Customer(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("getAllCustomers: " + e);
        }
        return ret;
    }

    public List<Customer> search(String keyword) {
        keyword = "%" + keyword.toLowerCase() + "%";

        List<Customer> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("select `id`, `first_name`, `last_name`, `phone`, `address` " +
            "from `customer` where first_name like ? or last_name like ? or phone like ? or address like ?",
            new Object[]{keyword, keyword, keyword, keyword}
        );
        try {
            while(rs.next()) {
                ret.add(new Customer(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("search: " + e);
        }
        return ret;
    }
}
