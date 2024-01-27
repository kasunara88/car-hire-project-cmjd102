//package lk.ijse.carhire.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import lk.ijse.carhire.dto.RentDTO;
//import lk.ijse.carhire.dto.tablemodel.RentListTable;
//import lk.ijse.carhire.dto.tablemodel.RentTableModel;
//import lk.ijse.carhire.service.ServiceFactory;
//import lk.ijse.carhire.service.custom.RentService;
//
//import java.util.List;
//
//public class AllRentListFormController {
//    @FXML
//    private TableColumn<?, ?> co_RentID;
//
//    @FXML
//    private TableColumn<?, ?> col_CarID;
//
//    @FXML
//    private TableColumn<?, ?> col_CustID;
//
//    @FXML
//    private TableColumn<?, ?> col_EndDate;
//
//    @FXML
//    private TableColumn<?, ?> col_StartingDate;
//
//    @FXML
//    private TableColumn<?, ?> col_Total;
//
//    @FXML
//    private TableView<?> rentList_Table;
//
//    @FXML
//    private TextField txt_CustomerID;
//
//    RentService rentService = (RentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.RENT);
//    public void initialize() {
//        co_RentID.setCellValueFactory(cellData -> cellData.getValue().rentIdProperty().asObject());
//       col_CustID.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());
//        col_CarID.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
//        col_StartingDate.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
//        col_EndDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
//        col_Total.setCellValueFactory(cellData -> cellData.getValue().rentFeeProperty().asObject());
//
//        loadRentData();
//    }
//
//    private void loadRentData() {
//        try {
//            List<RentDTO> rentList = rentService.getAllRent();
//            if (rentList == null || rentList.isEmpty()) {
//                new Alert(Alert.AlertType.ERROR, "No rentals found.").show();
//                return;
//            }
//            ObservableList<RentListTable> rentTableModelList = getRentTableModels(rentList);
//            rentList_Table.setItems(rentTableModelList);
//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "Error loading rentals: " + e.getMessage()).show();
//            e.printStackTrace();
//        }
//    }
//
//    private ObservableList<RentListTable> getRentTableModels(List<RentDTO> rentList) {
//        ObservableList<RentListTable> rentTableModelList = FXCollections.observableArrayList();
//        for (RentDTO rentDTO : rentList) {
//            RentListTable rentTableModel = new RentListTable(
//                    rentDTO.getRentId(),
//                    rentDTO.getCustomerID(),
//                    rentDTO.getCarID(),
//                    rentDTO.getRentDate().toString(), // Convert LocalDate to String as needed
//                    rentDTO.getReturnDate().toString(),   // Convert LocalDate to String as needed
//                    rentDTO.getRentFee()
//            );
//            rentTableModelList.add(rentTableModel);
//        }
//        return rentTableModelList;
//    }
//}
