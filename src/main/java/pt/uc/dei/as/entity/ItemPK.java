/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Items database table.
 * 
 */
@Embeddable
public class ItemPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false, unique = true, nullable = false)
	private int orders_idOrders;

	@Column(insertable = false, updatable = false, unique = true, nullable = false)
	private int products_idProducts;

	public ItemPK() {
	}

	public int getOrders_idOrders() {
		return this.orders_idOrders;
	}

	public void setOrders_idOrders(int orders_idOrders) {
		this.orders_idOrders = orders_idOrders;
	}

	public int getProducts_idProducts() {
		return this.products_idProducts;
	}

	public void setProducts_idProducts(int products_idProducts) {
		this.products_idProducts = products_idProducts;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ItemPK)) {
			return false;
		}
		ItemPK castOther = (ItemPK) other;
		return (this.orders_idOrders == castOther.orders_idOrders)
				&& (this.products_idProducts == castOther.products_idProducts);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orders_idOrders;
		hash = hash * prime + this.products_idProducts;

		return hash;
	}
}