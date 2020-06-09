package com.promo.service.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblOffers")
@Access(value = AccessType.FIELD)
public class PromotionObj {

	@Id
	@Column(name = "ProductId", unique = true, nullable = false)
	private long productId;
	@Column
	private long promoId;
	@Column
	private String type;
	@Column
	private String value;
	@Column
	private String created;
	@Column
	private String updated;
	@Column
	private String description;
	@Column
	private String isActive;

	public PromotionObj() {
		super();
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getPromoId() {
		return promoId;
	}

	public void setPromoId(long promoId) {
		this.promoId = promoId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public PromotionObj(long productId, long promoId, String type, String value, String created, String updated,
			String description, String isActive) {
		super();
		this.productId = productId;
		this.promoId = promoId;
		this.type = type;
		this.value = value;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.isActive = isActive;
	}

}
