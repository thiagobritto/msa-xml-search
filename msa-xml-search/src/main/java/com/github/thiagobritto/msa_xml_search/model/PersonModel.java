package com.github.thiagobritto.msa_xml_search.model;

import java.util.Objects;

public class PersonModel {
	private String code;
	private String name;
	private String address;
	private String zone;
	private String city;
	private String uf;

	public PersonModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonModel(String code, String name, String address, String zone, String city, String uf) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.zone = zone;
		this.city = city;
		this.uf = uf;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "PersonModel [code=" + code + ", name=" + name + ", address=" + address + ", zone=" + zone + ", city="
				+ city + ", uf=" + uf + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, city, code, name, uf, zone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonModel other = (PersonModel) obj;
		return Objects.equals(address, other.address) && Objects.equals(city, other.city) && code == other.code
				&& Objects.equals(name, other.name) && Objects.equals(uf, other.uf) && Objects.equals(zone, other.zone);
	}

}
