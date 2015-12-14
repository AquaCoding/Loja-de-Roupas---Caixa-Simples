package br.com.redline.caixasimples.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.EntradaProduto;
import br.com.redline.caixasimples.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class NovoProdutoController implements Initializable {
	@FXML
	private TextField tfCodigo, tfQuantidade, tfCusto, tfFornecedor, tfNome, tfPreco;
	
	@FXML
	private TextArea taDescricao;
	
	@FXML
	private TableView<EntradaProduto> tvEntradaEstoque;
	
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
		tfPreco.setTextFormatter(new TextFormatter<BigDecimal>(bigDecimalFormatter));
		tvEntradaEstoque.setVisible(false);
	}
	
	@FXML
	public void cadastraProduto() {
		try {
			if(!tfQuantidade.getText().equals("") && !tfPreco.getText().equals("")) {
				Produto produto = new Produto(tfNome.getText(), tfCodigo.getText(), taDescricao.getText(), Integer.parseInt(tfQuantidade.getText()), new BigDecimal(tfPreco.getText()), new BigDecimal(tfCusto.getText()), tfFornecedor.getText());
				
				if(produto.create())
					if(produto.createEntradaEstoque()) {
						Alert a = new Alert(AlertType.INFORMATION);
				        a.setTitle("Produto cadastrado com sucesso");
				        a.setHeaderText(null);
				        a.setContentText("Um produto foi cadastrado com sucesso");
				        a.showAndWait();
				        
				        Main.showViewCaixa();
					}
			}
		} catch (Exception e) {
			String message;
			if(e.getMessage() == null) {
				message = "Preço precisa ser um número";
			} else if(e.getMessage().matches("^For input string: \"[\\S ]{1,}\"$")) {
				message = "Quantidade precisa ser um número";
			} else {
				message = e.getMessage();
			}
			Alert a = new Alert(AlertType.INFORMATION);
	        a.setTitle("Um erro ocorreu ao criar o produto");
	        a.setHeaderText(null);
	        a.setContentText(message);
	        a.showAndWait();
		}
	}
}
