package com.github.thiagobritto.msa_xml_search.model;

import java.util.Objects;

public class ProductModel {
	private String code;
	private String desc;
	private Float qtd;
	private Float uVal;
	private Float tVal;

	public ProductModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductModel(String desc, String qtd, String uVal, String tVal) {
		super();
		this.desc = desc;
		this.qtd = Float.parseFloat(qtd);
		this.uVal = Float.parseFloat(uVal);
		this.tVal = Float.parseFloat(tVal);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Float getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = Float.parseFloat(qtd);
	}

	public Float getuVal() {
		return uVal;
	}

	public void setuVal(String uVal) {
		this.uVal = Float.parseFloat(uVal);
	}

	public Float gettVal() {
		return tVal;
	}

	public void settVal(String tVal) {
		this.tVal = Float.parseFloat(tVal);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(desc, qtd, tVal, uVal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductModel other = (ProductModel) obj;
		return Objects.equals(desc, other.desc) && Objects.equals(qtd, other.qtd) && Objects.equals(tVal, other.tVal)
				&& Objects.equals(uVal, other.uVal);
	}

	@Override
	public String toString() {
		return "ProductModel [code=" + code + ", desc=" + desc + ", qtd=" + qtd + ", uVal=" + uVal + ", tVal=" + tVal
				+ "]";
	}

}
