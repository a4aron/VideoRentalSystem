package ui.dashboard;

import controller.Auth;
import controller.ControllerInterface;
import controller.SystemController;
import dao.CustomerDAO;
import dao.VideosDAO;
import dao.RentalRecordDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import ruleset.RuleException;
import ruleset.RuleSet;
import ruleset.RuleSetFactory;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Videos;
import model.Customer;
import model.Staff;
import model.RentalRecord;
import ui.Main;
import util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    private static final boolean IS_TEST = false;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String customerId = null;
    int vdoId = -1;
    int late_fine = 0;

    @FXML
    private ListView<String> customer_list, video_list;

    @FXML
    private DatePicker rent_date, expected_date;

    @FXML
    private TextField search_customer, search_video;

    @FXML
    private Label lbl_movie, lbl_rating, lbl_genre, lbl_director,lbl_rental, lbl_status, lbl_price;

    @FXML
    private Label lbl_firstname, lbl_lastname, lbl_phone, lbl_address;

    @FXML
    private Label lbl_rental_title, lbl_rent_Date, lbl_expected_date;

    @FXML
    private Button btn_customer, btn_video, btn_rental, btn_staff, btn_return, btn_save;

    @FXML
    private HBox top_layout, bottom_layout;

    @FXML
    private BorderPane root;

    private List<Videos> videoList;
    private List<Customer> customerList;
    private int indexOfVideoList = -1, indexOfCustomerList = -1;
    private String current_view = "RENT";


    public void initialize() throws IOException {
        if (SystemController.getInstance().currentAuth.equals(Auth.EMPLOYEE)) {
            btn_save.setVisible(false);
        }
        initVideoList(current_view);
        initCustomerList();
        initCSS();
    }

//    private String enterColor = "#383838";
    private String enterColor = "#CCC";
    private String exitColor = "#303030";

    private void initCSS() throws IOException {
        btn_customer.setStyle("-fx-background-color: " + exitColor);
        btn_video.setStyle("-fx-background-color: " + exitColor);
        btn_rental.setStyle("-fx-background-color: " + exitColor);
        btn_staff.setStyle("-fx-background-color: " + exitColor);
        top_layout.setStyle("-fx-background-color: " + exitColor);
        bottom_layout.setStyle("-fx-background-color: #F8F9F9; ");
    }
    private void initVideoList(String view) {
//        videoList = VideosDAO.getInstance().getAllVideos();
        if(view == "RENT") {
            videoList = VideosDAO.getInstance().getAvailableVideos();
            List<String> videoNameList = new ArrayList<>();
            for (Videos vdo : videoList) {
                if (vdo.getStatus())
                    videoNameList.add(vdo.getName());
            }
            ObservableList<String> strList = FXCollections.observableArrayList(videoNameList);
            video_list.setItems(strList);
        }
    }

