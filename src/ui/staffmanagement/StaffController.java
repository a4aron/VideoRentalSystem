package ui.staffmanagement;

import controller.ControllerInterface;
import controller.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Staff;
import ruleset.RuleException;
import ruleset.RuleSet;
import ruleset.RuleSetFactory;
//import ui.customermanagement.CustomerController;
import util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaffController {

    @FXML
    private TextField tfStaffId, tfStaffUsername, tfStaffFirstName, tfStaffLastName, tfStaffPhone, tfStaffAddress, tfStaffSalary, tfStaffRole;
    @FXML
    private ListView<String> staffListView;

    public void initialize() {
        ControllerInterface controller = SystemController.getInstance();
        List<Staff> staffList = controller.getAllStaff();
        ArrayList<String> staffs = new ArrayList<>();
        for (Staff s:
                staffList) {
            staffs.add(s.getFirstName() + " " + s.getLastName());
        }
        ObservableList<String> strList = FXCollections.observableArrayList(staffs);

        staffListView.setItems(strList);
        staffListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                int position = staffListView.getSelectionModel().getSelectedIndex();
                tfStaffId.setText(staffList.get(position).getId()+"");
                tfStaffUsername.setText(staffList.get(position).getUsername()+"");
                tfStaffFirstName.setText(staffList.get(position).getFirstName()+"");
                tfStaffLastName.setText(staffList.get(position).getLastName()+"");
                tfStaffPhone.setText(staffList.get(position).getPhone()+"");
                tfStaffAddress.setText(staffList.get(position).getAddress()+"");
                tfStaffSalary.setText(staffList.get(position).getSalary()+"");
                tfStaffRole.setText(staffList.get(position).getRole()+"");
            }
        });
    }

    @FXML
    public void updateStaff(ActionEvent actionEvent) throws IOException {
        try {
            RuleSet staffRules = RuleSetFactory.getRuleSet(StaffController.this);
            staffRules.applyRules(StaffController.this);
            ControllerInterface controller = SystemController.getInstance();
            if (tfStaffId.getText().isEmpty()){
                if (controller.addStaff(new Staff(0, tfStaffFirstName.getText(), tfStaffLastName.getText(), tfStaffPhone.getText(), tfStaffAddress.getText(), tfStaffUsername.getText(), "12345", Integer.parseInt(tfStaffRole.getText()), Double.parseDouble(tfStaffSalary.getText())))) {
                    initialize();
                    clearFields(actionEvent);
                }
            } else {
                if (controller.updateStaff(new Staff(Long.parseLong(tfStaffId.getText()), tfStaffFirstName.getText(), tfStaffLastName.getText(), tfStaffPhone.getText(), tfStaffAddress.getText(), tfStaffUsername.getText(), "", Integer.parseInt(tfStaffRole.getText()), Double.parseDouble(tfStaffSalary.getText())))) {
                    initialize();
                }
            }
        } catch (RuleException e) {
            Util.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteStaff(ActionEvent actionEvent) {
        if (tfStaffId.getText().isEmpty()) {
            Util.showError("No staff chosen");
            return;
        }
        ControllerInterface controller = SystemController.getInstance();
        if (controller.deleteStaff(Long.parseLong(tfStaffId.getText()))){
            initialize();
            clearFields(actionEvent);
        }
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        tfStaffId.clear();
        tfStaffUsername.clear();
        tfStaffFirstName.clear();
        tfStaffLastName.clear();
        tfStaffPhone.clear();
        tfStaffAddress.clear();
        tfStaffSalary.clear();
        tfStaffRole.clear();
    }

    public String getUsername(){
        return tfStaffUsername.getText();
    }

    public String getFirstName(){
        return tfStaffFirstName.getText();
    }

    public String getLastName(){
        return tfStaffLastName.getText();
    }

    public String getPhone(){
        return tfStaffPhone.getText();
    }

    public String getAddress(){
        return tfStaffAddress.getText();
    }

    public String getSalary(){
        return tfStaffSalary.getText();
    }

    public String getRole(){
        return tfStaffRole.getText();
    }



}
