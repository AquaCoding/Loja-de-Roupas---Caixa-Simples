package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Usuario;

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
		
		// Cria um alert de confirmação
		Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Remoção de usuário");
        a.setHeaderText(null);
        a.setContentText("Você tem certeza que deseja remover o cliente " + remover.getNome());
        
        // Obtem a resposta do usuario
        Optional<ButtonType> resultado = a.showAndWait();
        if ((resultado.isPresent()) && (resultado.get() == ButtonType.OK)) {
        	try {
        		if(remover.delete()) {
        			loadContent();
        			showAlert("Remoção de usuário", "O usuário selecionado foi removido com sucesso");
        		}
        	} catch(RuntimeException e ) {
        		showAlert("Um erro ocorreu", e.getMessage());
        	}            
        }
	}
	
	private void showAlert(String title, String content) {
		Alert b = new Alert(AlertType.INFORMATION);
        b.setTitle(title);
        b.setHeaderText(null);
        b.setContentText(content);
        b.showAndWait();
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
