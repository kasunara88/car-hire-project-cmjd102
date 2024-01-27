package lk.ijse.carhire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {
    public AnchorPane dashboard_NodeRoot;
    public AnchorPane dashboard_BodyRoot;
    public Label user_name;

    public void btn_Customer_OnActionPerforms(ActionEvent actionEvent) throws IOException {
        Parent customer_NoteRoot = FXMLLoader.load(this.getClass().getResource("/view/Customer_Form.fxml"));
        this.dashboard_BodyRoot.getChildren().clear();
        this.dashboard_BodyRoot.getChildren().add(customer_NoteRoot);

    }

    public void btn_CarsClickOnActionPerform(ActionEvent actionEvent) throws IOException {
        Parent cars_NoteRood = FXMLLoader.load(this.getClass().getResource("/view/Car_Form.fxml"));
        this.dashboard_BodyRoot.getChildren().clear();
        this.dashboard_BodyRoot.getChildren().add(cars_NoteRood);
    }

    public void btn_RentClickOnAction_Perform(ActionEvent actionEvent) throws IOException {
        Parent rent_root = FXMLLoader.load(this.getClass().getResource("/view/Rent_Form.fxml"));
        this.dashboard_BodyRoot.getChildren().clear();;
        this.dashboard_BodyRoot.getChildren().add(rent_root);
    }

    public void btn_logiutClickOnAction(ActionEvent actionEvent) throws IOException {
        Parent logoutForm = FXMLLoader.load(this.getClass().getResource("/view/Lgout_Form.fxml"));
        Scene scene = new Scene(logoutForm);
        Stage stage = (Stage) this.dashboard_NodeRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public void btn_CarCategory_ClickOnAction(ActionEvent actionEvent) throws IOException {
        Parent carCategory_NodeRoot = FXMLLoader.load(this.getClass().getResource("/view/CarCategory_Form.fxml"));
        this.dashboard_BodyRoot.getChildren().clear();
        this.dashboard_BodyRoot.getChildren().add(carCategory_NodeRoot);
    }

}
