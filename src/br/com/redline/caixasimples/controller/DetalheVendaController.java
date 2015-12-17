package br.com.redline.caixasimples.controller;

import java.util.ArrayList;
import br.com.redline.caixasimples.model.ItemVenda;
import br.com.redline.caixasimples.model.Venda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DetalheVendaController {

	@FXML
	private Label lCodigo, lData, lTotal;
	
	@FXML
	private TableView<ItemVenda> tProdutos;
	
	@FXML
	private TableColumn<ItemVenda, String> tcCodigo, tcNome, tcQuantidade, tcPreco, tcTotal;
	
	@FXML
	private ArrayList<ItemVenda> produtos;
	
	public void setVenda(Venda venda) {
		lCodigo.setText(""+venda.getIdVenda());
		lData.setText(venda.getData());
		lTotal.setText(""+venda.getTotal());
		loadContent(venda.getIdVenda());
		setTable();
	}
	
	private void loadContent(int idVenda) {
		produtos = Venda.getAllItens(idVenda);
		tProdutos.setItems(FXCollections.observableArrayList(produtos));
	}
	
	private void setTable() {
		tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tcPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
	}
}
