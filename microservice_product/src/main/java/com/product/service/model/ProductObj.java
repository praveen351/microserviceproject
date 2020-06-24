package com.product.service.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblProduct")
@Access(value = AccessType.FIELD)

public class ProductObj implements Serializable {
	@Id
	@Column(name = "ProductId", unique = true, nullable = false)
	private long productId;
	@Column
	private String category;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private String brand;
	@Column
	private double price;
	@Column
	private int qty;
	@Column
	private String weight;
	@Column
	private String dimen;
	@Column
	private String created;
	@Column
	private String updated;
	@Column
	private String isActive;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDimen() {
		return dimen;
	}

	public void setDimen(String dimen) {
		this.dimen = dimen;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public ProductObj() {
		super();
	}

	public ProductObj(long productId, String category, String name, String description, String brand, double price,
			int qty, String weight, String dimen, String created, String updated, String isActive) {
		super();
		this.productId = productId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.brand = brand;
		this.price = price;
		this.qty = qty;
		this.weight = weight;
		this.dimen = dimen;
		this.created = created;
		this.updated = updated;
		this.isActive = isActive;
	}

}
