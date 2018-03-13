/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.entity;

import java.io.Serializable;
import javax.persistence.*;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

/**
 * The persistent class for the Items database table.
 * 
 */
@Entity
@Table(name = "Items")
@NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemPK id;

	@Column(nullable = false, length = 45)
	private int items_Quantity;

	@Column(nullable = false, precision = 10)
	private BigDecimal items_Unit_Price;

	// bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name = "Orders_idOrders", nullable = false, insertable = false, updatable = false)
	private Order order;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "Products_idProducts", nullable = false, insertable = false, updatable = false)
	private Product product;

	@Transient
	private int previousQuantity = -1;
	
	public Item() {
	}

	public void setPreviousQuantity (int value) {
		this.previousQuantity = value;
	}
	
	public int getPreviousQuantity() {
		return this.previousQuantity;
	}
	
	public int getQuantityVariation() {
		if (this.previousQuantity < 0)
			return 0-this.items_Quantity;
		return this.previousQuantity - this.items_Quantity;
	}
	
	public ItemPK getId() {
		return this.id;
	}

	public void setId(ItemPK id) {
		this.id = id;
	}

	public int getItems_Quantity() {
		return this.items_Quantity;
	}

	public StringProperty items_QuantityProperty() {
		return new SimpleStringProperty(this.items_Quantity + "");
	}

	public void setItems_Quantity(int items_Quantity) {
		this.items_Quantity = items_Quantity;
	}

	public BigDecimal getItems_Unit_Price() {
		return this.items_Unit_Price;
	}

	public StringProperty items_Unit_PriceProperty() {
		return new SimpleStringProperty(this.items_Unit_Price.toString());
	}

	public void setItems_Unit_Price(BigDecimal items_Unit_Price) {
		this.items_Unit_Price = items_Unit_Price;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}