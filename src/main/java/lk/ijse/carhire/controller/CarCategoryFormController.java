package lk.ijse.carhire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.carhire.dto.CategoryDTO;
import lk.ijse.carhire.dto.tablemodel.CarCategoryTableModel;
import lk.ijse.carhire.service.ServiceFactory;
import lk.ijse.carhire.service.custom.CarCategoryService;
import lk.ijse.carhire.service.custom.serviceimpl.CarCategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CarCategoryFormController {
    @FXML
    private TableView<CarCategoryTableModel> carCategory_Table;

    @FXML
    private TextField categoryName_Text;

    @FXML
    private TextField categoryID_Text;

    @FXML
    private TableColumn<?, ?> col_categoryID;

    @FXML
    private TableColumn<?, ?> col_categoryName;
    private static final Logger logger = LoggerFactory.getLogger(CarCategoryFormController.class);
    CarCategoryService categoryService = (CarCategoryService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.CAR_CATEGORY);
    public void btn_SaveClickOn(ActionEvent actionEvent) {
        try {
            long carCategoryID = Long.parseLong(categoryID_Text.getText());
             String categoryName = categoryName_Text.getText();

        if(carCategoryID<=0){
            new Alert(Alert.AlertType.ERROR, "Invalid Car Category ID. Please enter a valid Positive number");
            return;
        }

            if (categoryName.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Category Name cannot be empty").show();
                return;
            }

        var carCategoryDTO = new CategoryDTO(carCategoryID, categoryName);
            categoryService.carCategorySaved(carCategoryDTO);

            new Alert(Alert.AlertType.CONFIRMATION, "Car Category Successfully Saved").show();
            clear();
            initialize();
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.CONFIRMATION, "Invalid Car Category ID. Please enter a valid number").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
    }
    public void btn_UpdateClickOnAction(ActionEvent actionEvent) throws Exception {
        try {
            long carCategoryID = Long.parseLong(categoryID_Text.getText());
            String categoryName = categoryName_Text.getText();

            var carCategoryDTO = new CategoryDTO(carCategoryID, categoryName);
            categoryService.carCategoryUpdate(carCategoryDTO);

            new Alert(Alert.AlertType.CONFIRMATION, "Car Category Successfully Updated").show();

            // Clear fields and reload after success
            clear();
            initialize();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Car Category ID format").show();
        } catch (Exception e) {
            // Log the exception and show an error alert
            logger.error("Error updating car category", e);
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btn_DeleteClickOnAction(ActionEvent actionEvent) {
        try{
            long  carCategoryID = Long.parseLong(categoryID_Text.getText());

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this Category ID?",
                    ButtonType.YES, ButtonType.NO );
            confirmationAlert.showAndWait();

            if(confirmationAlert.getResult() == ButtonType.YES){
                var categoryDTO = new CategoryDTO();
                categoryDTO.setCategory_id(carCategoryID);
                categoryService.carCategoryDelete(categoryDTO);
                new Alert(Alert.AlertType.CONFIRMATION, "CategoryID Successfully Deleted").show();
                clear();
                initialize();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "CategoryID Can not be Deleted").show();
            }

        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Category ID format").show();
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void btn_SearchClickOnAction(ActionEvent actionEvent) {
        try {
            long carCategoryID = Long.parseLong(categoryID_Text.getText());

            // Validation check
            if(carCategoryID<=0){
                new Alert(Alert.AlertType.ERROR, "Invalid Car Category ID. Please enter a positive number").show();
                return;
            }
            CategoryDTO categoryDto = categoryService.search(carCategoryID);

            if(categoryDto != null){
                categoryID_Text.setText(String.valueOf(categoryDto.getCategory_id()));
                categoryName_Text.setText(categoryDto.getCategory_Name());
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Category Not Found").show();
            }

        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid Car Category ID format").show();

        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() throws Exception {
        col_categoryID.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        col_categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        loadAllCarCategory();
    }

    private void loadAllCarCategory() throws Exception{
        List<CategoryDTO> carCategory = categoryService.getAllCarCategory();
        ObservableList<CarCategoryTableModel> observableList = FXCollections.observableArrayList();

        for (CategoryDTO category: carCategory) {
            var categoryTable = new CarCategoryTableModel(category.getCategory_id(), category.getCategory_Name());
            observableList.add(categoryTable);
        }
        carCategory_Table.setItems(observableList);
    }

    public void clear(){
        categoryID_Text.setText("");
        categoryName_Text.setText("");
    }


}
