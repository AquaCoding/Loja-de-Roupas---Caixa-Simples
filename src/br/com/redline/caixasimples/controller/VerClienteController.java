package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerClienteController implements Initializable {
	
	@FXML 
	private TextField tfBusca;
	
	// TABELA
	@FXML
	private TableView<Cliente> tCliente;
	
	@FXML
	private TableColumn<Cliente, String> tcNome;
	
	@FXML
	private TableColumn<Cliente, String> tcSobrenome;
	
	@FXML
	private TableColumn<Cliente, String> tcRua;
	
	@FXML
	private TableColumn<Cliente, String> tcNumero;
	
	@FXML
	private TableColumn<Cliente, String> tcBairro;
	
	@FXML
	private TableColumn<Cliente, String> tcTelefone;
	
	@FXML
	private TableColumn<Cliente, String> tcEmail;
	
	private ArrayList<Cliente> clientes;
	
	@FXML
	public void novoClienteClick() {
		Main.showViewCriarCliente();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientes = Cliente.getAll();
		tCliente.setItems(FXCollections.observableArrayList(clientes));
		setTable();
	}
	
	@FXML
	public void buscar() {
		clientes = Cliente.getAllWithFilters(tfBusca.getText());
		tCliente.setItems(FXCollections.observableArrayList(clientes));
		setTable();
	}
	
	private void setTable() {
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tcSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
		tcRua.setCellValueFactory(new PropertyValueFactory<>("rua"));
		tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tcBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
		tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
	}
}
