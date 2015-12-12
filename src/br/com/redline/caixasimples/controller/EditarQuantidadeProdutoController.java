package br.com.redline.caixasimples.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

public class EditarQuantidadeProdutoController implements Initializable {
	
	@FXML
	private TextField tfCodigo, tfQuantidade, tfCusto, tfFornecedor;
	
	@FXML
	private Button bAdicionar;
	
	@FXML
	private Label lbNome, lbDescricao, lbQuantidadeAtual, lbQuantidadeNova;
	
	private Produto p;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disableElements(true);
		
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
	    
	    StringConverter<BigDecimal> bigDecimalFormatter = new StringConverter<BigDecimal>() {
			@Override
			public BigDecimal fromString(String string) {
				BigDecimal a = new BigDecimal(string);
				if(a.signum() == -1) 
					return new BigDecimal(0);
					
				return a;
			}

			@Override
			public String toString(BigDecimal object) {
				if(object == null)
					return "0";
				
				return object.toPlainString();
			}
		};
		
		tfQuantidade.setTextFormatter(new TextFormatter<Integer>(intFormatter));
		tfCusto.setTextFormatter(new TextFormatter<BigDecimal>(bigDecimalFormatter));
	}
	
	@FXML
	public void buscar() {
		p = Produto.getByCodigoBarras(tfCodigo.getText());
		
		if(p != null) {
			disableElements(false);
			lbNome.setText(p.getNomeProduto());
			lbDescricao.setText(p.getDescricao());
			lbQuantidadeAtual.setText(""+p.getQtd());
			updateQuantidade();
		} else {
			disableElements(true);
		}
	}
	
	@FXML
	public void updateQuantidade() {
		if(!tfQuantidade.getText().equals("")) {
			lbQuantidadeNova.setText(""+ (p.getQtd() + Integer.parseInt(tfQuantidade.getText())));
		} else {
			lbQuantidadeNova.setText(""+p.getQtd());
		}
	}
	
	@FXML
	public void adicionar() {
		try {
			p.setFornecedor(tfFornecedor.getText());
			p.setPrecoCusto(new BigDecimal(tfCusto.getText()));
			p.setQtd(Integer.parseInt(lbQuantidadeNova.getText()));
			
			if(p.update()) {
				p.setQtd(Integer.parseInt(tfQuantidade.getText()));
				if(p.createEntradaEstoque()) {
					buscar();
					tfFornecedor.setText("");
					tfCusto.setText("0");
					tfQuantidade.setText("0");
					updateQuantidade();
					
					Alert a = new Alert(AlertType.INFORMATION);
			        a.setTitle("Nova entrada em estoque");
			        a.setHeaderText(null);
			        a.setContentText("Uma nova entrada em estoque foi cadastrada com sucesso");
			        a.showAndWait();
			        
			        Main.showViewCaixa();
				}
			}
			
		} catch (RuntimeException e) {
			Alert a = new Alert(AlertType.INFORMATION);
	        a.setTitle("Erro ao realizar entrada no estoque");
	        a.setHeaderText(null);
	        a.setContentText(e.getMessage());
	        a.showAndWait();
		}
	}
	
	private void disableElements(boolean isDisable) {
		tfQuantidade.setDisable(isDisable);
		tfCusto.setDisable(isDisable);
		tfFornecedor.setDisable(isDisable);
		bAdicionar.setDisable(isDisable);
	}
}
