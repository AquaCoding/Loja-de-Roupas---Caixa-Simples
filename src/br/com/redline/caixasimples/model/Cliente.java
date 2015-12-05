package br.com.redline.caixasimples.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import br.com.redline.caixasimples.util.DatabaseConnect;
import com.mysql.jdbc.PreparedStatement;

public class Cliente {
	private int idCliente;
	private String nome;
	private String sobrenome;
	private String rua;
	private int numero;
	private String bairro;
	private String telefone;
	private String email;

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		try {
			this.idCliente = idCliente;
		} catch (Exception e) {
			throw new RuntimeException("O ID do cliente é inválido");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!nome.matches("^[a-zA-Zà-úÀ-Ú ]{1,45}$"))
			throw new RuntimeException("O valor de nome é inválido");
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		if (!sobrenome.matches("^[a-zA-Zà-úÀ-Ú ]{3,45}$"))
			throw new RuntimeException("O valor de sobrenome é inválido");
		this.sobrenome = sobrenome;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		if (!rua.matches("^[a-zA-Zà-úÀ-Ú1-9 ]{1,45}$"))
			throw new RuntimeException("O valor de rua é inválido");
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		if (numero < 0)
			throw new RuntimeException("O valor de numero é inválido");
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		if (!bairro.matches("^[a-zA-Zà-úÀ-Ú1-9 -_]{1,45}$"))
			throw new RuntimeException("O valor de bairro é inválido");
		this.bairro = bairro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		if (!telefone.matches("^[\\d]{10,15}$"))
			throw new RuntimeException("O valor de telefone é inválido");
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (!email.matches("^[\\w]+@[\\w]+\\.[\\w.]+"))
			throw new RuntimeException("O valor de email é inválido");
		this.email = email;
	}

	public Cliente(String nome, String sobrenome, String rua, int numero,
			String bairro, String telefone, String email) {
		setNome(nome);
		setSobrenome(sobrenome);
		setRua(rua);
		setNumero(numero);
		setBairro(bairro);
		setTelefone(telefone);
		setEmail(email);
	}

	public Cliente(int idCliente, String nome, String sobrenome, String rua,
			int numero, String bairro, String telefone, String email) {
		setIdCliente(idCliente);
		setNome(nome);
		setSobrenome(sobrenome);
		setRua(rua);
		setNumero(numero);
		setBairro(bairro);
		setTelefone(telefone);
		setEmail(email);
	}

	public boolean create() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("INSERT INTO Cliente (nome, sobrenome, rua, numero, bairro, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			// Realiza o bind dos valores
			statement.setString(1, this.nome);
			statement.setString(2, this.sobrenome);
			statement.setString(3, this.rua);
			statement.setInt(4, this.numero);
			statement.setString(5, this.bairro);
			statement.setString(6, this.telefone);
			statement.setString(7, this.email);

			// Executa o SQL
			int ret = statement.executeUpdate();

			// Retorna resultado
			if (ret == 1) {
				//Define o id a classe
				ResultSet id = statement.getGeneratedKeys();
				while(id.next())
					setIdCliente(id.getInt(1));
				
				// Encerra conexao
				connect.close();
				return true;
			} else {
				// Encerra conexao
				connect.close();
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu ao criar o cliente");
		}
	}

	public static ArrayList<Cliente> getAll() {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			Statement statement = connect.createStatement();
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Cliente");
			
			ArrayList<Cliente> clienteReturn = new ArrayList<Cliente>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Cliente c = new Cliente(resultSet.getInt("idCliente"), 
										resultSet.getString("nome"), 
										resultSet.getString("sobrenome"),
										resultSet.getString("rua"), 
										resultSet.getInt("numero"), 
										resultSet.getString("bairro"), 
										resultSet.getString("telefone"), 
										resultSet.getString("email"));
				
				// Adiciona o cliente ao retorno
				clienteReturn.add(c);
			}
			
			// Retorna os clientes
			return clienteReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public static ArrayList<Cliente> getAllWithFilters(String filter) {
		try{
			// Define % para o LIKE procurar no meio do conteudo
			filter = "%" + filter + "%";
			
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Cliente WHERE nome LIKE ? OR "
																+ "sobrenome LIKE ? OR "
																+ "rua LIKE ? OR "
																+ "numero LIKE ? OR "
																+ "bairro LIKE ? OR "
																+ "telefone LIKE ? OR "
																+ "email LIKE ?");
			
			// Faz bind dos parametros
			statement.setString(1, filter);
			statement.setString(2, filter);
			statement.setString(3, filter);
			statement.setString(4, filter);
			statement.setString(5, filter);
			statement.setString(6, filter);
			statement.setString(7, filter);
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery();

			ArrayList<Cliente> clienteReturn = new ArrayList<Cliente>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Cliente c = new Cliente(resultSet.getInt("idCliente"), 
										resultSet.getString("nome"), 
										resultSet.getString("sobrenome"),
										resultSet.getString("rua"), 
										resultSet.getInt("numero"), 
										resultSet.getString("bairro"), 
										resultSet.getString("telefone"), 
										resultSet.getString("email"));
				
				// Adiciona o cliente ao retorno
				clienteReturn.add(c);
			}
			
			// Retorna os clientes
			return clienteReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public boolean update() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("UPDATE Cliente SET nome = ?, sobrenome = ?, rua = ?, numero = ?, bairro = ?, telefone = ?, email = ? WHERE idCliente = ?");

			// Realiza o bind dos valores
			statement.setString(1, this.nome);
			statement.setString(2, this.sobrenome);
			statement.setString(3, this.rua);
			statement.setInt(4, this.numero);
			statement.setString(5, this.bairro);
			statement.setString(6, this.telefone);
			statement.setString(7, this.email);
			statement.setInt(8, this.idCliente);

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
			e.printStackTrace();
			throw new RuntimeException("Um erro ocorreu ao atualizar o cliente");
		}
	}

	public boolean delete() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("DELETE FROM Cliente WHERE idCliente = ?");

			// Realiza o bind dos valores
			statement.setInt(1, this.idCliente);

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
			e.printStackTrace();
			throw new RuntimeException("Um erro ocorreu ao deletar o cliente");
		}
	}
}
