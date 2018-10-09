package ui.customermanagement;

import controller.Auth;
import controller.ControllerInterface;
import controller.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Customer;
import ruleset.RuleException;
import ruleset.RuleSet;
import ruleset.RuleSetFactory;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    @FXML
    private TextField tfCustomerId, tfCustomerFName, tfCustomerLName, tfCustomerPhone, tfCustomerAddress;
    @FXML
    private ListView<String> customerListView;
    @FXML
    private Button addCustomer, updateCustomer, deleteCustomer, clearCustomer;

    public void initialize() {
        if (SystemController.getInstance().currentAuth.equals(Auth.EMPLOYEE)) {
            deleteCustomer.setVisible(false);
        }
        ControllerInterface controller = SystemController.getInstance();
        List<Customer> customerList = controller.getAllCustomer();
        ArrayList<String> customers = new ArrayList<>();
        for (Customer s :
                customerList) {
            customers.add(s.getFirstName() + " " + s.getLastName());
        }
        ObservableList<String> strList = FXCollections.observableArrayList(customers);

        customerListView.setItems(strList);
        customerListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int position = customerListView.getSelectionModel().getSelectedIndex();
                tfCustomerId.setText(customerList.get(position).getId() + "");
                tfCustomerFName.setText(customerList.get(position).getFirstName());
                tfCustomerLName.setText(customerList.get(position).getLastName());
                tfCustomerPhone.setText(customerList.get(position).getPhone());
                tfCustomerAddress.setText(customerList.get(position).getAddress());
            }
        });
    }

    @FXML
    public void updateCustomer(ActionEvent actionEvent) {
        try {
            RuleSet customerRules = RuleSetFactory.getRuleSet(CustomerController.this);
            customerRules.applyRules(CustomerController.this);
            ControllerInterface controller = SystemController.getInstance();
            if (tfCustomerId.getText().isEmpty()) {
                if (controller.addCustomer(new Customer(tfCustomerFName.getText(), tfCustomerLName.getText(), tfCustomerPhone.getText(), tfCustomerAddress.getText()))) {
                    initialize();
                    clearFields(actionEvent);
                }
            } else {
                if (controller.updateCustomer(new Customer(Long.parseLong(tfCustomerId.getText()), tfCustomerFName.getText(), tfCustomerLName.getText(), tfCustomerPhone.getText(), tfCustomerAddress.getText()))) {
                    initialize();
                }
            }
        } catch (RuleException e) {
            Util.showError(e.getMessage());
        }
    }

    @FXML
    public void deleteCustomer(ActionEvent actionEvent) {
        if (tfCustomerId.getText().isEmpty()) {
            Util.showError("No customer chosen");
            return;
        }
        ControllerInterface controller = SystemController.getInstance();
        if (controller.deleteCustomer(Long.parseLong(tfCustomerId.getText()))) {
            initialize();
            clearFields(actionEvent);
        }
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        tfCustomerId.clear();
        tfCustomerFName.clear();
        tfCustomerLName.clear();
        tfCustomerPhone.clear();
        tfCustomerAddress.clear();
    }

    public String getFirstName() {
        return tfCustomerFName.getText();
    }

    public String getLastName() {
        return tfCustomerLName.getText();
    }

    public String getPhone() {
        return tfCustomerPhone.getText();
    }

    public String getAddress() {
        return tfCustomerAddress.getText();
    }

}
