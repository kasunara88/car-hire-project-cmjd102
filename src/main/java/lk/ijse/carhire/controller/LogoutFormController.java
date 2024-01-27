package lk.ijse.carhire.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.carhire.db.DB;

import java.io.IOException;

public class LogoutFormController {
    public TextField txt_userName;
    public PasswordField txt_UserPassword;
    public AnchorPane loging_RootNode;


    public void btnClickOnActionPerform(ActionEvent actionEvent) throws IOException {
        String user = txt_userName.getText();
        String pass = txt_UserPassword.getText();
        try{
            if(authenticateUser(user, pass)){
                dashboardPage();
            }else {
                clear();
                new Alert(Alert.AlertType.ERROR, "Username or Password Incorrect").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred during login").show();
        }
    }
    public void dashboardPage() throws IOException {
        String user = txt_userName.getText();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/DashBoard_Form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage primaryStage = (Stage) this.loging_RootNode.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    private boolean authenticateUser(String username, String password) {
        // Implement your authentication logic here, including password hashing
        return username.equals(DB.username) && password.equals(DB.password);
    }

    public void clear(){
        txt_UserPassword.setText("");
    }
}
