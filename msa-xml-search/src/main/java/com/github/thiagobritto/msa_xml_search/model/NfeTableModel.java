package com.github.thiagobritto.msa_xml_search.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class NfeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNS = { "NUMERO", "CLIENTE", "EMITENTE" };

	private List<NfeModel> nfes;

	public NfeTableModel(List<NfeModel> nfes) {
		this.nfes = nfes;
	}

	@Override
	public int getRowCount() {
		return nfes.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		NfeModel nfe = nfes.get(rowIndex);
		if (columnIndex == 0)
			return nfe.getCode();
		if (columnIndex == 1)
			return nfe.getClient().getName();
		if (columnIndex == 2)
			return nfe.getStore().getName();

		return null;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

}
