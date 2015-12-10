package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Usuario;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class CriarUsuarioController {
	private boolean closeAfterCreate = false;
	
	@FXML
	private VBox vBox;
	
	@FXML
	private TextField tfNome;
	
	@FXML
	private PasswordField pfSenha, pfConfirmaSenha, pfSenhaAtual = new PasswordField();
	
	@FXML
	private Label lSenhaAtual = new Label("Senha Atual"), lSenha;
	
	@FXML
	private Button bCadastrar;
		
	public void setCloseAfterCreate(boolean close) {
		closeAfterCreate = close;
	}
	
	@FXML
	public void createUsuario() {
		if(pfSenha.getText().equals("") || pfConfirmaSenha.getText().equals("") || tfNome.getText().equals("")) {
			showAlert("Campos obrigatórios", "Todos os campos são obrigatórios");
		} else {
			if(pfSenha.getText().equals(pfConfirmaSenha.getText())) {
				try {
					Usuario u = new Usuario(tfNome.getText(), pfSenha.getText());
					if(u.create()) {
						showAlert("Usuário criado", "O usuário foi criado com sucesso");
						if(closeAfterCreate) {
							Main.endLoginLayout();
							Main.initLoginLayout();
						} else {
							Main.showViewCaixa();
						}
					}
				} catch (RuntimeException e) {
					showAlert(e.getMessage(), e.getMessage());
				}
			} else {
				showAlert("Senhas incorretas", "As senhas informadas não batem");
			}
		}
	}
	
	@FXML
	public void cancelar() {
		if(closeAfterCreate) {
			Main.endLoginLayout();
		} else {
			Main.showViewCaixa();
		}
	}
	
	private void showAlert(String title, String content) {
		Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
	}
	
	public void setUsuario(Usuario usuario) {
		// Define os campos auto preenchidos
		tfNome.setText(usuario.getNome());
		
		// Cria os campos para confirmar senha
		vBox.getChildren().add(6, lSenhaAtual);		
		vBox.getChildren().add(7, pfSenhaAtual);
		
		// Altera os textos de elementos
		lSenha.setText("Nova senha");
		bCadastrar.setText("Atualizar");
		
		// Altera o evento de clique no botão de Atualizar
		bCadastrar.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Verifica se a senha atual esta correta
				if(Usuario.isValidSenha(usuario.getNome(), pfSenhaAtual.getText())) {
					try {
						boolean update = true;
						
						// Obtem os dados originais do usuario
						Usuario u = Usuario.getById(usuario.getIdUsuario());
									
						// Define os dados modificados
						u.setNome(tfNome.getText());
						if(!pfSenha.equals("")) {
							if(pfSenha.getText().equals(pfConfirmaSenha.getText())) {
								u.setSenha(pfSenha.getText());
							} else {
								Alert a = new Alert(AlertType.INFORMATION);
					            a.setTitle("Atualização de usuário");
					            a.setHeaderText(null);
					            a.setContentText("A nova senha não coincide com a confirmação");
					            a.showAndWait();
					            update = false;
							}
						}
												
						// Atualiza o usuario
						if(update && u.update()) {
							Alert a = new Alert(AlertType.INFORMATION);
				            a.setTitle("Atualização de usuário");
				            a.setHeaderText(null);
				            a.setContentText("O usuário foi atualizado");
				            a.showAndWait();
				            
				            Main.showUsuarios();
						}
					} catch (RuntimeException e) {
						Alert a = new Alert(AlertType.INFORMATION);
			            a.setTitle("Atualização de usuário");
			            a.setHeaderText(null);
			            a.setContentText(e.getMessage());
			            a.showAndWait();
					}
					
				} else {
					Alert a = new Alert(AlertType.INFORMATION);
		            a.setTitle("Atualização de usuário");
		            a.setHeaderText(null);
		            a.setContentText("A senha informada esta incorreta");
		            a.showAndWait();
				}
			}
		});
	}
}
