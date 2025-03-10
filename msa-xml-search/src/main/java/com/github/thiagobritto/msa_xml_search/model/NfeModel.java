package com.github.thiagobritto.msa_xml_search.model;

import java.util.Arrays;
import java.util.Objects;

public class NfeModel {
	
	private String code;
	private StoreModel store;
	private ClientModel client;
	private ProductModel[] products;
	
	public NfeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NfeModel(String code, StoreModel store, ClientModel client, ProductModel[] products) {
		super();
		this.code = code;
		this.store = store;
		this.client = client;
		this.products = products;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public StoreModel getStore() {
		return store;
	}
	public void setStore(StoreModel store) {
		this.store = store;
	}
	public ClientModel getClient() {
		return client;
	}
	public void setClient(ClientModel client) {
		this.client = client;
	}
	public ProductModel[] getProducts() {
		return products;
	}
	public void setProducts(ProductModel[] products) {
		this.products = products;
	}
	@Override
	public String toString() {
		return "NfeModel [code=" + code + ", store=" + store + ", client=" + client + ", products="
				+ Arrays.toString(products) + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(products);
		result = prime * result + Objects.hash(client, code, store);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NfeModel other = (NfeModel) obj;
		return Objects.equals(client, other.client) && Objects.equals(code, other.code)
				&& Arrays.equals(products, other.products) && Objects.equals(store, other.store);
	}
}
