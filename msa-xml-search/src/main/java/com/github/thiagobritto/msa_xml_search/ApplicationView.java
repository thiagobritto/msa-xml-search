package com.github.thiagobritto.msa_xml_search;

import java.awt.Cursor;
import java.awt.Font;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.github.thiagobritto.msa_xml_search.model.NfeTableModel;
import com.github.thiagobritto.msa_xml_search.model.ProductModel;
import com.github.thiagobritto.msa_xml_search.model.ProductTableModel;
import java.awt.Color;

public class ApplicationView extends JFrame {
	private static final long serialVersionUID = 1L;
	public JTable tableXml;
	public JTable tableProducts;
	public JTextField txtAddProduct;
	public JList<String> listProducts;
	public JButton btnAddProduct;
	public JButton btnLoadXml;
	public JTextField txtTotal;
	public JLabel lblNfeCount;
	public JTextField txtSumSelection;
	public JTextArea txtaDataClient;
	public JButton btnExportCSV;
	private JLabel lblNewLabel_1;

	public ApplicationView() {
		setTitle("MSA-NFe");
		setSize(800, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 224, 145);
		getContentPane().add(scrollPane);

		listProducts = new JList<String>();
		scrollPane.setViewportView(listProducts);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(251, 46, 523, 144);
		getContentPane().add(scrollPane_1);

		tableXml = new JTable(new NfeTableModel(Collections.emptyList()));
		// Permite apenas uma linha selecionada
		tableXml.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		tableXml.getColumnModel().getColumn(0).setMaxWidth(100);
		scrollPane_1.setViewportView(tableXml);

		btnAddProduct = new JButton("Adiciona");
		btnAddProduct.setBounds(153, 201, 81, 23);
		getContentPane().add(btnAddProduct);

		btnLoadXml = new JButton("Carregar XML");
		btnLoadXml.setBounds(660, 201, 114, 23);
		getContentPane().add(btnLoadXml);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 275, 764, 179);
		getContentPane().add(scrollPane_2);

		ProductModel[] emptyProduct = {};
		tableProducts = new JTable(new ProductTableModel(emptyProduct));
		tableProducts.getColumnModel().getColumn(0).setMaxWidth(100);
		// Permite varias linhas selecionadas
		tableProducts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_2.setViewportView(tableProducts);

		txtAddProduct = new JTextField();
		txtAddProduct.setBounds(10, 201, 133, 23);
		getContentPane().add(txtAddProduct);
		txtAddProduct.setColumns(10);

		JLabel lblNewLabel = new JLabel("Filtrar por ocorrencia de:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 224, 33);
		getContentPane().add(lblNewLabel);

		JLabel lblListaDeNotas = new JLabel("Lista de notas");
		lblListaDeNotas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblListaDeNotas.setBounds(252, 11, 224, 33);
		getContentPane().add(lblListaDeNotas);

		JLabel lblListaDeProdutos = new JLabel("Lista de produtos");
		lblListaDeProdutos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblListaDeProdutos.setBounds(10, 240, 224, 33);
		getContentPane().add(lblListaDeProdutos);
		
		lblNfeCount = new JLabel("0 Resultados");
		lblNfeCount.setBounds(251, 195, 144, 14);
		getContentPane().add(lblNfeCount);
		
		txtTotal = new JTextField();
		txtTotal.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		txtTotal.setText("0,00");
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtTotal.setColumns(10);
		txtTotal.setEditable(false);
		txtTotal.setBounds(616, 245, 158, 23);
		getContentPane().add(txtTotal);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTotal.setBounds(382, 240, 224, 33);
		getContentPane().add(lblTotal);
		
		txtSumSelection = new JTextField();
		txtSumSelection.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		txtSumSelection.setText("0,00");
		txtSumSelection.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSumSelection.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSumSelection.setEditable(false);
		txtSumSelection.setColumns(10);
		txtSumSelection.setBounds(616, 459, 158, 23);
		getContentPane().add(txtSumSelection);
		
		JLabel lblCliente = new JLabel("Dados do cliente");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCliente.setBounds(10, 464, 133, 14);
		getContentPane().add(lblCliente);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 490, 286, 80);
		getContentPane().add(scrollPane_3);
		
		txtaDataClient = new JTextArea();
		txtaDataClient.setEditable(false);
		txtaDataClient.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		scrollPane_3.setViewportView(txtaDataClient);
		
		btnExportCSV = new JButton("Exportar CSV");
		btnExportCSV.setEnabled(false);
		btnExportCSV.setBounds(525, 201, 126, 23);
		getContentPane().add(btnExportCSV);
		
		lblNewLabel_1 = new JLabel("** USE O \"DUPLO CLICK\" PARA REMOVER UM FILTRO");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_1.setBounds(306, 540, 468, 14);
		getContentPane().add(lblNewLabel_1);
		
		SwingUtilities.invokeLater(() -> txtAddProduct.requestFocus());
		
		new ApplicationController(ApplicationView.this, new ListProductsRepositoryImpl(), new EstadoPathRepository());
	}
}
