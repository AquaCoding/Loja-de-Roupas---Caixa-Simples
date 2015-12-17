package br.com.redline.caixasimples.model;

import java.math.BigDecimal;

public class ItemVenda {
	private int idVenda;
	private int idProduto;
	private String codigoBarras;
	private String nomeProduto;
	private BigDecimal precoVenda;
	private int qtd;
	private BigDecimal total;
	
	public int getIdVenda() {
		return idVenda;
	}
	
	public int getIdProduto() {
		return idProduto;
	}
	
	public String getCodigoBarras() {
		return codigoBarras;
	}
	
	public String getNomeProduto() {
		return nomeProduto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}
	
	public int getQtd() {
		return qtd;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public ItemVenda(int idVenda, int idProduto, String codigoBarras, String nomeProduto, BigDecimal precoVenda, int qtd, BigDecimal total) {
		this.idVenda = idVenda;
		this.idProduto = idProduto;
		this.codigoBarras = codigoBarras;
		this.nomeProduto = nomeProduto;
		this.precoVenda = precoVenda;
		this.qtd = qtd;
		this.total = total;
	}
}
