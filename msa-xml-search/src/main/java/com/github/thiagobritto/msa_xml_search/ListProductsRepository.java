package com.github.thiagobritto.msa_xml_search;

import java.util.List;

public interface ListProductsRepository {
	List<String> findAll();
	void save(String description);
	void deleteByDescription(String description);
}
