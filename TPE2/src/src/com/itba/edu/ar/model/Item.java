package com.itba.edu.ar.model;

import java.util.List;

public class Item {
	List<Product> products;
	Integer quantity;
	Integer price;
	
	public Item(Integer quantity, Integer price){
		this.quantity = quantity;
		this.price = price;
	}
}
