package br.com.redline.caixasimples.util;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DatabaseConnect {

	// MySQL Connection
	private static Connection con;

	// Retorna uma conex�o com o banco de dados
	public static Connection getInstance() {
		try {
			// Carrega o driver do MySQL
			Class.forName("com.mysql.jdbc.Driver");

			// Cria uma conex�o com o MySQL
			con = DriverManager.getConnection("jdbc:mysql://localhost/loja", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Retorna a conex�o
		return con;
	}
}
