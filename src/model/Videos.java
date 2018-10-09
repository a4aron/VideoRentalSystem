package model;

import java.io.Serializable;

public final class Videos implements Serializable{

    private int id;
    private String name;
    private String rating;
    private String genre;
    private String description;
    private String director;
    private double rentalFee;
    private double price;
    private boolean status;

    public Videos(int id) {
        this.id = id;
    }

    public Videos(int id, String name, String rating, String genre, String description, String director, double rentalFee, double price, boolean status) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.rentalFee = rentalFee;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}






