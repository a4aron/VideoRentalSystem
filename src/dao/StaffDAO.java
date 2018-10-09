package dao;

import controller.Auth;
import dbconnection.DatabaseException;
import model.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO extends DAO{
    // singleton + lazy init
    private static StaffDAO instance;
    public static StaffDAO getInstance() {
        if (instance == null) {
            instance = new StaffDAO();
        }
        return instance;
    }

    private StaffDAO() {
        super();
    }

    public Auth login(String username, String password) {
        ResultSet rs = this.databaseConnection.select(
                "select id, role from staff where account_username = ? and account_password = md5(?)", new Object[]{username, password}
        );
        try {
            if (rs.next()) {
                return Auth.of(rs.getInt(2)).setUserId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("login: " + e);
        }
        return Auth.INVALID;
    }

    /**
     * Role 0 - Admin
     * Role 1 - Employee
     * @param staff
     * @return true - insert successfully, otherwise false
     */
    public boolean register(Staff staff) {
        List<Long> rs = this.databaseConnection.insert(
                "INSERT INTO `staff`(`account_username`, `account_password`, `first_name`, `last_name`, `phone`, `address`, `salary`, `role`) " +
                        "VALUES (?, md5(?), ?, ?, ?, ?, ?, ?)",
                new Object[]{
                        staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(),
                        staff.getPhone(), staff.getAddress(), staff.getSalary(), staff.getRole()
                }
        );
        return !rs.isEmpty();
    }

    public boolean delete(long id) {
        //TODO: must check the relationship between staff and record before delete
        /**
         * For future referencing, if a staff issues already at least one record, then we don't allow that staff
         * record to be deleted. Only standalone staff can be deleted
         */
        int rs = databaseConnection.delete("delete from `staff` where `id` = ?", new Object[]{id});
        return rs == 1;
    }

    public boolean update(Staff staff) {
        int rs = databaseConnection.update(
                "UPDATE `staff` SET `account_username`=?,`account_password`=md5(?),`first_name`=?,`last_name`=?," +
                        "`phone`=?,`address`=?,`salary`=?,`role`=? WHERE `id` = ?",
                new Object[]{
                        staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(),
                        staff.getPhone(), staff.getAddress(), staff.getSalary(), staff.getRole(), staff.getId()
                }
        );
        return rs == 1;
    }

    public boolean updateWithoutPassword(Staff staff) {
        int rs = databaseConnection.update(
                "UPDATE `staff` SET `account_username`=?, `first_name`=?,`last_name`=?," +
                        "`phone`=?,`address`=?,`salary`=?,`role`=? WHERE `id` = ?",
                new Object[]{
                        staff.getUsername(), staff.getFirstName(), staff.getLastName(),
                        staff.getPhone(), staff.getAddress(), staff.getSalary(), staff.getRole(), staff.getId()
                }
        );
        return rs == 1;
    }

    public Staff getStaff(long id) {
        ResultSet rs = databaseConnection.select("select `id`, `first_name`, `last_name`, `phone`, `address`, `account_username`, `account_password`, `role`, `salary`  from `staff` where `id` = ?", new Object[]{id});
        try {
            if (rs.next()) {
                return new Staff(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getDouble(9)
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("getStaff: " + e);
        }
        return null;
    }

    public List<Staff> getAllStaff() {
        List<Staff> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("select `id`, `first_name`, `last_name`, `phone`, `address`, `account_username`, `account_password`, `role`, `salary` from `staff`", new Object[]{});
        try {
            while(rs.next()) {
                ret.add(new Staff(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getDouble(9)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("getAllStaff: " + e);
        }
        return ret;
    }
}
