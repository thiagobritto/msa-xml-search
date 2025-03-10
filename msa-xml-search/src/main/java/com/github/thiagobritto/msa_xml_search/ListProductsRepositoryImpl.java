package com.github.thiagobritto.msa_xml_search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListProductsRepositoryImpl implements ListProductsRepository {

	public ListProductsRepositoryImpl() {
		super();
		try (Connection conn = ConnectionSqlite.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute("CREATE TABLE IF NOT EXISTS lista_produtos (description TEXT)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> findAll() {
		try (Connection conn = ConnectionSqlite.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT description FROM lista_produtos")) {
			List<String> description = new ArrayList<String>();
			while (rs.next()) {
				description.add(rs.getString("description"));
			}
			return description;
		} catch (SQLException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public void save(String description) {
		try (Connection conn = ConnectionSqlite.getConnection();
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO lista_produtos (description) VALUES (?)")) {
			stmt.setString(1, description);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByDescription(String description) {
		try (Connection conn = ConnectionSqlite.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM lista_produtos WHERE description = ?")) {
			stmt.setString(1, description);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
