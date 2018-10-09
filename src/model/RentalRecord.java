package model;

import java.time.LocalDate;

public class RentalRecord {

    private long id;
    private LocalDate rentalDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private double fineAmount;
    private Videos video;
    private Customer customer;
    private Staff staff;

    public RentalRecord(Videos video, Customer customer, Staff staff, LocalDate rentalDate, LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
        this.rentalDate = rentalDate;
        this.video = video;
        this.customer = customer;
        this.staff = staff;
    }

    public RentalRecord(long id, LocalDate rentalDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, double fineAmount, Videos video, Customer customer, Staff staff) {
        this(rentalDate, expectedReturnDate, actualReturnDate, fineAmount,video, customer, staff);
        this.setId(id);
    }

    public RentalRecord(LocalDate rentalDate,
                        LocalDate expectedReturnDate,
                        LocalDate actualReturnDate,
                        double fineAmount,
                        Videos car,
                        Customer customer,
                        Staff staff) {
        this.rentalDate = rentalDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.fineAmount = fineAmount;
        this.video = car;
        this.customer = customer;
        this.staff = staff;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Videos getVideo() {
        return video;
    }

    public void setCar(Videos video) {
        this.video = video;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
