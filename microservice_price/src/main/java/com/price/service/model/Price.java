package com.price.service.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Price {
	
	@Id
	private int productId;
	
	private double productPrice;
	
	public Price() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Price(int productId, double productPrice) {
		super();
		this.productId = productId;
		this.productPrice = productPrice;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	

}
