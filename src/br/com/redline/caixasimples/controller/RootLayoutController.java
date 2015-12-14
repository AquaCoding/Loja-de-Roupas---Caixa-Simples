package br.com.redline.caixasimples.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import br.com.redline.caixasimples.Main;

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
		Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Ajuda");
        a.setHeaderText(null);
        a.setContentText("Nós contate com seu problema pelo e-mail: suporte@redline.com.br");
        a.showAndWait();
	}
	
	@FXML
	public void sobre() {
		Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Sobre");
        a.setHeaderText(null);
        a.setContentText("Desenvolvido por RedLine\nVersão: 1.0");
        a.showAndWait();
	} 
}
