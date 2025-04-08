package com.github.thiagobritto.msa_xml_search;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;

public class Application {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Application::start);
	}

	private static void start() {
		try {
			FlatLightLaf.setup();
			new ApplicationView().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
