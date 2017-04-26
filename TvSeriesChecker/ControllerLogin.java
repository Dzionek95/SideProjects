package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControllerLogin implements Initializable {

@FXML
private Label labelMessage;
@FXML
private TextField loginField;
@FXML
private PasswordField passwordField;
@FXML
private Button button;



public void loginButton(ActionEvent action) throws IOException {
    DBQueries q=new DBQueries();
    try {
        if(q.loginChecker(loginField.getText(), passwordField.getText())){
            Node source= (Node) action.getSource();
            Stage stageClose=(Stage)source.getScene().getWindow();
            stageClose.close();
            Parent parent= FXMLLoader.load(getClass().getResource("FXML/Main.fxml"));
            Stage stage=new Stage();
            stage.setTitle("TvSeries Checker");
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        else
            labelMessage.setText("Login or password is wrong");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.defaultButtonProperty().bind(button.focusedProperty());
    }
}
