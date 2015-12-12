package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerProdutoController implements Initializable {
	
	private ArrayList<Produto> produtos;
	
	@FXML
	private TableView<Produto> tProduto;
	
	@FXML
	private TableColumn<Produto, String> tcCodigo, tcNome, tcEstoque, tcPreco;
	
	@FXML
	private TextField tfBusca;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadContent();
		setTable();		
	}
	
	@FXML
	public void buscar() {
		produtos = Produto.getAllWithFilters(tfBusca.getText());
		tProduto.setItems(FXCollections.observableArrayList(produtos));
	}
	
	@FXML
	public void novoProduto() {
		Main.showViewEntradaEstoque();
	}

	@FXML
	public void remover() {
		Produto remover = tProduto.getSelectionModel().getSelectedItem();
		
		// Cria um alert de confirmação
		Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Remoção de produto");
        a.setHeaderText(null);
        a.setContentText("Você tem certeza que deseja remover o produto " + remover.getNomeProduto()+"? Isso irá remover todas as entradas relacionadas com ele.");
        
        // Obtem a resposta do usuario
        Optional<ButtonType> resultado = a.showAndWait();
        if ((resultado.isPresent()) && (resultado.get() == ButtonType.OK)) {
        	remover.delete();
        	loadContent();
        	
        	Alert b = new Alert(AlertType.INFORMATION);
            b.setTitle("Remoção de produto");
            b.setHeaderText(null);
            b.setContentText("O produto foi removido com sucesso");
            b.showAndWait();
        }
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
