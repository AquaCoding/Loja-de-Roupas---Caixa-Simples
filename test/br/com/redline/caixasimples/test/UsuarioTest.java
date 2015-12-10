package br.com.redline.caixasimples.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import br.com.redline.caixasimples.model.Usuario;
import br.com.redline.caixasimples.util.DatabaseConnect;

public class UsuarioTest {

	private Usuario u;

	// Recria a tabela usuario e inseri um valor valido antes de todos os testes
	@Before
	public void criaUsuario() {
		try {
			Connection connect = DatabaseConnect.getInstance();
			InputStream in = UsuarioTest.class.getResourceAsStream("sqls/usuario.sql");
			DatabaseConnect.importSQL(connect, in);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		u = new Usuario("Rodrigo", "123456789");
		assertEquals(true, u.create());
	}

	@Test
	public void criaUsuarioNomeInvalido() {
		try {
			u = new Usuario("a", "123456789");
			fail();
		} catch (RuntimeException e) {
			assertEquals("O valor de nome é inválido", e.getMessage());
		}
	}

	@Test
	public void criaUsuarioSenhaInvalido() {
		try {
			u = new Usuario("Rodrigo", "123");
			fail();
		} catch (RuntimeException e) {
			assertEquals("O valor de senha é inválido", e.getMessage());
		}
	}

	@Test(expected = RuntimeException.class)
	public void criaUsuarioComErro() {
		u.create();
	}

	@Test
	public void obtemUsuarioPorId() {
		u = Usuario.getById(1);
		assertEquals(1, u.getIdUsuario());
		assertEquals("Rodrigo", u.getNome());
	}

	@Test
	public void obtemUsuarioPorNome() {
		u = Usuario.getByNome("Rodrigo");
		assertEquals(1, u.getIdUsuario());
		assertEquals("Rodrigo", u.getNome());
	}

	@Test
	public void obtemUsuarioInvalido() {
		try {
			u = Usuario.getById(2);
			fail();
		} catch (RuntimeException e) {
			assertEquals("O usuário não existe", e.getMessage());
		}
	}
	
	@Test
	public void verificaSenhaCorreta() {
		assertEquals(true, Usuario.isValidSenha("Rodrigo", "123456789"));
	}
	
	@Test
	public void verificaSenhaErrada() {
		assertEquals(false, Usuario.isValidSenha("Rodrigo", "12345678"));
	}
	
	@Test
	public void getAll() {
		u = new Usuario("Usuario B", "12345678");
		u.create();
		
		ArrayList<Usuario> usuarios = Usuario.getAll();
		
		assertEquals(2, usuarios.size());
		assertEquals(1, usuarios.get(0).getIdUsuario());
		assertEquals("Rodrigo", usuarios.get(0).getNome());
		assertEquals(2, usuarios.get(1).getIdUsuario());
		assertEquals("Usuario B", usuarios.get(1).getNome());
	}
	
	@Test
	public void atualizaUsuario() {
		u.setNome("Usuario B");
		u.setSenha("Minha nova Senha");
		assertEquals(true, u.update());
		assertEquals(true, Usuario.isValidSenha("Usuario B", "Minha nova Senha"));
	}
	
	@Test(expected=RuntimeException.class)
	public void deletaUsuario() {
		assertEquals(true, u.delete());
		ArrayList<Usuario> usuarios = Usuario.getAll();
		assertEquals(0, usuarios.size());
	}
}
