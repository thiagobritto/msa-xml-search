package com.github.thiagobritto.msa_xml_search.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNS = { "CÓDIGO", "DESCRIÇÃO", "QTD", "VALOR", "SUB-TOTAL" };

	private List<ProductModel> products;

	public ProductTableModel(List<ProductModel> products) {
		//ArrayList<Object> arrayList = new ArrayList<>();
		this.products = products;
	}

	@Override
	public int getRowCount() {
		return products.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductModel product = products.get(rowIndex);
		if (columnIndex == 0)
			return product.getCode();
		if (columnIndex == 1)
			return product.getDesc();
		if (columnIndex == 2)
		return String.format("%.4f", product.getQtd());
		if (columnIndex == 3)
			return String.format("%.2f", product.getuVal());
		if (columnIndex == 4)
			return String.format("%.2f", product.gettVal());

		return null;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

}
