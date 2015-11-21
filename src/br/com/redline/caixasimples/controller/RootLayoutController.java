package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import javafx.fxml.FXML;

public class RootLayoutController {
	
	@FXML
	public void cadastrarClienteClick() {
		Main.showViewCriarCliente();
	}
	
	@FXML
	public void novoEstoqueClick() {
		Main.showViewEntradaEstoque();
	}
}
