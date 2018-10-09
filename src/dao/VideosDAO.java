package dao;

import dbconnection.DatabaseException;
import model.Videos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideosDAO extends DAO{
    // singleton + lazy init
    private static VideosDAO instance;
    public static VideosDAO getInstance() {
        if (instance == null) {
            instance = new VideosDAO();
        }
        return instance;
    }

    private VideosDAO() {
        super();
    }

    //insert new video record
    public boolean create(Videos video) {
       return databaseConnection.insert(
                "INSERT INTO `videos`(`name`, `rating`, `genre`, `description`, `director`, `rentalFee`, `price`,`status`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[] {
                        video.getName(), video.getRating(), video.getGenre(),
                        video.getDescription(), video.getDirector(), video.getRentalFee(),video.getPrice(), /*video.getStatus()*/1
                }
        ).size() == 1;
    }

    //update existing video record
    public boolean update(Videos video) {
        return databaseConnection.update(
                "UPDATE `videos` SET `name`=?,`rating`=?,`genre`=?,`description`=?,`director`=?, `rentalFee`=?, `price`=?, `status`=? WHERE id = ?",
                new Object[] {
                        video.getName(), video.getRating(), video.getGenre(),
                        video.getDescription(), video.getDirector(), video.getRentalFee(),video.getPrice(), video.getStatus() == true ? 1 : 0,video.getId()
                }
        ) == 1;
    }

    //delete video record
    public boolean delete(int id) {
        return databaseConnection.delete(
                "DELETE FROM `videos` WHERE `id` = ?",
                new Object[]{id}
        ) == 1;
    }

    public Videos getVideoById(int id) {
        ResultSet rs = databaseConnection.select("select * from `videos` where `id` = ?", new Object[]{id});
        try {
            if (rs.next()) {
                //int id, String name, String rating, String genre, String description, String director,
                // double rentalFee, double price, boolean status
                return new Videos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getBoolean(9)
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting Video record by ID: " + e);
        }
        return null;
    }

    public List<Videos> getAllVideos() {
        List<Videos> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("select * from `videos`", new Object[]{});
        try {
            while(rs.next()) {
                ret.add(new Videos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getBoolean(9)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("getAllCars: " + e);
        }
        return ret;
    }

    public List<Videos> getAvailableVideos() {
        List<Videos> ret = new ArrayList<>();
        ResultSet rs = databaseConnection.select("select * from `videos` WHERE `status`=1", new Object[]{});
        try {
            while(rs.next()) {
                ret.add(new Videos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getBoolean(9)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("getAllCars: " + e);
        }
        return ret;
    }


    public List<Videos> getVideosFromUser(long uid) {
        List<Long> videoids = RentalRecordDAO.getInstance().getRentalRecordforReturn(uid);
        List<Videos> ret = new ArrayList<>();
        for(Long vdoid: videoids) {
            ResultSet rs = databaseConnection.select("select * from `videos` WHERE `id`=?", new Object[]{vdoid});
            try {
                while (rs.next()) {
                    ret.add(new Videos(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getDouble(7),
                            rs.getDouble(8),
                            rs.getBoolean(9)
                    ));
                }
            } catch (SQLException e) {
                throw new DatabaseException("getAllCars: " + e);
            }
        }
        return ret;
    }

    //search by movie name
    public List<Videos> searchByVideoName(String keyword) {
        List<Videos> ret = new ArrayList<>();
        keyword = "%" + keyword.toLowerCase() + "%";
        ResultSet rs = databaseConnection.select("select * " +
                "from `videos` where name like ? ", new Object[]{keyword});
        try {
            while(rs.next()) {
                ret.add(new Videos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getBoolean(9)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Search by Movie name : " + e);
        }
        return ret;
    }

    //search by movie name
    public List<Videos> searchByVideoGenre(String keyword) {
        List<Videos> ret = new ArrayList<>();
        keyword = "%" + keyword.toLowerCase() + "%";
        ResultSet rs = databaseConnection.select("select * " +
                "from `videos` where genre like ? ", new Object[]{keyword});
        try {
            while(rs.next()) {
                ret.add(new Videos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getBoolean(9)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("search by Movie genre: " + e);
        }
        return ret;
    }

}
