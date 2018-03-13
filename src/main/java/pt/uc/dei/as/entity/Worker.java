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

import java.util.List;

/**
 * The persistent class for the Clients database table.
 * 
 */
@Entity
@Table(name = "Workers")
@NamedQueries({ @NamedQuery(name = "Worker.findAll", query = "SELECT c FROM Worker c"),
		@NamedQuery(name = "Worker.findWorker", query = "SELECT c FROM Worker AS c where c.workers_Name = :workers_name"), })
public class Worker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int idWorkers;

	@Column(nullable = false, length = 250)
	private String workers_Password;

	@Column(nullable = false, length = 100)
	private String workers_Name;

	// bi-directional many-to-one association to Order
	//@OneToMany(mappedBy = "worker")
	//private List<Order> orders;

	public Worker() {
	}

	public int getIdWorkers() {
		return this.idWorkers;
	}

	public void setIdWorkers(int idWorkers) {
		this.idWorkers = idWorkers;
	}

	public String getWorkers_Password() {
		return this.workers_Password;
	}

	public StringProperty workers_PasswordProperty() {
		return new SimpleStringProperty(this.workers_Password);
	}

	public void setWorkers_Password(String workers_Password) {
		this.workers_Password = workers_Password;
	}

	public String getWorkers_Name() {
		return this.workers_Name;
	}

	public StringProperty workers_NameProperty() {
		return new SimpleStringProperty(this.workers_Name);
	}

	public void setWorkers_Name(String workers_Name) {
		this.workers_Name = workers_Name;
	}


	///TODO ALTERAR ORDER PARA TER TBM QUEM A FEZ -- LOGGING STUFF
	/*public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setClient(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setClient(null);

		return order;
	}*/

}