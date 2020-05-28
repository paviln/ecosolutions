package dk.ecosolutions.oms.application.controllers.assistant;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.sql.SQLException;

public class CustomerController {
    @FXML
    private TextField name, phone;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> col_customerName;
    @FXML
    private TableColumn<Customer, String> col_customerPhone;


    @FXML
    public void initialize() {
        col_customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        col_customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        customerTable.getItems().addAll(CustomerService.allCustomer());
    }

    @FXML
    public void save() {
        Customer customer = new Customer();
        if (name.getText().isEmpty() || phone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "One of the field is empty");
        } else {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());

            CustomerService.addCustomer(customer);
            customerTable.getItems().add(customer);
            JOptionPane.showMessageDialog(null, "Saved Successfully");
        }
    }

    @FXML
    public void update() throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());
            JOptionPane.showMessageDialog(null, "updated");
            CustomerService.updateCustomer(customer);
        }
        name.clear();
        phone.clear();
    }

    /**
     * This function shows details of the customer is shown in the
     * respective field when a particular row is clicked
     */
    @FXML
    public void show() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        name.setText(customer.getName());
        phone.setText(customer.getPhone());
    }

    public void delete() throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            JOptionPane frame = new JOptionPane();
            if (JOptionPane.showConfirmDialog(frame, "Confirm if You want to delete", "print System",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Deleted successfully");

                CustomerService.deleteCustomer(customer);
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                customerTable.getItems().remove(selectedCustomer);
            }
        }
    }

    /**
     * This function allows only number input in the phone field
     * @param keyEvent
     */
    public void numberValidation(KeyEvent keyEvent) {
        phone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * This function allows only letters in the name field
     * @param keyEvent
     */
    public void letterValidation(KeyEvent keyEvent) {
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                name.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
    }
}
