package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML
	private Label lEntrar;
	
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
			lEntrar.setText("Nome de usuário ou senha incorretos");
		}
	}
}
