package br.com.redline.caixasimples.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.EntradaProduto;
import br.com.redline.caixasimples.model.Produto;
import br.com.redline.caixasimples.util.CustomAlert;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class NovoProdutoController implements Initializable {
	@FXML
	private TextField tfCodigo, tfQuantidade, tfCusto, tfFornecedor, tfNome, tfPreco;
	
	@FXML
	private TextArea taDescricao;
	
	@FXML
	private TableView<EntradaProduto> tvEntradaEstoque;
	
	@FXML
	private TableColumn<EntradaProduto, String> tcData, tcFornecedor, tcQuantidade, tcCusto;
			
	@FXML
	private Button bAdicionar;
	
	@FXML
	private Label lQuantidade, lCusto, lFornecedor;
	
	private ArrayList<EntradaProduto> entradas;
	
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
						CustomAlert.showAlert("Produto - Cadastro", "Um produto foi cadastrado com sucesso", AlertType.INFORMATION);
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
			CustomAlert.showAlert("Produto - Cadastro", message, AlertType.INFORMATION);
		}
	}
	
	public void setProduto(Produto produto) {
		// Desativa campos não modificaveis
		tfCodigo.setDisable(true);
		tfQuantidade.setDisable(true);
		
		// Oculta os campos não utilizados
		lFornecedor.setVisible(false);
		tfFornecedor.setDisable(true);
		tfFornecedor.setVisible(false);
		lCusto.setVisible(false);
		tfCusto.setDisable(true);
		tfCusto.setVisible(false);
		
		// Define os campos
		tfCodigo.setText(produto.getCodigoBarras());
		tfQuantidade.setText(""+produto.getQtd());
		tfNome.setText(produto.getNomeProduto());
		tfPreco.setText(produto.getPrecoVenda());
		taDescricao.setText(produto.getDescricao());
		
		// Exibe a tabela de entradas desse produto
		tvEntradaEstoque.setVisible(true);
		
		loadContent(produto.getCodigoBarras());
		setTable();
		
		// Altera os labels
		lQuantidade.setText("Estoque");
		bAdicionar.setText("Atualizar");
		
		// Define a função de editar
		bAdicionar.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {
					if(!tfPreco.getText().equals("")) {
						produto.setNomeProduto(tfNome.getText());
						produto.setPrecoVenda(new BigDecimal(tfPreco.getText()));
						produto.setDescricao(taDescricao.getText());
						
						if(produto.update()) {
							CustomAlert.showAlert("Produto - Atualização", "Um produto foi atualizado com sucesso", AlertType.INFORMATION);					        
					        Main.showViewCaixa();
						}
					}
				} catch (Exception e) {
					String message;
					if(e.getMessage() == null) {
						message = "Preço precisa ser um número";
					} else {
						message = e.getMessage();
					}
					CustomAlert.showAlert("Produto - Atualização", message, AlertType.INFORMATION);
				}
			}
		});
	}
	
	private void loadContent(String codigoBarras) {
		entradas = Produto.getAllEntradasByCodigoBarras(codigoBarras);
		tvEntradaEstoque.setItems(FXCollections.observableArrayList(entradas));
	}
	
	private void setTable() {
		tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tcFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tcCusto.setCellValueFactory(new PropertyValueFactory<>("precoEntrada"));
	}
}
