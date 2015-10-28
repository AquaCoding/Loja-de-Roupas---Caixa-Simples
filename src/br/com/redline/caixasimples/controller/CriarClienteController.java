package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import javafx.fxml.FXML;

public class CriarClienteController {

	@FXML
	public void cancelar() {
		Main.showViewCaixa();
	}
}
