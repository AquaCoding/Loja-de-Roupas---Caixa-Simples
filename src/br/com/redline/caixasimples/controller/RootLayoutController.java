package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class RootLayoutController {
	@FXML
	private MenuItem cadastrarCliente;
	
	@FXML
	public void cadastrarClienteClick() {
		Main.showViewCriarCliente();
	}
}
