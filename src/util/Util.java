package util;

import javafx.scene.control.Alert;

public class Util {

    public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}
