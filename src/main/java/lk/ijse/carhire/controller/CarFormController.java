package lk.ijse.carhire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lk.ijse.carhire.dto.CarDTO;
import lk.ijse.carhire.dto.CategoryDTO;
import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.dto.RentDTO;
import lk.ijse.carhire.dto.tablemodel.CarTableModel;
import lk.ijse.carhire.dto.tablemodel.CustomerTableModel;
import lk.ijse.carhire.dto.tablemodel.RentTableModel;
import lk.ijse.carhire.service.ServiceFactory;
import lk.ijse.carhire.service.custom.CarCategoryService;
import lk.ijse.carhire.service.custom.CarService;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

import java.time.LocalDate;
import java.util.List;

public class CarFormController {
    @FXML
    private TextField brand_Text;

    @FXML
    private RadioButton btn_Radiobtn1;

    @FXML
    private RadioButton btn_Radionbtn2;

    @FXML
    private TextField carID_Text;

    @FXML
    private TableView<CarTableModel> car_Table;

    @FXML
    private ComboBox<CategoryDTO> cmbo_Boxs;

    @FXML
    private TableColumn<CarTableModel, String> col_Brand;

    @FXML
    private TableColumn<CarTableModel, Long> col_CarID;
    @FXML
    private TableColumn<CarTableModel, Long> col_CategoryID;

    @FXML
    private TableColumn<CarTableModel, String> col_Model;

    @FXML
    private TableColumn<CarTableModel, Double> col_Rate;

    @FXML
    private TableColumn<CarTableModel, String> col_Vehicalumber;

    @FXML
    private TableColumn<CarTableModel, Integer> col_Year;

    @FXML
    private TextField model_Text;

    @FXML
    private TextField rate_Teaxt;

    @FXML
    private TextField vehicalNumber_Text;

    @FXML
    private TextField year_Text;

