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
import java.util.List;

/**
 * The persistent class for the Products database table.
 * 
 */
@Entity
@Table(name = "Products")
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.findProductByName", query = "SELECT p FROM Product AS p where p.products_Description = :pname"),
		@NamedQuery(name = "Product.findProductById", query = "SELECT p FROM Product AS p where p.idProducts = :pid"),
		@NamedQuery(name = "Product.findProductsByType", query = "SELECT p FROM Product AS p, Product_Type AS pt "
				+ "where p.productType.idProduct_Types = pt.idProduct_Types "
				+ "and pt.product_Types_Code = :product_type"),})
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private int idProducts;

	@Column(nullable = false, length = 45)
	private String products_Code;

	@Column(nullable = false, length = 250)
	private String products_Description;

	@Column(nullable = false, precision = 10)
	private BigDecimal products_Price;

	@Column(nullable = false)
	private int products_Stock;

	// bi-directional many-to-one association to Item
	@OneToMany(mappedBy = "product")
	private List<Item> items;

	// bi-directional many-to-one association to Product_Type
	@ManyToOne
	@JoinColumn(name = "Product_Types_idProduct_Types", nullable = false)
	private Product_Type productType;

	public Product() {
	}

	public int getIdProducts() {
		return this.idProducts;
	}

	public StringProperty idProductsProperty() {
		return new SimpleStringProperty(Integer.toString(this.idProducts));
	}

	public void setIdProducts(int idProducts) {
		this.idProducts = idProducts;
	}

	public String getProducts_Code() {
		return this.products_Code;
	}

	public StringProperty products_CodeProperty() {
		return new SimpleStringProperty(this.products_Code);
	}

	public void setProducts_Code(String products_Code) {
		this.products_Code = products_Code;
	}

	public String getProducts_Description() {
		return this.products_Description;
	}

	public StringProperty products_DescriptionProperty() {
		return new SimpleStringProperty(this.products_Description);
	}

	public void setProducts_Description(String products_Description) {
		this.products_Description = products_Description;
	}

	public BigDecimal getProducts_Price() {
		return this.products_Price;
	}

	public StringProperty products_PriceProperty() {
		return new SimpleStringProperty(this.products_Price.toString());
	}

	public void setProducts_Price(BigDecimal products_Price) {
		this.products_Price = products_Price;
	}

	public int getProducts_Stock() {
		return this.products_Stock;
	}

	public StringProperty products_StockProperty() {
		return new SimpleStringProperty(Integer.toString(this.products_Stock));
	}

	public void setProducts_Stock(int products_Stock) {
		this.products_Stock = products_Stock;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setProduct(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setProduct(null);

		return item;
	}

	public Product_Type getProductType() {
		return this.productType;
	}

	public void setProductType(Product_Type productType) {
		this.productType = productType;
	}

}