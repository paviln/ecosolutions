package dk.ecosolutions.oms.application.controllers.assistant;

import dk.ecosolutions.oms.application.controllers.WelcomeController;
import dk.ecosolutions.oms.application.helpers.DialogHelper;
import dk.ecosolutions.oms.domain.Customer;
import dk.ecosolutions.oms.service.CustomerService;
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
    private TableColumn<Customer, Integer> col_customerID;
    /**
     * Initialized after fxml is loaded
     */
    @FXML
    public void initialize() {
        col_customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        col_customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        col_customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        customerTable.getItems().addAll(CustomerService.allCustomer());
    }

    /**
     * The function saves customer input data into the customer
     * database table
     */
    @FXML
    public void save() {
        Customer customer = new Customer();
        if (name.getText().isEmpty() || phone.getText().isEmpty()) {
            DialogHelper.showErrorAlert("One of the field is empty");
        } else {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());
            customer.setId(WelcomeController.getAuthenticatedUser().getId());
            CustomerService.addCustomer(customer);
            customerTable.getItems().add(customer);
            DialogHelper.showInformationAlert("Saved Successfully");
        }
    }

    /**
     * The function updates changes in customer name and phone
     */
    @FXML
    public void update() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            customer.setName(name.getText());
            customer.setPhone(phone.getText());
            DialogHelper.showInformationAlert("updated");
            CustomerService.updateCustomer(customer);
            customerTable.refresh();
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
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            name.setText(customer.getName());
            phone.setText(customer.getPhone());
        }
    }

    /**
     * The function deletes the selected customer from the database
     */
    @FXML
    public void delete() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            if (DialogHelper.confirmAlert("Sure you want to delete the customer?")) {
                CustomerService.deleteCustomer(customer);
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                customerTable.getItems().remove(selectedCustomer);
            }
        }
    }

    /**
     * This function allows only number input in the phone field
     *
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
     *
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
