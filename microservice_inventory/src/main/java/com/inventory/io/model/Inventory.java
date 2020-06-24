package com.inventory.io.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {
	
	@Id
	private String productId;
	private int quantity;
	
	public Inventory() {
		// TODO Auto-generated constructor stub
	}

	public Inventory(String productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
