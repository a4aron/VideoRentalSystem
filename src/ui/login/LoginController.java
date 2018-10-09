package ui.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import controller.ControllerInterface;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.LoginException;
import controller.SystemController;
import ruleset.RuleException;
import ruleset.RuleSet;
import ruleset.RuleSetFactory;

import java.io.IOException;
import javafx.scene.image.Image;

public class LoginController {
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button signIn;

    public void initialize() {
        tfUsername.setOnAction(event -> {
            login_handler();
        });
        pfPassword.setOnAction(event -> {
            login_handler();
        });
    }
    public void login_handler(){
        try {
            RuleSet loginRules = RuleSetFactory.getRuleSet(LoginController.this);
            loginRules.applyRules(LoginController.this);
            ControllerInterface controller = SystemController.getInstance();
            controller.login(getUsername(), getPassword());

            Parent root = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
            Stage stage = new Stage();
            Image applicationIcon = new Image(getClass().getResourceAsStream("../image/file-video-icon.png"));
            stage.getIcons().add(applicationIcon);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Video Rental System");
            stage.setScene(new Scene(root));
            stage.show();

            tfUsername.clear();
            pfPassword.clear();
            Stage loginStage = (Stage) signIn.getScene().getWindow();
            loginStage.close();

        } catch (RuleException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (LoginException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Please check the credential.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getUsername(){
        return tfUsername.getText();
    }

    public String getPassword(){
        return pfPassword.getText();
    }

}
