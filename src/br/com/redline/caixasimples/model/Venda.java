package br.com.redline.caixasimples.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.mysql.jdbc.PreparedStatement;
import br.com.redline.caixasimples.util.DatabaseConnect;

public class Venda {
	private int idVenda;
	private Date data;
	private BigDecimal total;
	
	public int getIdVenda() {
		return idVenda;
	}
	
	public String getData() {
		SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
		return format.format(data);
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public Venda(int idVenda, Date data, BigDecimal total) {
		setIdVenda(idVenda);
		setData(data);
		setTotal(total);
	}
	
	public static ArrayList<Venda> getAll() {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();
			
			// Cria um statement
			Statement statement = connect.createStatement();
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Venda");
			
			ArrayList<Venda> vendaReturn = new ArrayList<Venda>();
			while(resultSet.next()) {
				// Cria um cliente com os dados do BD
				Venda v = new Venda(resultSet.getInt("idVenda"), 
									resultSet.getDate("dataVenda"),
									getTotal(resultSet.getInt("idVenda")));

				// Adiciona o cliente ao retorno
				vendaReturn.add(v);
			}
			
			// Retorna os clientes
			return vendaReturn;
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}
	
	private static BigDecimal getTotal(int idVenda) {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM VendaTotal WHERE idVenda = ?");
			
			statement.setInt(1, idVenda);
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next())
				return resultSet.getBigDecimal("total");
			
			return new BigDecimal(0);
		} catch (SQLException e) {
			throw new RuntimeException("Um erro ocorreu");
		}
	}

	public static ArrayList<ItemVenda> getAllItens(int idVenda) {
		try{
			// Obtem uma conexão com o banco de dados
			Connection connect = DatabaseConnect.getInstance();

			// Cria um prepared statement
			PreparedStatement statement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM ItemVendaInfo WHERE idVenda = ?");
			
			statement.setInt(1, idVenda);
			
			// Executa um SQL
			ResultSet resultSet = statement.executeQuery();
			
			ArrayList<ItemVenda> itensReturn = new ArrayList<ItemVenda>();
			while(resultSet.next()) {
				ItemVenda iv = new ItemVenda(resultSet.getInt("idVenda"),
												resultSet.getInt("idProduto"),
												resultSet.getString("codigoBarras"),
												resultSet.getString("nomeProduto"),
												resultSet.getBigDecimal("precoVenda"),
												resultSet.getInt("qtd"),
												resultSet.getBigDecimal("total"));
				
				itensReturn.add(iv);
			}
			
			return itensReturn;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Um erro ocorreu");
		}
	}
}
