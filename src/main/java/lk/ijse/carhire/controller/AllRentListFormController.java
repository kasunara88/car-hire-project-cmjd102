package lk.ijse.carhire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.carhire.dto.RentDTO;
import lk.ijse.carhire.dto.tablemodel.RentListTable;
import lk.ijse.carhire.dto.tablemodel.RentTableModel;
import lk.ijse.carhire.service.ServiceFactory;
import lk.ijse.carhire.service.custom.RentService;

import java.util.List;

public class AllRentListFormController {
    @FXML
    private TableColumn<?,?> co_RentID;

    @FXML
    private TableColumn<?,?> col_CarID;

    @FXML
    private TableColumn<?,?> col_CustID;

    @FXML
    private TableColumn<?, ?> col_EndDate;

    @FXML
    private TableColumn<?, ?> col_StartingDate;

    @FXML
    private TableColumn<?, ?> col_Total;

    @FXML
    private TableView<RentListTable> rentList_Table;

    @FXML
    private TextField txt_CustomerID;

    RentService rentService = (RentService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.RENT);
    public void initialize() {
        co_RentID.setCellValueFactory(new PropertyValueFactory<>("rentId"));
       col_CustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        col_CarID.setCellValueFactory(new PropertyValueFactory<>("carID"));
        col_StartingDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col_EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("rentFee"));
        loadRentData();
    }

    private void loadRentData() {
        try {
            List<RentDTO> rentList = rentService.getAllRent();
            if (rentList == null || rentList.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "No rentals found.").show();
                return;
            }
            ObservableList<RentListTable> rentTableModelList = getRentTableModels(rentList);
            rentList_Table.setItems(rentTableModelList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading rentals: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private ObservableList<RentListTable> getRentTableModels(List<RentDTO> rentList) {
        ObservableList<RentListTable> rentTableModelList = FXCollections.observableArrayList();
        for (RentDTO rentDTO : rentList) {
            RentListTable rentTableModel = new RentListTable(
                    rentDTO.getRentId(),
                    rentDTO.getCustomerID().getCustomerId(),
                    rentDTO.getCarID().getCarId(),
                    rentDTO.getRentDate(),
                    rentDTO.getReturnDate(),
                    rentDTO.getRentFee()
            );
            rentTableModelList.add(rentTableModel);
        }
        return rentTableModelList;
    }
}
