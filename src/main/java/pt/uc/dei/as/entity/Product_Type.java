/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the Product_Types database table.
 * 
 */
@Entity
@Table(name = "Product_Types")
@NamedQuery(name = "Product_Type.findAll", query = "SELECT p FROM Product_Type p")
public class Product_Type implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private int idProduct_Types;

	@Column(nullable = false, length = 45)
	private String product_Types_Code;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "productType")
	private List<Product> products;

	public Product_Type() {
	}

	public int getIdProduct_Types() {
		return this.idProduct_Types;
	}

	public void setIdProduct_Types(int idProduct_Types) {
		this.idProduct_Types = idProduct_Types;
	}

	public String getProduct_Types_Code() {
		return this.product_Types_Code;
	}

	public void setProduct_Types_Code(String product_Types_Code) {
		this.product_Types_Code = product_Types_Code;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setProductType(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setProductType(null);

		return product;
	}

}