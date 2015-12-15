package br.com.redline.caixasimples.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import br.com.redline.caixasimples.Main;
import br.com.redline.caixasimples.model.EntradaProduto;
import br.com.redline.caixasimples.model.Produto;
import br.com.redline.caixasimples.util.CustomAlert;
import br.com.redline.caixasimples.util.MaskField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditarQuantidadeProdutoController implements Initializable {
	
	@FXML
	private TextField tfCodigo, tfQuantidade, tfCusto, tfFornecedor;
	
	@FXML
	private Button bAdicionar;
	
	@FXML
	private Label lbNome, lbDescricao, lbQuantidadeAtual, lbQuantidadeNova;
	
	@FXML
	private TableView<EntradaProduto> tEntradas;
	
	@FXML
	private TableColumn<EntradaProduto, String> tcData, tcFornecedor, tcQuantidade, tcCusto;
	
	private Produto p;
	private ArrayList<EntradaProduto> entradas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disableElements(true);		
		MaskField.intMask(tfQuantidade);
		MaskField.moneyMask(tfCusto);
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
			loadContent(tfCodigo.getText());
			setTable();
		} else {
			disableElements(true);
			loadContent(null);
			lbNome.setText("");
			lbDescricao.setText("");
			lbQuantidadeAtual.setText("");
			lbQuantidadeNova.setText("");
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
			if(tfQuantidade.getText().equals("") || tfCusto.getText().equals(""))
				throw new RuntimeException("Todos os campos são obriatórios");
			
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
					
					CustomAlert.showAlert("Estoque - Entrada", "Uma nova entrada em estoque foi cadastrada com sucesso", AlertType.INFORMATION);			        
			        Main.showViewCaixa();
				}
			}
			
		} catch (RuntimeException e) {
			CustomAlert.showAlert("Estoque - Entrada", e.getMessage(), AlertType.INFORMATION);
		}
	}
	
	private void disableElements(boolean isDisable) {
		tfQuantidade.setDisable(isDisable);
		tfCusto.setDisable(isDisable);
		tfFornecedor.setDisable(isDisable);
		bAdicionar.setDisable(isDisable);
	}
	
	private void loadContent(String codigoBarras) {
		entradas = Produto.getAllEntradasByCodigoBarras(codigoBarras);
		tEntradas.setItems(FXCollections.observableArrayList(entradas));
	}
	
	private void setTable() {
		tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tcFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tcCusto.setCellValueFactory(new PropertyValueFactory<>("precoEntrada"));
	}
}
