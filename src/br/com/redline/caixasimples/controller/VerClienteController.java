package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Cliente;

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
		Main.showViewCriarCliente(null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadContent();
		setTable();
	}
	
	@FXML
	public void buscar() {
		clientes = Cliente.getAllWithFilters(tfBusca.getText());
		tCliente.setItems(FXCollections.observableArrayList(clientes));
	}
	
	@FXML
	public void editar() {
		Main.showViewCriarCliente(tCliente.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void remover() {
		Cliente remover = tCliente.getSelectionModel().getSelectedItem();
		
		// Cria um alert de confirmação
		Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Remoção de cliente");
        a.setHeaderText(null);
        a.setContentText("Você tem certeza que deseja remover o cliente " + remover.getNome() + " " + remover.getSobrenome());
        
        // Obtem a resposta do usuario
        Optional<ButtonType> resultado = a.showAndWait();
        if ((resultado.isPresent()) && (resultado.get() == ButtonType.OK)) {
        	remover.delete();
        	loadContent();
        	
        	Alert b = new Alert(AlertType.INFORMATION);
            b.setTitle("Remoção de cliente");
            b.setHeaderText(null);
            b.setContentText("O cliente foi removido com sucesso");
            b.showAndWait();
            
        }
	}
	
	private void loadContent() {
		clientes = Cliente.getAll();
		tCliente.setItems(FXCollections.observableArrayList(clientes));
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
