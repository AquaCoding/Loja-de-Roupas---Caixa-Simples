package br.com.redline.caixasimples.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.redline.caixasimples.util.DatabaseConnect;

import com.mysql.jdbc.PreparedStatement;

public class Produto {
	private int idProduto;
	private String nomeProduto;
	private String codigoBarras;
	private String descricao;
	private String fornecedor;
	private int qtd;
	private BigDecimal precoVenda, precoCusto;

	// Gets
	public int getIdProduto() {
		return idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getQtd() {
		return qtd;
	}

	public String getPrecoVenda() {
		return precoVenda.toString();
	}

	// Sets
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		if (!nomeProduto.matches("^[a-zA-Zà-úÀ-Ú ]{3,45}$"))
			throw new RuntimeException("O valor de nome é inválido");
		this.nomeProduto = nomeProduto;
	}

	public void setCodigoBarras(String codigoBarras) {
		if(codigoBarras.length() != 13)
			throw new RuntimeException("Um código de barras (EAN-13) deve ter 13 caracteres");
		this.codigoBarras = codigoBarras;
	}

	public void setDescricao(String descricao) {
		if (!descricao.matches("^[a-zA-Zà-úÀ-Ú1-9 ]{3,200}$"))
			throw new RuntimeException("O valor de descrição é inválido");
		this.descricao = descricao;
	}

	public void setQtd(int qtd) {
		if (qtd < 0)
			throw new RuntimeException("O valor de quantidade é inválido");
		this.qtd = qtd;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		if(precoVenda.signum() == -1)
			throw new RuntimeException("O valor de preço de venda é inválido");
		this.precoVenda = precoVenda;
	}
	
	public void setPrecoCusto(BigDecimal precoVenda) {
		if(precoVenda.signum() == -1)
			throw new RuntimeException("O valor de preço de venda é inválido");
		this.precoCusto = precoVenda;
	}
	
	public void setFornecedor(String fornecedor) {
		if (!fornecedor.matches("^[a-zA-Zà-úÀ-Ú ]{3,45}$"))
			throw new RuntimeException("O valor de fornecedor é inválido");
		this.fornecedor = fornecedor;
	}

	public Produto(String nomeProduto, String codigoBarras, String descricao,
			int qtd, BigDecimal precoVenda) {
		setNomeProduto(nomeProduto);
		setCodigoBarras(codigoBarras);
		setDescricao(descricao);
		setQtd(qtd);
		setPrecoVenda(precoVenda);
	}
	
	public Produto(String nomeProduto, String codigoBarras, String descricao,
			int qtd, BigDecimal precoVenda, BigDecimal custo, String forncedor) {
		setCodigoBarras(codigoBarras);
		setQtd(qtd);
		setPrecoCusto(custo);
		setFornecedor(forncedor);
		setNomeProduto(nomeProduto);
		setPrecoVenda(precoVenda);
		setDescricao(descricao);
	}

	public Produto(int idProduto, String nomeProduto, String codigoBarras, String descricao,
			int qtd, BigDecimal precoVenda) {
		setIdProduto(idProduto);
		setNomeProduto(nomeProduto);
		setCodigoBarras(codigoBarras);
		setDescricao(descricao);
		setQtd(qtd);
		setPrecoVenda(precoVenda);
	}
	
