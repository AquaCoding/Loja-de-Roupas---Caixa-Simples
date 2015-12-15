package br.com.redline.caixasimples.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Cliente;
import br.com.redline.caixasimples.util.CustomAlert;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class CriarClienteController implements Initializable {
	
	private Cliente c;
	
	@FXML
	private TextField tfNome, tfSobrenome, tfRua, tfNumero, tfBairro, tfTelefone, tfEmail;
	
	@FXML
	private Button bCadastrar1;
	
	@FXML
	public void cancelar() {
		Main.showViewCaixa();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		StringConverter<Integer> intFormatter = new StringConverter<Integer>() {
			@Override
			public Integer fromString(String string) {
				if(Integer.parseInt(string) > 0)
					return Integer.parseInt(string);
				
				return 0;
			}

			@Override
			public String toString(Integer object) {
				if(object == null)
					return "0";
				
				return object.toString();
			}
	    };
	    
	    tfNumero.setTextFormatter(new TextFormatter<Integer>(intFormatter));
	}
	
	public void setCliente(Cliente cliente) {
		if(cliente != null) {
			// Define os dados do cliente no formulario
			tfNome.setText(cliente.getNome());
			tfSobrenome.setText(cliente.getSobrenome());
			tfRua.setText(cliente.getRua());
			tfNumero.setText(""+cliente.getNumero());
			tfBairro.setText(cliente.getBairro());
			tfTelefone.setText(cliente.getTelefone());
			tfEmail.setText(cliente.getEmail());
			
			// Salva o cliente para uso posterior
			c = cliente;
			
			// Atualiza texto do botão de cadastro
			bCadastrar1.setText("Atualizar");
			
			// Altera o evento de click do botão de cadastro
			bCadastrar1.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					// Atualiza o cliente com os novos dados informados
					c.setNome(tfNome.getText());
					c.setSobrenome(tfSobrenome.getText());
					c.setRua(tfRua.getText());
					c.setNumero(Integer.parseInt(tfNumero.getText()));
					c.setBairro(tfBairro.getText());
					c.setTelefone(tfTelefone.getText());
					c.setEmail(tfEmail.getText());

					if(c.update()) {
						CustomAlert.showAlert("Cliente - Atualização", "O cliente " + c.getNome() + " " + c.getSobrenome() + " foi atualizado com sucesso", AlertType.INFORMATION);			            
			            Main.showClientes();
					}
				}
			});
		}
	}
	
	@FXML
	public void cadastrarClick() {
		if(tfNumero.getText().equals(""))
			tfNumero.setText("-1");
		
		try {
			Cliente c = new Cliente(tfNome.getText(), tfSobrenome.getText(), tfRua.getText(), Integer.parseInt(tfNumero.getText()), tfBairro.getText(), tfTelefone.getText(), tfEmail.getText());
			c.create();
			
			CustomAlert.showAlert("Cliente - Cadastro", "Um cliente foi cadastrado com sucesso", AlertType.INFORMATION);
			Main.showViewCaixa();
		} catch (RuntimeException e) {			
			CustomAlert.showAlert("Cliente - Cadastro", e.getMessage(), AlertType.INFORMATION);
		}
	}	
}
