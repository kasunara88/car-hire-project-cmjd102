package lk.ijse.carhire.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.carhire.dto.CarDTO;
import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.dto.RentDTO;
import lk.ijse.carhire.dto.tablemodel.RentTableModel;

import lk.ijse.carhire.service.ServiceFactory;
import lk.ijse.carhire.service.custom.CarService;
import lk.ijse.carhire.service.custom.CustomerService;
import lk.ijse.carhire.service.custom.RentService;

import java.io.IOException;
import java.time.LocalDate;


import java.util.List;

public class RentFormController {
    @FXML
    private TextField advncedPayment_Text;

    @FXML
    private ComboBox<CarDTO> cmb_CarID;

    @FXML
    private ComboBox<CustomerDTO> cmb_CustID;

    @FXML
    private TableColumn<?, ?> col_rentFee;

    @FXML
    private TableColumn<?, ?> col_CustID;

    @FXML
    private TableColumn<?, ?> col_EndDate;

    @FXML
    private TableColumn<?, ?> col_RentID;

    @FXML
    private TableColumn<?, ?> col_StartDate;

    @FXML
    private DatePicker endDate_Picker;

    @FXML
    private Label lbl_DueAmount;

    @FXML
    private Label lbl_TotalAmount;

    @FXML
    private TextField rentID_Text;

    @FXML
    private TextField rentPerDay_Text;

    @FXML
    private TableView<RentTableModel> rent_Table;

    @FXML
    private DatePicker start_Day_Picker;

    RentService rentService = (RentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.RENT);
    CarService carService = (CarService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CAR);
    CustomerService customerService = (CustomerService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CUSTOMER);

    public void btn_RentClickOnAction(ActionEvent actionEvent) {
        try {
            long rentID = Long.parseLong(rentID_Text.getText());
            LocalDate startDate = start_Day_Picker.getValue();
            LocalDate endDate = endDate_Picker.getValue();
            double rentFee = Double.parseDouble(rentPerDay_Text.getText());
            CarDTO carID = cmb_CarID.getSelectionModel().getSelectedItem();
            CustomerDTO customerID = cmb_CustID.getSelectionModel().getSelectedItem();

            // Convert LocalDate to Date
//            Date sqlStartDate = Date.valueOf(startDate);
//            Date sqlEndDate = Date.valueOf(endDate);

            // Input Validation
            if (rentID <= 0 || carID == null || customerID == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid Rent ID or carID or CustomerID. Please enter valid values.").show();
                return;  // Exit the method if there's an error
            }
            // Create RentDTO with the retrieved data
            RentDTO rentDTO = new RentDTO(rentID, carID, customerID, startDate, endDate, rentFee);


            // Save the rent information
            rentService.rentSaved(rentDTO);

            new Alert(Alert.AlertType.CONFIRMATION, "Rent information saved successfully.").show();

            clear();
            //loadRentTable();
            initialize();

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving rent information: " + e.getMessage()).show();
        }
    }

    public void btn_RentListOnActionClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/All_Rent_List_Form.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Active Rent");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() throws Exception {
        col_RentID.setCellValueFactory(new PropertyValueFactory<>("rentId"));
        col_CustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        col_rentFee.setCellValueFactory(new PropertyValueFactory<>("rentFee"));
        col_StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col_EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        loadRentTable();
        loadAllCar();
        loadAllCustomers();

    }

    private void loadAllCustomers() throws Exception {
        List<CustomerDTO> allCustomerId = customerService.getAllCustomer();
        cmb_CustID.getItems().addAll(allCustomerId);

        // Set custom cell factory to display only car IDs
        cmb_CustID.setCellFactory(new Callback<ListView<CustomerDTO>, ListCell<CustomerDTO>>() {
            @Override
            public ListCell<CustomerDTO> call(ListView<CustomerDTO> param) {
                return new ListCell<CustomerDTO>() {
                    @Override
                    protected void updateItem(CustomerDTO item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(item.getCustomerId()));
                        }
                    }
                };
            }
        });
    }

    private void loadAllCar() throws Exception {
        List<CarDTO> allCarId = carService.getAllCars();
        cmb_CarID.getItems().addAll(allCarId);

        // Set custom cell factory to display only car IDs
        cmb_CarID.setCellFactory(new Callback<ListView<CarDTO>, ListCell<CarDTO>>() {
            @Override
            public ListCell<CarDTO> call(ListView<CarDTO> param) {
                return new ListCell<CarDTO>() {
                    @Override
                    protected void updateItem(CarDTO item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(item.getCarId()));
                        }
                    }
                };
            }
        });
    }


    private void loadRentTable() {
        try {
            List<RentDTO> rentList = rentService.getAllRent();
            if (rentList == null) {
                new Alert(Alert.AlertType.ERROR, "No Rent found.").show();
                return;
            }

            ObservableList<RentTableModel> obsList = getRentTableModels(rentList);
            rent_Table.setItems(obsList);
        } catch (Exception e) {
            // Log any exceptions that occur
            new Alert(Alert.AlertType.ERROR, "Error loading car: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private static ObservableList<RentTableModel> getRentTableModels(List<RentDTO> rentList) {
        ObservableList<RentTableModel> obsList = FXCollections.observableArrayList();
        for (RentDTO rentDTO : rentList) {
            LocalDate startDate = rentDTO.getRentDate();
            LocalDate endDate = rentDTO.getReturnDate();

            var tableModel = new RentTableModel(
                    rentDTO.getRentId(),
                    rentDTO.getCustomerID().getCustomerId(), startDate, endDate,
                    rentDTO.getRentFee());
            obsList.add(tableModel);
        }
        return obsList;
    }

    void clear() {
        rentID_Text.setText("");
        cmb_CarID.setValue(null);
        cmb_CustID.setValue(null);
        rentPerDay_Text.setText("");
        start_Day_Picker.setValue(null);
        endDate_Picker.setValue(null);

    }

    public void btn_ReturnCar_ClickOnAction(ActionEvent actionEvent) {
                try {
            long rentId = Long.parseLong(rentID_Text.getText());
            LocalDate returnDate = LocalDate.now();

            // Input validation
            if (rentId <= 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid Rent ID. Please enter a valid number").show();
                return;
            }

            // Call the service method to return the car
            rentService.returnCar(rentId, returnDate);

            new Alert(Alert.AlertType.CONFIRMATION, "Car returned successfully.").show();

            // Reload the Rentals table or update the specific row in the table
            loadRentTable();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Rent ID. Please enter a valid number").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error returning the car: " + e.getMessage()).show();
        }
    }

}
