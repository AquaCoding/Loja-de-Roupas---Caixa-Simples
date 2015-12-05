package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class criarUsuarioController {
	private boolean closeAfterCreate = false;
	
	@FXML
	private TextField tfNome;
	
	@FXML
	private PasswordField pfSenha, pfConfirmaSenha;
	
	public void setCloseAfterCreate(boolean close) {
		closeAfterCreate = close;
	}
	
	@FXML
	public void createUsuario() {
		if(pfSenha.getText().equals("") || pfConfirmaSenha.getText().equals("") || tfNome.getText().equals("")) {
			showAlert("Campos obrigat�rios", "Todos os campos s�o obrigat�rios");
		} else {
			if(pfSenha.getText().equals(pfConfirmaSenha.getText())) {
				try {
					Usuario u = new Usuario(tfNome.getText(), pfSenha.getText());
					if(u.create()) {
						showAlert("Usu�rio criado", "O usu�rio foi criado com sucesso");
						if(closeAfterCreate) {
							Main.endLoginLayout();
							Main.initLoginLayout();
						} else {
							Main.showViewCaixa();
						}
					}
				} catch (RuntimeException e) {
					showAlert(e.getMessage(), e.getMessage());
				}
			} else {
				showAlert("Senhas incorretas", "As senhas informadas n�o batem");
			}
		}
	}
	
	@FXML
	public void cancelar() {
		if(closeAfterCreate) {
			Main.endLoginLayout();
		}
	}
	
	private void showAlert(String title, String content) {
		Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
	}
}
