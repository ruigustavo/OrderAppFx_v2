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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Workers_log database table.
 *
 */
@Entity
@Table(name = "Workers_Log")
@NamedQueries({ @NamedQuery(name = "Worker_log.findAll", query = "SELECT c FROM Workers_Log c"),
        @NamedQuery(name = "Worker_log.findWorker", query = "SELECT c FROM Workers_Log AS c where c.workers_Name = :workers_name"), })
public class Workers_Log implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idLog;

    @Column(nullable = false)
    private int idWorkers;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date in_out_Date;

    @Column(nullable = false, length = 100)
    private String workers_Name;

    @Column(nullable = false)
    private int check_point;

    // bi-directional many-to-one association to Order
    //@OneToMany(mappedBy = "worker")
    //private List<Order> orders;

    public Workers_Log() {
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public Date getIn_out_Date() {
        return this.in_out_Date;
    }

    public StringProperty in_out_DateProperty() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy-hh-mm:");
        String date1 = format1.format(this.in_out_Date);
        return new SimpleStringProperty(date1);
    }

    public void setIn_out_Date(Date in_out_Date) {
        this.in_out_Date = in_out_Date;
    }

    public int getCheck_point() {
        return check_point;
    }

    public void setCheck_point(int check_point) {
        this.check_point = check_point;
    }

    public int getIdWorkers() {
        return this.idWorkers;
    }

    public void setIdWorkers(int idWorkers) {
        this.idWorkers = idWorkers;
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