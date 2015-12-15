package br.com.redline.caixasimples.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.util.CustomAlert;

public class RootLayoutController {
	
	@FXML
	private Menu mSobre;
	
	@FXML
	public void caixaClick() {
		Main.showViewCaixa();
	}
	
	@FXML
	public void cadastrarClienteClick() {
		Main.showViewCriarCliente(null);
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
	
	@FXML
	public void criarUsuarioClick() {
		Main.showViewCriarUsuario(null);
	}
	
	@FXML
	public void obterAjuda() {
		CustomAlert.showAlert("Ajuda", "N�s contate com seu problema pelo e-mail: suporte@redline.com.br", AlertType.INFORMATION);
	}
	
	@FXML
	public void sobre() {
		CustomAlert.showAlert("Ajuda", "Desenvolvido por RedLine\nVers�o: 1.0", AlertType.INFORMATION);
	} 
}
