package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Venda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistroVendaController implements Initializable {

	@FXML
	private TableView<Venda> tVendas;
	
	@FXML
	private TableColumn<Venda, String> tcCodigo, tcData, tcTotal;
	
	private ArrayList<Venda> vendas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadContent();
		setTable();
	}
	
	@FXML
	public void abreDetalheVenda() {
		Main.showViewDetalheVenda(tVendas.getSelectionModel().getSelectedItem());
	}

	private void loadContent() {
		vendas = Venda.getAll();
		tVendas.setItems(FXCollections.observableArrayList(vendas));
	}
	
	private void setTable() {
		tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
		tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
	}
}
