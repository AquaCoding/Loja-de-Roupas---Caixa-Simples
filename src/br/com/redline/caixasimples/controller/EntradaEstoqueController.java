package br.com.redline.caixasimples.controller;

import java.io.IOException;

import br.com.redline.caixasimples.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EntradaEstoqueController {
	@FXML
	private BorderPane bpMain;
	
	@FXML
	public void abrirEstoqueNovoProduto() throws IOException {
		// Carrega o FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/NovoProduto.fxml"));
		AnchorPane personOverview = (AnchorPane) loader.load();
		
		// Define o FXML no borderPane
		bpMain.setCenter(personOverview);
	}
	
	@FXML
	public void abrirEstoqueProdutoExistente() throws IOException {
		// Carrega o FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/EditarQuantidadeProduto.fxml"));
		AnchorPane personOverview = (AnchorPane) loader.load();
		
		// Define o FXML no borderPane
		bpMain.setCenter(personOverview);
	}
}
