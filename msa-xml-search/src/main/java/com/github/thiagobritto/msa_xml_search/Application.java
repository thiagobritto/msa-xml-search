package com.github.thiagobritto.msa_xml_search;

import java.awt.EventQueue;

import com.formdev.flatlaf.FlatLightLaf;

public class Application {

	public static void main(String[] args) {
		try {
			FlatLightLaf.setup(); // Tema claro
			// FlatDarkLaf.setup(); // Tema escuro
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(() -> new ApplicationView().setVisible(true));
	}
}
