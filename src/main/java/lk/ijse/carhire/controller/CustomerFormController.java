package lk.ijse.carhire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.dto.tablemodel.CustomerTableModel;
import lk.ijse.carhire.service.ServiceFactory;
import lk.ijse.carhire.service.custom.CustomerService;

import java.util.List;

public class CustomerFormController {
    @FXML
    private TextField address_Text;

    @FXML
    private TextField city_Text;

    @FXML
    private TableColumn<?, ?> col_Address;

    @FXML
    private TableColumn<?, ?> col_City;

    @FXML
    private TableColumn<?, ?> col_Contact;

    @FXML
    private TableColumn<?, ?> col_ID;

    @FXML
    private TableColumn<?, ?> col_PostalCoad;

    @FXML
    private TableColumn<?, ?> col_Title;

    @FXML
    private TableColumn<?, ?> col_email;

    @FXML
    private TableColumn<?, ?> col_lastName;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TextField contact_Text;

    @FXML
    private TextField cust_ID_Text;

    @FXML
    private TextField cust_Title_text;

    @FXML
    private TableView<CustomerTableModel> customer_Table;

    @FXML
    private TextField email_Text;

    @FXML
    private TextField firstName_Text;

    @FXML
    private TextField lastName_Text;

    @FXML
    private TextField postalCode_Text;

    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CUSTOMER);
    public void btn_DeleteClickOnAction(ActionEvent actionEvent) {
        try {
            long custId = Long.parseLong(cust_ID_Text.getText());

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this customer?",
                    ButtonType.YES, ButtonType.NO );
            confirmationAlert.showAndWait();

            if(confirmationAlert.getResult() == ButtonType.YES){
                var customerDto = new CustomerDTO();
                customerDto.setCustomerId(custId);
                customerService.customerDelete(customerDto);
            }

            new Alert(Alert.AlertType.CONFIRMATION, "Customer Successfully Deleted").show();
            clear();
            initialize();
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID format").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btn_UpdateClickOnAction(ActionEvent actionEvent) throws Exception {
        try {
            long custID = Long.parseLong(cust_ID_Text.getText());
            String custTitle = cust_Title_text.getText();
            String firstName = firstName_Text.getText();
            String lastName = lastName_Text.getText();
            String city = city_Text.getText();
            String address = address_Text.getText();
            String postalCode = postalCode_Text.getText();
            String email = email_Text.getText();
            String contactNumber = contact_Text.getText();

            var custDto = new CustomerDTO(custID, custTitle, firstName, lastName, city, address, postalCode, email, contactNumber);

            customerService.customerUpdate(custDto);
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Successfully Updated").show();
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID format").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
        clear();
        initialize();
    }

    public void btn_SaveClickOnAction(ActionEvent actionEvent) {
        try{
        long custID = Long.parseLong(cust_ID_Text.getText());
        String custTitle = cust_Title_text.getText();
        String firstName = firstName_Text.getText();
        String lastName = lastName_Text.getText();
        String city = city_Text.getText();
        String address = address_Text.getText();
        String postalCode = postalCode_Text.getText();
        String email = email_Text.getText();
        String contactNumber = contact_Text.getText();

        //Input Validation
        if(custID<=0){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID. Please enter a valid number");
        }

        var custDto = new CustomerDTO(custID, custTitle, firstName, lastName,city,address,postalCode,email,contactNumber);
            customerService.customerSaved(custDto);
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Successfully Saved").show();
            clear();
            loadAllCustomer();

        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.CONFIRMATION, "Invalid Customer ID. Please enter a valid number").show();
        }catch (IllegalArgumentException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving customer: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception stack trace for debugging
        }

    }

    public void btn_SearchClickOnAction(ActionEvent actionEvent) {
        try {
            long custID = Long.parseLong(cust_ID_Text.getText());
            CustomerDTO customerDto = customerService.customerSearch(custID);
            if(customerDto != null){
                cust_ID_Text.setText(String.valueOf(customerDto.getCustomerId()));
                cust_Title_text.setText(customerDto.getTitle());
                firstName_Text.setText(customerDto.getTitle());
                lastName_Text.setText(customerDto.getLastName());
                city_Text.setText(customerDto.getCity());
                address_Text.setText(customerDto.getAddress());
                postalCode_Text.setText(customerDto.getPostalCode());
                email_Text.setText(customerDto.getEmail());
                contact_Text.setText(customerDto.getContactNumber());
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Not Found").show();
            }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID format").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void clear(){
        cust_ID_Text.setText("");
        cust_Title_text.setText("");
        firstName_Text.setText("");
        lastName_Text.setText("");
        city_Text.setText("");
        address_Text.setText("");
        postalCode_Text.setText("");
        email_Text.setText("");
        contact_Text.setText("");
    }

    public void initialize() throws Exception{
        col_ID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_City.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_PostalCoad.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_Contact.setCellValueFactory(new PropertyValueFactory<>("contact_Number"));
        loadAllCustomer();
    }
    public void loadAllCustomer() {
        try {
            List<CustomerDTO> customer = customerService.getAllCustomer();
            if (customer == null) {
                // Handle case where no customers are found
                new Alert(Alert.AlertType.ERROR, "No customers found.").show();
                //System.out.println("No customers found.");
                return;
            }

            ObservableList<CustomerTableModel> obsList = getCustomerTableModels(customer);
            customer_Table.setItems(obsList);
        } catch (Exception e) {
            // Log any exceptions that occur
            new Alert(Alert.AlertType.ERROR, "Error loading customers: " + e.getMessage()).show();
           // System.err.println("Error loading customers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ObservableList<CustomerTableModel> getCustomerTableModels(List<CustomerDTO> customer) {
        ObservableList<CustomerTableModel> obsList = FXCollections.observableArrayList();
        for (CustomerDTO customerDto : customer) {
            var tableModel = new CustomerTableModel(customerDto.getCustomerId(),
                    customerDto.getTitle(), customerDto.getFirstName(), customerDto.getLastName(),
                    customerDto.getCity(), customerDto.getAddress(),
                    customerDto.getPostalCode(), customerDto.getEmail(), customerDto.getContactNumber());
            obsList.add(tableModel);
        }
        return obsList;
    }
}
