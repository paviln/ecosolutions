package dk.ecosolutions.oms.application.controllers.assistant;

import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.service.CustomerService;
import dk.ecosolutions.oms.service.helpers.AlertHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;


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
            AlertHelper.showErrorAlert("One of the field is empty");
        } else {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());

            CustomerService.addCustomer(customer);
            customerTable.getItems().add(customer);
            AlertHelper.showInformationAlert("Saved Successfully");
        }
    }

    @FXML
    public void update() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());
            AlertHelper.showInformationAlert("updated");
            CustomerService.updateCustomer(customer);
        }
        name.clear();
        phone.clear();
    }

    /**
     * This function shows details of the customer in the
     * respective field when a particular row is clicked
     */
    @FXML
    public void show() {
        try {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            name.setText(customer.getName());
            phone.setText(customer.getPhone());
        }catch (NullPointerException e){

        }

    }
    @FXML
    public void delete() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            if (AlertHelper.confirmAlert("Sure you want to delete the customer?")) {
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
