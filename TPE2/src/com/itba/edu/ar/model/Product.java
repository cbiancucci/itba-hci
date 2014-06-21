package com.itba.edu.ar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {
	private Integer id;
	String name;
	Integer price;
	List<String> imageUrl;
	Map<String, String> attributes;
	Category category;
	Subcategory subcategory;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public void addImageUrl(String url) {
		if(this.imageUrl == null) {
			imageUrl = new ArrayList<String>();
		}
		imageUrl.add(url);
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public String getAttribute(String attr) {
		if(attributes.containsKey(attr)) {
			return attributes.get(attr);
		}
		return "";
	}
	
	public List<String> getImageUrl() {
		return imageUrl;
	}
	
	public void addAttribute(Attribute attr) {
		if(this.attributes == null) {
			attributes = new HashMap<String, String>();
		}
		attributes.put(attr.getName(), attr.getValues());
	}
}
