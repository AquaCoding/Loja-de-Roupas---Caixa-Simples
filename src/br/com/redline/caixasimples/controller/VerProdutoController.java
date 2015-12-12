package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import br.com.redline.caixasimples.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerProdutoController implements Initializable {
	
	private ArrayList<Produto> produtos;
	
	@FXML
	TableView<Produto> tProduto;
	
	@FXML
	TableColumn<Produto, String> tcCodigo, tcNome, tcEstoque, tcPreco;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadContent();
		setTable();		
	}

	private void loadContent() {
		produtos = Produto.getAll();
		tProduto.setItems(FXCollections.observableArrayList(produtos));
	}
	
	private void setTable() {
		tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tcEstoque.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tcPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
	}
}
