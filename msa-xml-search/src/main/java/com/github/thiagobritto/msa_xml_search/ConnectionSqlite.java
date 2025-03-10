package com.github.thiagobritto.msa_xml_search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSqlite {
	private static Connection connection;

	private ConnectionSqlite() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:estado.db");
	}

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				new ConnectionSqlite();
			}
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
