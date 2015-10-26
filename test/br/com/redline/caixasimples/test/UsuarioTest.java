package br.com.redline.caixasimples.test;

import static org.junit.Assert.*;
import org.junit.Test;

import br.com.redline.caixasimples.model.Usuario;

public class UsuarioTest {

	private Usuario u;
	
	@Test
	public void criaUsuario() {
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
	
	@Test(expected=RuntimeException.class)
	public void criaUsuarioComErro() {
		u.create();
	}
	
	@Test
	public void obtemUsuario() {
		u = Usuario.getById(1);
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
}
