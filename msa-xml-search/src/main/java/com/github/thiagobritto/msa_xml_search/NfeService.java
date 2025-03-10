package com.github.thiagobritto.msa_xml_search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.github.thiagobritto.msa_xml_search.model.ClientModel;
import com.github.thiagobritto.msa_xml_search.model.NfeModel;
import com.github.thiagobritto.msa_xml_search.model.ProductModel;
import com.github.thiagobritto.msa_xml_search.model.StoreModel;

public class NfeService {

	private static Document doc;

	public static NfeModel readXML(String path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(path);
		} catch (ParserConfigurationException e) {
			// Factory Exception
			e.printStackTrace();
			return null;
		} catch (SAXException | IOException e) {
			// Factory Exception
			e.printStackTrace();
			return null;
		}

		String code = getCode();
		if (code == null)
			return null;

		NfeModel nfeModel = new NfeModel();
		nfeModel.setCode(code);
		nfeModel.setStore(getStore());
		nfeModel.setClient(getClient());
		nfeModel.setProducts(getProducts());

		return nfeModel;
	}

	public static ArrayList<NfeModel> filterByProducts(ArrayList<NfeModel> nfeList,
			DefaultListModel<String> listProducts) {
		if (listProducts.isEmpty())
			return nfeList;

		ArrayList<NfeModel> newNfeList = new ArrayList<NfeModel>();
		for (int i = 0; i < nfeList.size(); i++) {
			Boolean econtrou = false;
			ProductModel[] prod = nfeList.get(i).getProducts();
			for (int j = 0; j < prod.length; j++) {
				String desc = prod[j].getDesc();
				for (int x = 0; x < listProducts.size(); x++) {
					if (desc.contains(listProducts.get(x))) {
						econtrou = true;
						break;
					}
				}

			}
			if (econtrou)
				newNfeList.add(nfeList.get(i));
		}
		return newNfeList;
	}

	private static String getCode() {
		Node code = doc.getElementsByTagName("nNF").item(0);
		if (code != null)
			return code.getTextContent();
		return null;
	}

	private static StoreModel getStore() {
		Node emit = doc.getElementsByTagName("emit").item(0);
		StoreModel store = new StoreModel();
		if (emit != null) {
			NodeList emitChild = emit.getChildNodes();
			Node name = getNode(emitChild, "xNome");
			Node cnpj = getNode(emitChild, "CNPJ");
			Node addresEmit = getNode(emitChild, "enderEmit");
			if (name != null)
				store.setName(name.getTextContent());
			if (cnpj != null)
				store.setCode(cnpj.getTextContent());
			if (addresEmit != null) {
				NodeList addresEmitChild = addresEmit.getChildNodes();
				Node address = getNode(addresEmitChild, "xLgr");
				Node zone = getNode(addresEmitChild, "xBairro");
				Node city = getNode(addresEmitChild, "xMun");
				Node uf = getNode(addresEmitChild, "UF");
				if (address != null)
					store.setAddress(address.getTextContent());
				if (zone != null)
					store.setZone(zone.getTextContent());
				if (city != null)
					store.setCity(city.getTextContent());
				if (uf != null)
					store.setUf(uf.getTextContent());
			}
		}
		return store;
	}

	private static ClientModel getClient() {
		Node dest = doc.getElementsByTagName("dest").item(0);
		ClientModel client = new ClientModel();
		if (dest != null) {
			NodeList destChild = dest.getChildNodes();
			Node name = getNode(destChild, "xNome");
			Node cpf = getNode(destChild, "CPF");
			Node cnpj = getNode(destChild, "CNPJ");
			Node addresDest = getNode(destChild, "enderDest");
			if (name != null)
				client.setName(name.getTextContent());
			if (cpf != null)
				client.setCode(cpf.getTextContent());
			if (cnpj != null)
				client.setCode(cnpj.getTextContent());
			if (addresDest != null) {
				NodeList addresDestChild = addresDest.getChildNodes();
				Node address = getNode(addresDestChild, "xLgr");
				Node zone = getNode(addresDestChild, "xBairro");
				Node city = getNode(addresDestChild, "xMun");
				Node uf = getNode(addresDestChild, "UF");
				if (address != null)
					client.setAddress(address.getTextContent());
				if (zone != null)
					client.setZone(zone.getTextContent());
				if (city != null)
					client.setCity(city.getTextContent());
				if (uf != null)
					client.setUf(uf.getTextContent());
			}
		}
		return client;
	}

	private static ProductModel[] getProducts() {
		NodeList products = doc.getElementsByTagName("prod");
		ProductModel[] productList = new ProductModel[products.getLength()];
		for (int i = 0; i < products.getLength(); i++) {
			ProductModel product = new ProductModel();
			NodeList prodList = products.item(i).getChildNodes();
			Node code = getNode(prodList, "cProd");
			Node desc = getNode(prodList, "xProd");
			Node qtd = getNode(prodList, "qCom");
			Node uVal = getNode(prodList, "vUnCom");
			Node tVal = getNode(prodList, "vProd");
			if (code != null)
				product.setCode(code.getTextContent());
			if (desc != null)
				product.setDesc(desc.getTextContent());
			if (qtd != null)
				product.setQtd(qtd.getTextContent());
			if (uVal != null)
				product.setuVal(uVal.getTextContent());
			if (tVal != null)
				product.settVal(tVal.getTextContent());
			productList[i] = product;
		}
		return productList;
	}

	private static Node getNode(NodeList nodeList, String tag) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName().equals(tag))
				return node;
		}
		return null;
	}

	public static String prepareCSV(List<NfeModel> listNfeModel) {
		String csv = "";

		for (NfeModel nfe : listNfeModel) {
			csv += nfe.getCode() + ";;;\n";

			String name = (nfe.getClient().getName() == null) ? "" : nfe.getClient().getName();
			String code = (nfe.getClient().getCode() == null) ? "" : nfe.getClient().getCode();
			String address = (nfe.getClient().getAddress() == null) ? "" : nfe.getClient().getAddress();
			String zone = (nfe.getClient().getZone() == null) ? "" : nfe.getClient().getZone();
			String city = (nfe.getClient().getCity() == null) ? "" : nfe.getClient().getCity();
			String uf = (nfe.getClient().getUf() == null) ? "" : nfe.getClient().getUf();

			csv += "Nome;" + name + ";;;\n";
			csv += "CPF/CNPJ;" + code + ";;;\n";
			csv += "Endereço;" + address + ";;;\n";
			csv += "Bairro;" + zone + ";;;\n";
			csv += "Cidade;" + city + ";;UF;" + uf + "\n";

			csv += "Codigo;Descrição;Qtd;Valor;Total\n";
			Float sum = 0f;
			for (ProductModel prod : nfe.getProducts()) {
				csv += prod.getCode() + ";" + prod.getDesc() + ";" + String.format("%.2f", prod.getQtd()) + ";"
						+ String.format("%.2f", prod.getuVal()) + ";" + String.format("%.2f", prod.gettVal()) + "\n";
				sum += prod.gettVal();
			}
			csv += ";;;;" + String.format("%.2f", sum) + "\n\n\n";
		}
		return csv;
	}
}
