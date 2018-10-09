package dao;

import dbconnection.DatabaseException;
import model.Videos;
import model.Customer;
import model.RentalRecord;
import model.Staff;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RentalRecordDAO extends DAO {
    // singleton + lazy init
    private static RentalRecordDAO instance;
    public static RentalRecordDAO getInstance() {
        if (instance == null) {
            instance = new RentalRecordDAO();
        }
        return instance;
    }

    private RentalRecordDAO() {
        super();
    }

    public boolean create(RentalRecord rentalRecord) {
        int rec_num = databaseConnection.insert(
                "INSERT INTO `rental_record`(`staff_id`, `video_id`, `customer_id`, `date`, `expected_return_date`) " +
                        "VALUES (?, ?, ?, ?, ?)",
                new Object[] {
                        rentalRecord.getStaff().getId(), rentalRecord.getVideo().getId(), rentalRecord.getCustomer().getId(),
                        Date.valueOf(rentalRecord.getRentalDate()),
                        Date.valueOf(rentalRecord.getExpectedReturnDate())
                }
        ).size();
        if(rec_num == 1){
            int vdo_id = rentalRecord.getVideo().getId();
            Videos v = VideosDAO.getInstance().getVideoById(vdo_id);
            v.setStatus(false);
            return VideosDAO.getInstance().update(v);
        }
        else return false;
    }

    public boolean update(RentalRecord rentalRecord) {
        return databaseConnection.update(
                "UPDATE `rental_record` SET `staff_id`=?,`video_id`=?,`customer_id`=?," +
                        "`date`=?,`expected_return_date`=?,`actual_return_date`=?,`fine_amount`=?;",
                new Object[] {
                        rentalRecord.getStaff().getId(), rentalRecord.getVideo().getId(), rentalRecord.getCustomer().getId(),
                        rentalRecord.getRentalDate(), rentalRecord.getExpectedReturnDate(), rentalRecord.getActualReturnDate(),
                        rentalRecord.getFineAmount()
                }
        ) == 1;
    }

    public boolean delete(long id) {
        return databaseConnection.delete(
                "DELETE FROM `rental_record` WHERE `id` = ?",
                new Object[]{id}
        ) == 1;
    }

    public String[] deleteByVideoIdAndUserId(int vdoid, long uid){
        Videos v = VideosDAO.getInstance().getVideoById(vdoid);
        v.setStatus(true);
        if(VideosDAO.getInstance().update(v) == true){
            ResultSet rs = databaseConnection.select(
                    "SELECT `id`,DATEDIFF(CURRENT_TIMESTAMP,`expected_return_date`)  FROM `rental_record` where `video_id` = ? AND `customer_id` = ? ",
                    new Object[]{vdoid,uid});
            try {
                if (rs.next()) {
                    long rental_id = rs.getLong(1);
                    int fine = rs.getInt(2);
                    return new String[]{String.valueOf(fine), String.valueOf(this.delete(rental_id))};
                }
            } catch (SQLException e) {
                throw new DatabaseException("getRentalRecord: " + e);
            }

        }
        return new String[] {"false",""};
    }
    public RentalRecord getRentalRecord(long id) {
        ResultSet rs = databaseConnection.select(
                "SELECT `id`, `date`, `expected_return_date`, `actual_return_date`, `fine_amount`, `staff_id`, `video_id`, `customer_id`" +
                        "FROM `rental_record` where `id` = ?",
                new Object[]{id});
        try {
            if (rs.next()) {
                return new RentalRecord(
                        rs.getLong(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getDate(3).toLocalDate(),
                        rs.getDate(4).toLocalDate(),
                        rs.getDouble(5),
                        VideosDAO.getInstance().getVideoById(rs.getInt(7)),
                        CustomerDAO.getInstance().getCustomer(rs.getLong(8)),
                        StaffDAO.getInstance().getStaff(rs.getLong(6))
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("getRentalRecord: " + e);
        }
        return null;
    }

    public List<RentalRecord> getAllRentalRecords() {
        List<RentalRecord> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("SELECT `id`, `date`, `expected_return_date`, `actual_return_date`, " +
                "`fine_amount`, `staff_id`, `car_id`, `customer_id` FROM `rental_record`", new Object[]{});
        try {
            while(rs.next()) {
                Date actualReturnDate_ = rs.getDate(4);
                LocalDate actualReturnDate = (actualReturnDate_ == null)? null: actualReturnDate_.toLocalDate();
                ret.add(new RentalRecord(
                        rs.getLong(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getDate(3).toLocalDate(),
                        actualReturnDate,
                        rs.getDouble(5),
                        VideosDAO.getInstance().getVideoById(rs.getInt(7)),
                        CustomerDAO.getInstance().getCustomer(rs.getLong(8)),
                        StaffDAO.getInstance().getStaff(rs.getLong(6))
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("getAllRentalRecords: " + e);
        }
        return ret;
    }

    public List<Long> getRentalRecordforReturn(long uid) {
        List<Long> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("SELECT `video_id` FROM `rental_record` WHERE `customer_id`= ?", new Object[]{uid});
        try {
            while(rs.next()) {
               ret.add(
                        rs.getLong(1)
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("getRentalRecordforReturn: " + e);
        }
        return ret;
    }

}
