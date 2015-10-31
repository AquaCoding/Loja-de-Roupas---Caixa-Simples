package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class CriarClienteController {

	@FXML
	private TextField tfNome, tfSobrenome, tfRua, tfNumero, tfBairro, tfTelefone, tfEmail;
	
	@FXML
	public void cancelar() {
		Main.showViewCaixa();
	}
	
	@FXML
	public void cadastrarClick() {
		if(tfNumero.getText().equals(""))
			tfNumero.setText("-1");
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setHeaderText(null);
		
		try {
			Cliente c = new Cliente(tfNome.getText(), tfSobrenome.getText(), tfRua.getText(), Integer.parseInt(tfNumero.getText()), tfBairro.getText(), tfTelefone.getText(), tfEmail.getText());
			c.create();
			
			alert.setTitle("Cliente cadastrado com sucesso");
			alert.setContentText("O cliente " + tfNome.getText() + " foi cadastrado com sucesso");
			alert.showAndWait();
			Main.showViewCaixa();
		} catch (RuntimeException e) {
			tfNumero.setText("");
			alert.setTitle("Um erro ocorreu ao criar o cliente");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
