package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Produto;
import br.com.redline.caixasimples.util.CustomAlert;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        
        // Obtem a resposta do usuario
        Optional<ButtonType> resultado = CustomAlert.showAlert("Produto - Remover", "Você tem certeza que deseja remover o produto " + remover.getNomeProduto()+"? Isso irá remover todas as entradas e vendas relacionadas com ele", AlertType.CONFIRMATION);
        if ((resultado.isPresent()) && (resultado.get() == ButtonType.OK)) {
        	remover.delete();
        	loadContent();
        	CustomAlert.showAlert("Produto - Remover", "Um produto foi removido com sucesso", AlertType.INFORMATION);
        }
	}
	
	@FXML
	public void editar() {
		Main.showViewNovoProduto(tProduto.getSelectionModel().getSelectedItem());
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
