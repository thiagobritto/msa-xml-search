package com.github.thiagobritto.msa_xml_search;

import java.util.List;

import javax.swing.AbstractListModel;

public class ProductListModel extends AbstractListModel<String> {

	private static final long serialVersionUID = 1L;
	private List<String> list;

	public ProductListModel(List<String> list) {
		super();
		this.list = list;
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public String getElementAt(int index) {
		return list.get(index);
	}

}