//    private void initAvailableVideoList() {
//        videoList = VideosDAO.getInstance().getAvailableVideos();
//        List<String> videoNameList = new ArrayList<>();
//        for (Videos vdo : videoList) {
//            if(vdo.getStatus())
//                videoNameList.add(vdo.getName());
//        }
//        ObservableList<String> strList = FXCollections.observableArrayList(videoNameList);
//        video_list.setItems(strList);
//    }

    private void initCustomerList() {
        customerList = CustomerDAO.getInstance().getAllCustomers();
        List<String> customerNameList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerNameList.add(customer.getFirstName() + " " + customer.getLastName());
        }
        ObservableList<String> strList = FXCollections.observableArrayList(customerNameList);
        customer_list.setItems(strList);
    }


    //MENU ITEM HOVER EFFECT
    @FXML
    public void onVideoMouseEntered() {
        btn_video.setStyle("-fx-background-color: " + enterColor);
    }

    @FXML
    public void onVideoMouseExited() {
        btn_video.setStyle("-fx-background-color: " + exitColor);
    }

    @FXML
    public void onStaffMouseEntered() {
        btn_staff.setStyle("-fx-background-color: " + enterColor);
    }

    @FXML
    public void onStaffMouseExited() {
        btn_staff.setStyle("-fx-background-color: " + exitColor);
    }
    @FXML
    public void onCustomerMouseEntered() {
        btn_customer.setStyle("-fx-background-color: " + enterColor);
    }

    @FXML
    public void onCustomerMouseExited() {
        btn_customer.setStyle("-fx-background-color: " + exitColor);
    }

    @FXML
    public void onRetalMouseEntered() {
        btn_rental.setStyle("-fx-background-color: " + enterColor);
    }

    @FXML
    public void onRetalMouseExited() {
        btn_rental.setStyle("-fx-background-color: " + exitColor);
    }
    @FXML
    public void onReturnMouseEntered() {
        btn_return.setStyle("-fx-background-color: " + enterColor);
    }

    @FXML
    public void onReturnMouseExited() {
        btn_return.setStyle("-fx-background-color: " + exitColor);
    }


    //MENU ITEM CLICK HANDLER

    @FXML
    public void onReturnClick() throws IOException{
        current_view = "RETURN";
        //View Change
         rent_date.setVisible(false);
         expected_date.setVisible(false);
         lbl_rental_title.setVisible(false);
         lbl_rent_Date.setVisible(false);
         lbl_expected_date.setVisible(false);
         btn_save.setText(current_view);
         video_list.getItems().clear();
         initialize();

    }
    @FXML
    public void onRentalClick() throws IOException{
        current_view = "RENT";
        rent_date.setVisible(true);
        expected_date.setVisible(true);
        lbl_rental_title.setVisible(true);
        lbl_rent_Date.setVisible(true);
        lbl_expected_date.setVisible(true);
        btn_save.setText("RENT");
        initialize();
    }

    @FXML
    public void onCustomerMenuClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../customermanagement/customer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Customer Managerment");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(event -> {
            clean();
            initVideoList("RENT");
            initCustomerList();
        });
    }
    @FXML
    public void onVideoMenuClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../videomanagement/video.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Video Management");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(event -> {
            clean();
            initVideoList("RENT");
            initCustomerList();
        });
    }

    @FXML
    public void onLogOutMenuClick() throws IOException {
        ControllerInterface controller = SystemController.getInstance();
        controller.logout();

        Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        Stage stage = new Stage();
        Image applicationIcon = new Image(getClass().getResourceAsStream("../image/file-video-icon.png"));
        stage.getIcons().add(applicationIcon);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Video Rental System");
        stage.setScene(new Scene(root));
        stage.show();
        Stage loginStage = (Stage) btn_staff.getScene().getWindow();
        loginStage.close();

    }

    //Search Box typing event handler

    //<<<<<<<------------- for Customer -----------------
    @FXML
    public void onSearchCustomer() {
        String referenceStr = search_customer.getText();
        customerList = getCustomerList(referenceStr);
        List<String> customerNameList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerNameList.add(customer.getFirstName() + " " + customer.getLastName());
        }
        ObservableList<String> strList = FXCollections.observableArrayList(customerNameList);
        customer_list.setItems(strList);
    }

    private List<Customer> getCustomerList(String referenceStr) {
        if (referenceStr.isEmpty())
            return CustomerDAO.getInstance().getAllCustomers();
        else
            return CustomerDAO.getInstance().search(referenceStr);
    }
    //------------- for Customer ----------------- >>>>

    //<<<<<<<------------- for Video -----------------
    @FXML
    public void onSearchVideo() {
        String referenceStr = search_video.getText();
        videoList = getVideoList(referenceStr);
        List<String> videoNameList = new ArrayList<>();
        for (Videos vdo : videoList) {
            videoNameList.add(vdo.getName());
        }
        ObservableList<String> strList = FXCollections.observableArrayList(videoNameList);
        video_list.setItems(strList);
    }

    private List<Videos> getVideoList(String referenceStr) {
        if (referenceStr.isEmpty())
            return VideosDAO.getInstance().getAllVideos();
        else
            return VideosDAO.getInstance().searchByVideoName(referenceStr);
    }
    //------------- for Video ----------------- >>>>


    // <<<<---------- List ITEM click event handler
    @FXML
    public void onCustomerItemClick() {
        indexOfCustomerList = customer_list.getSelectionModel().getSelectedIndex();
        Customer selectedItem = customerList.get(indexOfCustomerList);
        lbl_firstname.setText(selectedItem.getFirstName());
        lbl_lastname.setText(selectedItem.getLastName());
        lbl_phone.setText(selectedItem.getPhone());
        lbl_address.setText(selectedItem.getAddress());

        if(current_view == "RETURN"){
            long user_id = selectedItem.getId();
            videoList = VideosDAO.getInstance().getVideosFromUser(user_id);
            List<String> videoNameList = new ArrayList<>();
            for (Videos vdo : videoList) {
                    videoNameList.add(vdo.getName());
            }
            video_list.getItems().clear();
            ObservableList<String> strList = FXCollections.observableArrayList(videoNameList);
            video_list.setItems(strList);
        }
    }

    @FXML
    public void onVideoItemClick() {
        indexOfVideoList = video_list.getSelectionModel().getSelectedIndex();
        Videos selectedItem = videoList.get(indexOfVideoList);
        lbl_movie.setText(selectedItem.getName());
        lbl_status.setText(selectedItem.getStatus() ? "Available" : "Not Available");
        lbl_rating.setText(selectedItem.getRating());
        lbl_genre.setText(selectedItem.getGenre());
        lbl_director.setText(selectedItem.getDirector());
        lbl_rental.setText(String.valueOf(selectedItem.getRentalFee()));
        lbl_price.setText(String.valueOf(selectedItem.getPrice()));
       }
       //---------- List ITEM click event handler >>>>>>>>>>>>>>

    // getter and setter for Dashboard Ruleset
    public String getCustomerId() {
        return customerId;
    }

    public String getStatus(){
        return lbl_status.getText();
    }
    public int getVideoId() {
        return vdoId;
    }

    public LocalDate getRentDate() {
        return rent_date.getValue();
    }

    public LocalDate getExpectedDate() {
        return expected_date.getValue();
    }


    // On Save button click
    @FXML
    public void on_Save() {
        try {
            if (indexOfCustomerList >- 1) {
                customerId = customerList.get(indexOfCustomerList).getId() + "";
            }
            if (indexOfVideoList >- 1) {
                vdoId = videoList.get(indexOfVideoList).getId();
            }
            boolean success = false;
            if(current_view == "RENT") {
                RuleSet dashboardRules = RuleSetFactory.getRuleSet(DashboardController.this);
                dashboardRules.applyRules(DashboardController.this);

                Videos vdo = new Videos(vdoId);
                Customer customer = new Customer(Long.parseLong(customerId));
                Staff staff = new Staff(SystemController.getInstance().currentAuth.getUserId());

                 success = RentalRecordDAO.getInstance().create(new RentalRecord(
                        vdo, customer, staff,
                        rent_date.getValue(),
                        expected_date.getValue()));
            }
            if(current_view == "RETURN") {
                String[] status = RentalRecordDAO.getInstance().deleteByVideoIdAndUserId(vdoId, Long.parseLong(customerId));
                success = Boolean.parseBoolean(status[1]);
                late_fine = Integer.parseInt(status[0]) > 0 ? Integer.parseInt(status[0]) : 0;
            }
            if (success) {
                showSubmitSuccessDialog();
                clean();
            } else {
                showSubmitErrorDialog();
            }
        } catch (RuleException e) {
            Util.showError(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
            showSubmitErrorDialog();
        }
    }
    private void showSubmitSuccessDialog() {
        String message = current_view == "RETURN" ? "Movie Returned Successfully  \n Fine ($1 per day): $" +late_fine : "Movie Rented Successfully ";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSubmitErrorDialog() {
        String message = current_view == "RETURN" ? "Failure while returning" : "Failure while renting";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clean() {
        lbl_firstname.setText("-");
        lbl_lastname.setText("-");
        lbl_phone.setText("-");
        lbl_address.setText("-");

        lbl_movie.setText("-");
        lbl_genre.setText("-");
        lbl_director.setText("-");
        lbl_rental.setText("-");
        lbl_status.setText("-");
        lbl_rating.setText("-");
        lbl_price.setText("-");

        rent_date.getEditor().setText("");
        expected_date.getEditor().setText("");

        indexOfVideoList = -1;
        indexOfCustomerList = -1;


        customerId = null;
        vdoId = -1;
        rent_date.setValue(null);
        expected_date.setValue(null);

        search_customer.setText("");
        search_video.setText("");

        initVideoList(current_view);
        initCustomerList();
    }
}
