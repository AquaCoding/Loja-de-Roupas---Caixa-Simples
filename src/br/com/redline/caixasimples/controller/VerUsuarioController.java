package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Usuario;
import br.com.redline.caixasimples.util.CustomAlert;

public class VerUsuarioController implements Initializable {

	@FXML
	private TableView<Usuario> tUsuario;
	
	@FXML
	private TableColumn<Usuario, String> tcCodigo, tcLogin;
	
	private ArrayList<Usuario> usuarios;
	
	@FXML
	public void abreCriarUsuario() {
		Main.showViewCriarUsuario(null);
	}
	
	@FXML
	public void editar() {
		Main.showViewCriarUsuario(tUsuario.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void remover() {
		Usuario remover = tUsuario.getSelectionModel().getSelectedItem();
		
		// Cria um alert de confirmação e obtem a resposta do usuario
        Optional<ButtonType> resultado = CustomAlert.showAlert("Usuário - Remover", "Você tem certeza que deseja remover o cliente " + remover.getNome()+"?", AlertType.CONFIRMATION);
        if ((resultado.isPresent()) && (resultado.get() == ButtonType.OK)) {
        	try {
        		if(remover.delete()) {
        			loadContent();
        			CustomAlert.showAlert("Usuário - Remover", "Um usuário foi removido com sucesso", AlertType.INFORMATION);
        		}
        	} catch(RuntimeException e ) {
        		CustomAlert.showAlert("Usuário - Remover",e.getMessage(), AlertType.INFORMATION);
        	}            
        }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadContent();
		setTable();
	}
	
	private void loadContent() {
		usuarios = Usuario.getAll();
		tUsuario.setItems(FXCollections.observableArrayList(usuarios));
	}
	
	private void setTable() {
		tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
		tcLogin.setCellValueFactory(new PropertyValueFactory<>("nome"));
	}
}
