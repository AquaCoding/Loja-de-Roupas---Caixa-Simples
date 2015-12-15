package br.com.redline.caixasimples.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import br.com.redline.caixasimples.Main;

public class CustomAlert {
	public static Optional<ButtonType> showAlert(String title, String content, AlertType alertType) {
		Alert a = new Alert(alertType);
		a.getDialogPane().getScene().getStylesheets().add(""+Main.class.getResource("application.css"));
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
       return  a.showAndWait();
	}
}
