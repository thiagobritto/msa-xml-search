package com.github.thiagobritto.msa_xml_search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstadoPathRepository {

	public EstadoPathRepository() {
		super();
		 try (Connection conn = ConnectionSqlite.getConnection(); Statement stmt = conn.createStatement()) {
	            stmt.execute("CREATE TABLE IF NOT EXISTS estado_path (id INTEGER PRIMARY KEY, dirpath TEXT)");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	public void salvarPath(String textPath) {
        try (Connection conn = ConnectionSqlite.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO estado_path (id, dirpath) VALUES (1, ?) ON CONFLICT(id) DO UPDATE SET dirpath = ?")) {
            stmt.setString(1, textPath);
            stmt.setString(2, textPath);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String carregarPath() {
        try (Connection conn = ConnectionSqlite.getConnection(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT dirpath FROM estado_path WHERE id = 1")) {
            if (rs.next()) {
                return rs.getString("dirpath");
            }
        } catch (SQLException e) {
        	return "";
        }
        return "";
    }

	
}
