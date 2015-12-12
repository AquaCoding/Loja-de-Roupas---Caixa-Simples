package br.com.redline.caixasimples.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntradaProduto {
	private int idEntrada, idProduto;
	private Date data;
	private int qtd;
	private BigDecimal precoEntrada;
	private String fornecedor;

	public int getIdEntrada() {
		return idEntrada;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public String getData() {
		SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
		return format.format(data);
	}

	public int getQtd() {
		return qtd;
	}

	public BigDecimal getPrecoEntrada() {
		return precoEntrada;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setIdEntrada(int idEntrada) {
		this.idEntrada = idEntrada;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public void setPrecoEntrada(BigDecimal precoEntrada) {
		this.precoEntrada = precoEntrada;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public EntradaProduto(int idEntrada, int idProduto, Date data, int qtd, BigDecimal precoEntrada, String fornecedor) {
		setIdEntrada(idEntrada);
		setIdProduto(idProduto);
		setData(data);
		setQtd(qtd);
		setPrecoEntrada(precoEntrada);
		setFornecedor(fornecedor);
	}
}
