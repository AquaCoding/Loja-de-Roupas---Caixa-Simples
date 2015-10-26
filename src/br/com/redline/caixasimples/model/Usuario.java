package br.com.redline.caixasimples.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import br.com.redline.caixasimples.util.BCrypt;
import br.com.redline.caixasimples.util.DatabaseConnect;

public class Usuario {

	// Atributos
	private int idUsuario;
	private String nome;
	private String senha;

	// Getters e Setters
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!nome.matches("^[a-zA-Zà-úÀ-Ú ]{3,45}$"))
			throw new RuntimeException("O valor de nome é inválido");

		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (!senha.matches("[\\S]{8,}"))
			throw new RuntimeException("O valor de senha é inválido");

		this.senha = senha;
	}

	// Construtores
	public Usuario(String nome, String senha) {
		setNome(nome);
		setSenha(senha);
	}

	public Usuario(int idUsuario, String nome, String senha) {
		setIdUsuario(idUsuario);
		setNome(nome);
		setSenha(senha);
	}

	// Cria um usuario
	public boolean create() {
		try {
			// Realiza o hash da senha
			this.senha = BCrypt.hashpw(this.senha, BCrypt.gensalt(12));

			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("INSERT INTO Usuario (nome, senha) VALUES (?, ?)");

			// Realiza o bind dos valores
			statement.setString(1, this.nome);
			statement.setString(2, this.senha);

			// Executa o SQL
			int ret = statement.executeUpdate();

			// Encerra conexao
			connect.close();

			// Retorna resultado
			if (ret == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu ao criar o usuário");
		}
	}

	// Obtem um usuario
	public static Usuario getById(int id) {
		// Result set get the result of the SQL query
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT idUsuario, nome, senha FROM Usuario WHERE idUsuario = ?");
			
			// Realiza o bind dos valores
			statement.setInt(1, id);

			// Executa o SQL
			ResultSet resultSet = statement.executeQuery();

			// Percorre pelo resultado
			if (resultSet.next()) {
				Usuario u = new Usuario(resultSet.getInt("idUsuario"),
						resultSet.getString("nome"),
						resultSet.getString("senha"));
				return u;
			} else {
				throw new RuntimeException("O usuário não existe");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Um erro ocorreu ao buscar o usuário");
		}
	}
}
