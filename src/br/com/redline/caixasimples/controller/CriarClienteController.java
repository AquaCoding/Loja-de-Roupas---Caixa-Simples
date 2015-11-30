package br.com.redline.caixasimples.controller;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Cliente;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class CriarClienteController {
	
	private Cliente c;
	
	@FXML
	private TextField tfNome, tfSobrenome, tfRua, tfNumero, tfBairro, tfTelefone, tfEmail;
	
	@FXML
	private Button bCadastrar1;
	
	@FXML
	public void cancelar() {
		Main.showViewCaixa();
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
						Alert a = new Alert(AlertType.INFORMATION);
			            a.setTitle("Atualização de cliente");
			            a.setHeaderText("Confirmação de atualização");
			            a.setContentText("O cliente " + c.getNome() + " " + c.getSobrenome() + " foi atualizado com sucesso");
			            a.showAndWait();
			            
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
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setHeaderText(null);
		
		try {
			Cliente c = new Cliente(tfNome.getText(), tfSobrenome.getText(), tfRua.getText(), Integer.parseInt(tfNumero.getText()), tfBairro.getText(), tfTelefone.getText(), tfEmail.getText());
			c.create();
			
			alert.setTitle("Cliente cadastrado com sucesso");
			alert.setContentText("O cliente " + tfNome.getText() + " foi cadastrado com sucesso");
			alert.showAndWait();
			Main.showViewCaixa();
		} catch (RuntimeException e) {
			if(e.getMessage().equals("O valor de numero é inválido"))
				tfNumero.setText("");
			
			alert.setTitle("Um erro ocorreu ao criar o cliente");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