	public boolean create() {
		try {
			// Verifica codigo de barras
			if(haveCodigoBarras(this.codigoBarras)) {
				throw new RuntimeException("Código de barras ja informado");
			} else {
				// Obtem uma conexão com o banco de dados
				Connection connect = DatabaseConnect.getInstance();

				// Cria um prepared statement
				PreparedStatement statement = (PreparedStatement) connect
						.prepareStatement("INSERT INTO Produto (nomeProduto, codigoBarras, descricao, qtd, precoVenda) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

				// Realiza o bind dos valores
				statement.setString(1, this.nomeProduto);
				statement.setString(2, this.codigoBarras);
				statement.setString(3, this.descricao);
				statement.setInt(4, this.qtd);
				statement.setBigDecimal(5, this.precoVenda);
				
				// Executa o SQL
				int ret = statement.executeUpdate();

				// Retorna resultado
				if (ret == 1) {
					//Define o id a classe
					ResultSet id = statement.getGeneratedKeys();
					while(id.next())
						setIdProduto(id.getInt(1));
					
					// Encerra conexao
					connect.close();
					return true;
				} else {
					// Encerra conexao
					connect.close();
					return false;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu ao criar o produto");
		}
	}
	
	private Boolean haveCodigoBarras(String codigoBarras) {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Produto WHERE codigoBarras = ?");
			
			statement.setString(1, codigoBarras);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public static Produto getByCodigoBarras(String codigoBarras) {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Produto WHERE codigoBarras = ?");
			
			statement.setString(1, codigoBarras);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Produto p = new Produto(resultSet.getInt("idProduto"), 
						resultSet.getString("nomeProduto"), 
						resultSet.getString("codigoBarras"),
						resultSet.getString("descricao"), 
						resultSet.getInt("qtd"), 
						resultSet.getBigDecimal("precoVenda"));
				
				return p;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}

	public static ArrayList<Produto> getAll() {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			Statement statement = connect.createStatement();
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Produto");
			
			ArrayList<Produto> produtoReturn = new ArrayList<Produto>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Produto p = new Produto(resultSet.getInt("idProduto"), 
										resultSet.getString("nomeProduto"), 
										resultSet.getString("codigoBarras"),
										resultSet.getString("descricao"), 
										resultSet.getInt("qtd"), 
										resultSet.getBigDecimal("precoVenda"));
				
				// Adiciona o cliente ao retorno
				produtoReturn.add(p);
			}
			
			// Retorna os clientes
			return produtoReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	public static ArrayList<Produto> getAllWithFilters(String filter) {
		try{
			filter = "%" + filter + "%";
			
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM Produto WHERE "
					+ "codigoBarras LIKE ? OR "
					+ "nomeProduto LIKE ?");
			
			statement.setString(1, "%"+ filter +"%");
			statement.setString(2, "%"+ filter +"%");
			
			// Executa SQL
			ResultSet resultSet = statement.executeQuery();
			
			ArrayList<Produto> produtoReturn = new ArrayList<Produto>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Produto p = new Produto(resultSet.getInt("idProduto"), 
										resultSet.getString("nomeProduto"), 
										resultSet.getString("codigoBarras"),
										resultSet.getString("descricao"), 
										resultSet.getInt("qtd"), 
										resultSet.getBigDecimal("precoVenda"));
				
				// Adiciona o cliente ao retorno
				produtoReturn.add(p);
			}
			
			// Retorna os clientes
			return produtoReturn;
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
					.prepareStatement("UPDATE Produto SET nomeProduto = ?, codigoBarras = ?, descricao = ?, qtd = ?, precoVenda = ? WHERE idProduto = ?");

			// Realiza o bind dos valores
			statement.setString(1, this.nomeProduto);
			statement.setString(2, this.codigoBarras);
			statement.setString(3, this.descricao);
			statement.setInt(4, this.qtd);
			statement.setBigDecimal(5, this.precoVenda);
			statement.setInt(6, this.idProduto);

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
			throw new RuntimeException("Um erro ocorreu ao atualizar o produto");
		}
	}
	
	public boolean delete() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("DELETE FROM Produto WHERE idProduto = ?");

			// Realiza o bind dos valores
			statement.setInt(1, this.idProduto);

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
			throw new RuntimeException("Um erro ocorreu ao deletar o produto");
		}
	}
	
	public boolean vender(int qtd) {
		if(this.qtd - qtd >= 0) {
			setQtd(this.qtd - qtd);
			return update();
		} else {
			throw new RuntimeException("Não é possivel vender mais do que existe em estoque - Estoque = " + this.qtd);
		}
	}

	public boolean createEntradaEstoque() {
		try {
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("INSERT INTO entradaestoque (idProduto, dataEntrada, qtd, precoEntrada, fornecedor) VALUES (?, NOW(), ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			// Realiza o bind dos valores
			statement.setInt(1, this.idProduto);
			statement.setInt(2, this.qtd);
			statement.setBigDecimal(3, this.precoCusto);
			statement.setString(4, this.fornecedor);
			
			// Executa o SQL
			int ret = statement.executeUpdate();

			// Retorna resultado
			if (ret == 1) {
				//Define o id a classe
				ResultSet id = statement.getGeneratedKeys();
				while(id.next())
					setIdProduto(id.getInt(1));
				
				// Encerra conexao
				connect.close();
				return true;
			} else {
				// Encerra conexao
				connect.close();
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Um erro ocorreu ao criar a entrada de estoque");
		}
	}
}
