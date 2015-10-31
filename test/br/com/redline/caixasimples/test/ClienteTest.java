package br.com.redline.caixasimples.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import br.com.redline.caixasimples.model.Cliente;
import br.com.redline.caixasimples.util.DatabaseConnect;

public class ClienteTest {

	private Cliente c;
	
	@Before
	public void criaCliente() {
		try {
			Connection connect = DatabaseConnect.getInstance();
			InputStream in = ClienteTest.class.getResourceAsStream("sqls/cliente.sql");
			DatabaseConnect.importSQL(connect, in);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c = new Cliente("Rodrigo", "Sobrenome", "Rua A", 10, "Bairro A", "1233333333", "email@example.com");
		assertEquals(true, c.create());
	}
	
	@Test
	public void testaClienteContato() {
		assertEquals(true, c.create());
	}
	
	@Test(expected=RuntimeException.class)
	public void setNomeErrado() {
		c.setNome("A");
	}
	
	@Test(expected=RuntimeException.class)
	public void setSobrenomeErrado() {
		c.setSobrenome("A");
	}
	
	@Test(expected=RuntimeException.class)
	public void setRuaErrado() {
		c.setRua("A");
	}
	
	@Test(expected=RuntimeException.class)
	public void setNumeroErrado() {
		c.setNumero(-10);
	}
	
	@Test(expected=RuntimeException.class)
	public void setBairroErrado() {
		c.setBairro("A");
	}
	
	@Test(expected=RuntimeException.class)
	public void setTelefoneErrado() {
		c.setTelefone("A");
	}
	
	@Test(expected=RuntimeException.class)
	public void setEmailErrado() {
		c.setEmail("A");
	}
}