    CarService carService = (CarService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CAR);
CarCategoryService carCategoryService = (CarCategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CAR_CATEGORY);
    public void btn_SaveClickOnAction(ActionEvent actionEvent) throws Exception {
        try {
            long carID = Long.parseLong(carID_Text.getText());
            String vehicleNumber = vehicalNumber_Text.getText();
            String brand = brand_Text.getText();
            String model = model_Text.getText();
            int year = Integer.parseInt(year_Text.getText());  // Corrected the field name
            double dailyRate = Double.parseDouble(rate_Teaxt.getText());

            CategoryDTO selectedCategory = cmbo_Boxs.getSelectionModel().getSelectedItem();

            // Input Validation
            if (carID <= 0 || selectedCategory == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid Car ID or Category. Please enter valid values.").show();
                return;  // Exit the method if there's an error
            }

            var carDto = new CarDTO(carID, vehicleNumber, brand, model, year, dailyRate, selectedCategory);
                carService.carSaved(carDto);
                new Alert(Alert.AlertType.CONFIRMATION, "Car Successfully Saved").show();
            clear();
            //loadAllCars();
            initialize();
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid Car ID. Please enter a valid number").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    public void btn_UpdateClickOnAction(ActionEvent actionEvent) throws Exception {
        try{
            long carID = Long.parseLong(carID_Text.getText());
            String vehicleNumber = vehicalNumber_Text.getText();
            String brand = brand_Text.getText();
            String model = model_Text.getText();
            int year = Integer.parseInt(year_Text.getText());
            double dailyRate = Double.parseDouble(rate_Teaxt.getText());

            //CategoryDTO selectedCategory =  cmbo_Boxs.getSelectionModel().getSelectedItem();
            var carDto = new CarDTO(carID, vehicleNumber, brand, model, year, dailyRate);
            carService.carUpdate(carDto);
            new Alert(Alert.AlertType.CONFIRMATION,"Car Update successfully!").show();

        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Car ID format").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
        clear();
        loadAllCars();
    }


    public void btn_DeleteClickOnAction(ActionEvent actionEvent) {
        try {
            long carID = Long.parseLong(carID_Text.getText());

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this customer?",
                    ButtonType.YES, ButtonType.NO );
            confirmationAlert.showAndWait();

            if(confirmationAlert.getResult() == ButtonType.YES){
                var carDto = new CarDTO();
                carDto.setCarId(carID);
                carService.carDelete(carDto);
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Car Successfully Deleted").show();
            clear();
            loadAllCars();
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Car ID format").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btn_SearchClickOnAction(ActionEvent actionEvent) {
        try {
            long carID = Long.parseLong(carID_Text.getText());
            CarDTO carDtoID = carService.carSearch(carID);
            if(carDtoID !=null){
                carID_Text.setText(String.valueOf(carDtoID.getCarId()));
                vehicalNumber_Text.setText(carDtoID.getVehicleNumber());
                brand_Text.setText(carDtoID.getBrand());
                model_Text.setText(carDtoID.getModel());
                year_Text.setText(String.valueOf(carDtoID.getYear()));
                rate_Teaxt.setText(String.valueOf(carDtoID.getRate()));
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Car Not Found").show();
            }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Car ID format").show();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void initialize() throws Exception {
        col_CarID.setCellValueFactory(new PropertyValueFactory<>("carId"));
        col_CategoryID.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        col_Vehicalumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        col_Brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        col_Model.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_Year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_Rate.setCellValueFactory(new PropertyValueFactory<>("rate"));

        loadAllCars();
        loadCarCategory();
    }

        void clear(){
            carID_Text.setText("");
            vehicalNumber_Text.setText("");
            brand_Text.setText("");
            model_Text.setText("");
            year_Text.setText("");
            rate_Teaxt.setText("");
        }

    public void loadAllCars() throws Exception {
        try {
            List<CarDTO> rentList = carService.getAllCars();
            if (rentList == null) {
                new Alert(Alert.AlertType.ERROR, "No Rent found.").show();
                return;
            }

            ObservableList<CarTableModel> obsList = getCarTableModels(rentList);
            car_Table.setItems(obsList);
        } catch (Exception e) {
            // Log any exceptions that occur
            new Alert(Alert.AlertType.ERROR, "Error loading car: " + e.getMessage()).show();
            e.printStackTrace();
        }
//        List<CarDTO> carList = carService.getAllCars();
//        ObservableList<CarTableModel> observableList = FXCollections.observableArrayList();
//        for (CarDTO carDto : carList) {
//            var tableModel = new CarTableModel(
//                    carDto.getCarId(),
//                    //carDto.getCategoryDto().getCategory_id(),
//                    carDto.getVehicleNumber(),
//                    carDto.getBrand(),
//                    carDto.getModel(),
//                    carDto.getYear(),
//                    carDto.getRate());
//            observableList.add(tableModel);
//        }
//        car_Table.setItems(observableList);

    }

    private static ObservableList<CarTableModel> getCarTableModels(List<CarDTO> carList) {
        ObservableList<CarTableModel> obsList = FXCollections.observableArrayList();
        for (CarDTO carDTO : carList) {
           // LocalDate startDate = ren.getRentDate();
           // LocalDate endDate = rentDTO.getReturnDate();

            var tableModel = new CarTableModel(
                    carDTO.getCarId(),
                    carDTO.getCategoryDto().getCategory_id(),
                    carDTO.getVehicleNumber(), carDTO.getBrand(),
                    carDTO.getModel(),carDTO.getYear(), carDTO.getRate());
            obsList.add(tableModel);
        }
        return obsList;
    }

    private void loadCarCategory() throws Exception {
        List<CategoryDTO> allCategoryCars = carCategoryService.getAllCarCategory();
        cmbo_Boxs.getItems().addAll(allCategoryCars);

        // Set custom cell factory to display only category IDs
        cmbo_Boxs.setCellFactory(new Callback<ListView<CategoryDTO>, ListCell<CategoryDTO>>() {
            @Override
            public ListCell<CategoryDTO> call(ListView<CategoryDTO> param) {
                return new ListCell<CategoryDTO>() {
                    @Override
                    protected void updateItem(CategoryDTO item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(item.getCategory_id()));
                        }
                    }
                };
            }
        });
    }
}
