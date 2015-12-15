package br.com.redline.caixasimples.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

public class CaixaController implements Initializable {
	
	@FXML
	private TableView<Produto> tbVenda, tbCaixa;
	
	@FXML
	private TableColumn<Produto, String> tcCaixaCodigo, tcCaixaNome, tcCaixaEstoque, tcCaixaPreco, tcVendaCodigo, tcVendaNome, tcVendaEstoque, tcVendaPreco;
	
	@FXML
	private Button bAdicionar, bRemover;
	
	@FXML
	private TextField tfCodigo, tfQuantidade, tfDescontoProduto, tfDescontoVenda, tfValorPagamento, tfTroco;
	
	@FXML
	private Label lbValorTotal;
	
	private ArrayList<Produto> produtos, venda = new ArrayList<Produto>();
	
	private BigDecimal totalVenda;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadContent();
		setTableCaixa();
		setTableVenda();

		// Adiciona as mascaras no campo
	    MaskField.intMask(tfQuantidade);
		MaskField.moneyMask(tfDescontoProduto);
		MaskField.moneyMask(tfDescontoVenda);
		MaskField.moneyMask(tfValorPagamento);
	}
	
	@FXML
	public void buscar() {
		produtos = Produto.getAllWithFilters(tfCodigo.getText());
		tbCaixa.setItems(FXCollections.observableArrayList(produtos));
	}
	
	@FXML
	public void adicionarVenda() {
		if(tbCaixa.getSelectionModel().getSelectedItem() != null && !tfQuantidade.getText().equals("") && Integer.parseInt(tfQuantidade.getText()) > 0) {
			// Obtem o produto selecionado
			Produto p = tbCaixa.getSelectionModel().getSelectedItem();
			
			// Verifica quantidade de estoque
			if(Integer.parseInt(tfQuantidade.getText()) > p.getQtd()) {
				CustomAlert.showAlert("Caixa - Controle de estoque", "A quantidade informada é maior que a quantidade em estoque", AlertType.INFORMATION);
			} else {
				tbVenda.setItems(null);
				tbVenda.layout();
				
				BigDecimal desconto = new BigDecimal(0);
				if(!tfDescontoProduto.getText().equals("")) {
					desconto = new BigDecimal(tfDescontoProduto.getText());
				}
				
				
				boolean haveProduto = false;
				for(Produto pv : venda) {
					if(pv.getCodigoBarras().equals(p.getCodigoBarras())) {
						// Obtem o precoVenda e aplica desconto
						BigDecimal precoVenda = new BigDecimal(p.getPrecoVenda());
						precoVenda = precoVenda.subtract(desconto);
						pv.setPrecoVenda(precoVenda);
						
						// Aplica a quantidade de item vendido
						pv.setQtd(Integer.parseInt(tfQuantidade.getText()));
						haveProduto = true;
					}
				}
				
				if(!haveProduto) {
					// Obtem o precoVenda e aplica desconto
					BigDecimal precoVenda = new BigDecimal(p.getPrecoVenda());
					precoVenda = precoVenda.subtract(desconto);
					p.setPrecoVenda(precoVenda);
					
					// Aplica a quantidade de item vendido
					p.setQtd(Integer.parseInt(tfQuantidade.getText()));
					venda.add(p);
				}
				
				// Define os items na tabela da venda
				tbVenda.setItems(FXCollections.observableArrayList(venda));
				tbVenda.refresh();
				
				// Atualiza os valores da venda
				updateTotal();
				loadContent();
				updateTroco();
				updateDesconto();
				
				// Retorna para os valores padrões de codgio, quantidade e desconto produto
				tfCodigo.setText("");
				tfQuantidade.setText("");
				tfDescontoProduto.setText("");
			}
		}
	}
	
	@FXML
	public void removeItem() {
		// Obtem o valor selecionado
		Produto p = tbVenda.getSelectionModel().getSelectedItem();
		
		// Verifica se algo estava selecionado
		if(p != null) {
			// Remove, atualiza interface e total
			venda.remove(venda.indexOf(p));
			tbVenda.setItems(FXCollections.observableArrayList(venda));
			tbVenda.refresh();
			
			// Se a venda estiver vazia reseta os valores de desconto e troco
			if(venda.size() == 0) {
				tfDescontoVenda.setText("");
				tfValorPagamento.setText("");
				tfTroco.setText("");
			}
			
			// Atualiza interface
			updateTotal();
			updateTroco();
			updateDesconto();
		}
	}
	
	@FXML
	public void updateTroco() {
		BigDecimal pagamento = new BigDecimal(0);
		if(!tfValorPagamento.getText().equals("") && !tfValorPagamento.getText().equals("-")) {
			pagamento = new BigDecimal(tfValorPagamento.getText());
		}
		
		if(pagamento.signum() != -1) {
			BigDecimal troco = pagamento.subtract(totalVenda);
			tfTroco.setText(""+troco);
		}
	}
	
	@FXML
	public void updateDesconto() {
		BigDecimal desconto = new BigDecimal(0);
		
		if(!tfDescontoVenda.getText().equals("") && !tfDescontoVenda.getText().equals("-") && !tfDescontoVenda.getText().matches("[\\D]+")) {
			desconto = new BigDecimal(tfDescontoVenda.getText());
		}
		
		if(desconto.signum() != -1) {
			BigDecimal total = getTotal();
			BigDecimal novoTotal = total.subtract(desconto);
			
			if(novoTotal.signum() != -1) {
				totalVenda = novoTotal;
				lbValorTotal.setText("R$"+novoTotal);
				updateTroco();
			}
		}
	}
	
	@FXML
	public void finalizarVenda() {
		if(venda != null && venda.size() > 0)
			try {
				if(Produto.vender(venda)) {
					// Cria uma noticação
					CustomAlert.showAlert("Caixa - Venda", "Troco R$" + tfTroco.getText(), AlertType.INFORMATION);
			        
			        // Limpa os campos
			        tfDescontoVenda.setText("");
			        tfValorPagamento.setText("");
			        tfTroco.setText("");
			        venda.clear();
			        tbVenda.setItems(FXCollections.observableArrayList(venda));
					tbVenda.refresh();
			        updateTotal();
			        
			        // Atualiza a lista de produtos do caixa
			        loadContent();
				}
			} catch (Exception e) {
				CustomAlert.showAlert("Caixa - Venda", e.getMessage(), AlertType.INFORMATION);
			}
	}
	
	private void loadContent() {
		produtos = Produto.getAll();
		tbCaixa.setItems(FXCollections.observableArrayList(produtos));
	}
	
	private void setTableCaixa() {
		tcCaixaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
		tcCaixaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tcCaixaEstoque.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tcCaixaPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
	}
	
	private void setTableVenda() {
		tcVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
		tcVendaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tcVendaEstoque.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tcVendaPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
	}
	
	private void updateTotal() {
		lbValorTotal.setText("R$"+getTotal());
	}
	
	private BigDecimal getTotal() {
		BigDecimal total = new BigDecimal(0);
		for(Produto pv : venda) {
			BigDecimal preco = new BigDecimal(pv.getPrecoVenda());
			preco = preco.multiply(new BigDecimal(pv.getQtd()));
			total = total.add(preco);
		}
		
		totalVenda = total;
		return total;
	}
}
