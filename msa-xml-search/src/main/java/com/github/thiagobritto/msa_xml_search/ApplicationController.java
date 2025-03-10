package com.github.thiagobritto.msa_xml_search;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.github.thiagobritto.msa_xml_search.model.NfeModel;
import com.github.thiagobritto.msa_xml_search.model.NfeTableModel;
import com.github.thiagobritto.msa_xml_search.model.ProductModel;
import com.github.thiagobritto.msa_xml_search.model.ProductTableModel;

public class ApplicationController {

	private ApplicationView view;
	private ListProductsRepository listProductsRepository;
	private EstadoPathRepository estadoPathRepository;
	private List<String> listProducts;
	private List<NfeModel> nfeListModel;

	public ApplicationController(ApplicationView view, ListProductsRepository listProductsRepository,
			EstadoPathRepository estadoPathRepository) {
		super();
		this.view = view;
		this.listProductsRepository = listProductsRepository;
		this.estadoPathRepository = estadoPathRepository;
		this.listProducts = listProductsRepository.findAll();
		initController();
		loadXml();
	}

	private void initController() {
		view.listProducts.setModel(new ProductListModel(listProducts));
		view.listProducts.addMouseListener(new DeleteProduct());
		view.txtAddProduct.addKeyListener(new SaveProduct());
		view.btnAddProduct.addActionListener(e -> saveProduct());
		view.btnLoadXml.addActionListener(e -> openChooser());
		view.btnExportCSV.addActionListener(e -> exportCSV());
		view.tableXml.addMouseListener(new ShowProduct());
		view.tableXml.getSelectionModel().addListSelectionListener(e -> new ShowProduct().mouseClicked(null));
		view.tableProducts.addMouseListener(new SumProductsSelecteds());
		view.tableProducts.getSelectionModel()
				.addListSelectionListener(e -> new SumProductsSelecteds().mouseClicked(null));
	}

	private class DeleteProduct extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				listProductsRepository.deleteByDescription(listProducts.get(view.listProducts.getSelectedIndex()));
				view.listProducts.setModel(new ProductListModel(listProducts = listProductsRepository.findAll()));
				loadXml();
			}
		}
	}

	private class SaveProduct extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				saveProduct();
			}
		}
	}

	private void saveProduct() {
		if ("".equals(view.txtAddProduct.getText())) {
			JOptionPane.showMessageDialog(view, "Valor invalido!");
		} else {
			listProductsRepository.save(view.txtAddProduct.getText());
			view.listProducts.setModel(new ProductListModel(listProducts = listProductsRepository.findAll()));
			loadXml();
		}
		view.txtAddProduct.setText("");
		view.txtAddProduct.requestFocus();
	}

	private void openChooser() {
		JFileChooser open = new JFileChooser();
		open.setDialogTitle("Selecione uma pasta");
		open.setApproveButtonText("Selecionar pasta");
		open.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (open.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(view, "Nenhum diretorio foi selecionado!", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Salvar
		estadoPathRepository.salvarPath(open.getSelectedFile().getPath());
		// Carregar
		loadXml();
	}

	private void loadXml() {
		// Carregar
		String xmlPath = estadoPathRepository.carregarPath();
		if ("".equals(xmlPath))
			return;

		List<String> listXmlFiles = getListXmlFiles(new File(xmlPath));
		nfeListModel = new ArrayList<NfeModel>();
		for (int i = 0; i < listXmlFiles.size(); i++) {
			NfeModel nfe = NfeService.readXML(listXmlFiles.get(i));
			if (nfe == null)
				continue;
			nfeListModel.add(nfe);
		}
		// Ordenar
		Collections.sort(nfeListModel, new Comparator<NfeModel>() {
			@Override
			public int compare(NfeModel o1, NfeModel o2) {
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		// Filtrar
		nfeListModel = nfeListModel.stream().filter(nfe -> {
			ProductModel[] products = nfe.getProducts();
			for (ProductModel product : products) {
				for (String productDescription : listProducts) {
					if (product.getDesc().contains(productDescription.toUpperCase())) {
						return true;
					}
				}
			}
			return false;
		}).toList();
		// Mostrar
		view.tableXml.setModel(new NfeTableModel(nfeListModel));
		view.tableXml.getColumnModel().getColumn(0).setMaxWidth(100);
		view.lblNfeCount.setText(String.format("%s Resultados", nfeListModel.size()));
		view.btnExportCSV.setEnabled(nfeListModel.size() > 0);

		ProductModel[] tableProductsEmpty = {};
		view.tableProducts.setModel(new ProductTableModel(tableProductsEmpty));
		view.tableProducts.getColumnModel().getColumn(0).setMaxWidth(100);
		view.txtTotal.setText("0,00");
		view.txtSumSelection.setText("0,00");
		view.txtaDataClient.setText("");
	}

	private void exportCSV() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setDialogTitle("Salvar arquivo CSV");
		jFileChooser.setSelectedFile(new File("dados.csv"));

		int userSelection = jFileChooser.showSaveDialog(view);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File arquivo = jFileChooser.getSelectedFile();
			if (!arquivo.getAbsolutePath().endsWith(".csv")) {
				arquivo = new File(arquivo.getAbsolutePath() + ".csv");
			}

			try (FileWriter writer = new FileWriter(arquivo)) {
				writer.append(NfeService.prepareCSV(nfeListModel));
				JOptionPane.showMessageDialog(view, "Arquivo salvo com sucesso!");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(view, "Erro ao salvar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class ShowProduct extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (view.tableXml.getSelectedRow() < 0)
				return;

			NfeModel nfe = nfeListModel.get(view.tableXml.getSelectedRow());
			double total = 0;
			for (ProductModel product : nfe.getProducts()) {
				total += product.gettVal();
			}
			view.tableProducts.setModel(new ProductTableModel(nfe.getProducts()));
			view.tableProducts.getColumnModel().getColumn(0).setMaxWidth(100);
			view.txtTotal.setText(String.format("%.2f", total));

			String text = String.format("CPF: %s\n" + "Nome: %s\n" + "Endereço: %s\n" + "%s - %s/%s",
					nfe.getClient().getCode(), nfe.getClient().getName(), nfe.getClient().getAddress(),
					nfe.getClient().getZone(), nfe.getClient().getCity(), nfe.getClient().getUf());

			view.txtaDataClient.setText(text.replace("null", ""));
		}
	}

	private class SumProductsSelecteds extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int[] rows = view.tableProducts.getSelectedRows();
			double sum = 0;
			for (int row : rows) {
				String valueAt = (String) view.tableProducts.getValueAt(row, 4);
				sum += Double.parseDouble(valueAt.replace(",", "."));
			}
			view.txtSumSelection.setText(String.format("%.2f", sum));
		}
	}

	private List<String> getListXmlFiles(File file) {
		List<String> listPath = new ArrayList<String>();
		File[] listFiles = file.listFiles();
		if (listFiles != null) {
			for (File f : listFiles) {
				if (f.getName().endsWith(".xml")) {
					listPath.add(f.getPath());
				}
				if (f.isDirectory()) {
					listPath.addAll(getListXmlFiles(new File(f.getPath())));
				}
			}
		}
		return listPath;
	}

}
