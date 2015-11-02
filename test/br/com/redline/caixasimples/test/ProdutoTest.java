package br.com.redline.caixasimples.test;

import static org.junit.Assert.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import br.com.redline.caixasimples.model.Produto;
import br.com.redline.caixasimples.util.DatabaseConnect;

public class ProdutoTest {

	private Produto p;

	@Before
	public void criaProduto() {
		try {
			Connection connect = DatabaseConnect.getInstance();
			InputStream in = ProdutoTest.class
					.getResourceAsStream("sqls/produto.sql");
			DatabaseConnect.importSQL(connect, in);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		p = new Produto("Produto A", "1234567891234", "Um belo produto", 5, new BigDecimal(9.99));
		assertEquals(true, p.create());
	}

	@Test(expected = RuntimeException.class)
	public void setNomeProdutoErrado() {
		p.setNomeProduto("A");
	}

	@Test(expected = RuntimeException.class)
	public void setCodigoBarrasErrado() {
		p.setCodigoBarras("A");
	}

	@Test(expected = RuntimeException.class)
	public void setDescricaoErrado() {
		p.setDescricao("A");
	}

	@Test(expected = RuntimeException.class)
	public void setqtdErrado() {
		p.setQtd(-10);
	}

	@Test(expected = RuntimeException.class)
	public void setPrecoVendaErrado() {
		p.setPrecoVenda(new BigDecimal(-5.80));
	}

	@Test
	public void getAll() {
		p = new Produto("Produto B", "1234567891234", "Nice produto", 10, new BigDecimal(2.55));
		p.create();

		ArrayList<Produto> produtos = Produto.getAll();

		assertEquals(2, produtos.size());
		assertEquals(1, produtos.get(0).getIdProduto());
		assertEquals("Produto A", produtos.get(0).getNomeProduto());
		assertEquals(2, produtos.get(1).getIdProduto());
		assertEquals("Produto B", produtos.get(1).getNomeProduto());
		assertEquals("2.55", produtos.get(1).getPrecoVenda());
	}

	@Test
	public void atualizaProduto() {
		p.setPrecoVenda(new BigDecimal(1.99));
		assertEquals(true, p.update());
		ArrayList<Produto> produtos = Produto.getAll();
		assertEquals("1.99", produtos.get(0).getPrecoVenda());
	}
	
	@Test
	public void deletaProduto() {
		assertEquals(true, p.delete());
		ArrayList<Produto> produtos = Produto.getAll();
		assertEquals(0, produtos.size());
	}
	
	@Test
	public void vendaProduto() {
		assertEquals(true, p.vender(2));
		ArrayList<Produto> produtos = Produto.getAll();
		assertEquals(3, produtos.get(0).getQtd());
	}
	
	@Test(expected=RuntimeException.class)
	public void vendaProdutoComErro() {
		p.vender(6);
	}
}
