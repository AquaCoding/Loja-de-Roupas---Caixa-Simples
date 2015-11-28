package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import javafx.fxml.FXML;

public class RootLayoutController {
	
	@FXML
	public void caixaClick() {
		Main.showViewCaixa();
	}
	
	@FXML
	public void cadastrarClienteClick() {
		Main.showViewCriarCliente();
	}
	
	@FXML
	public void novoEstoqueClick() {
		Main.showViewEntradaEstoque();
	}
	
	@FXML
	public void verTodosUsuariosClick() {
		Main.showUsuarios();
	}
	
	@FXML
	public void verTodosProdutosClick() {
		Main.showProdutos();
	}
	
	@FXML
	public void verTodosClientesClick() {
		Main.showClientes();
	}
}
