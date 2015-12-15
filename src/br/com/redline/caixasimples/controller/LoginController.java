package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Usuario;
import br.com.redline.caixasimples.util.CustomAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {	
	@FXML
	private TextField tfUsuario;
	
	@FXML
	private PasswordField pfSenha;
	
	@FXML
	private Button bEntrar;
	
	@FXML
	public void bEntrarClick() {
		boolean senhaCorreta = Usuario.isValidSenha(this.tfUsuario.getText(), this.pfSenha.getText());
		
		if(senhaCorreta) {
			Main.initRootLayout();
			Main.showViewCaixa();
			Main.endLoginLayout();
		} else {
			CustomAlert.showAlert("Caixa - Entrar", "Nome de usuário ou senha incorretos", AlertType.INFORMATION);
		}
	}
}
