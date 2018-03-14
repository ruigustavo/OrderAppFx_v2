/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.TypedQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.entity.*;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderOverviewController.
 */
public class OrderOverviewController {
	
	/** The order table. */
	@FXML
	private TableView<Order> orderTable;

	/** The name column. */
	@FXML
	private TableColumn<Order, String> nameColumn;
	
	/** The date column. */
	@FXML
	private TableColumn<Order, String> dateColumn;
	
	/** The shipped column. */
	@FXML
	private TableColumn<Order, String> shippedColumn;

	/** The items table. */
	@FXML
	private TableView<Item> itemsTable;

	/** The description column. */
	@FXML
	private TableColumn<Item, String> descriptionColumn;
	
	/** The quantity column. */
	@FXML
	private TableColumn<Item, String> quantityColumn;
	
	/** The price column. */
	@FXML
	private TableColumn<Item, String> priceColumn;

	/** The order total label. */
	@FXML
	private Label orderTotalLabel;
	
	/** The telephone label. */
	@FXML
	private Label telephoneLabel;
	
	/** The address label. */
	@FXML
	private Label addressLabel;
	
	/** The email label. */
	@FXML
	private Label emailLabel;

	/** The main app. */
	private MainApp mainApp;

	/** The items data. */
	private ObservableList<Item> itemsData = FXCollections.observableArrayList();

	/**
	 * Instantiates a new order overview controller.
	 */
	public OrderOverviewController() {

	}

	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().clients_NameProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().orders_DateProperty());
		shippedColumn.setCellValueFactory(cellData -> cellData.getValue().orders_ShippedProperty());
		descriptionColumn
				.setCellValueFactory(cellData -> cellData.getValue().getProduct().products_DescriptionProperty());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().items_QuantityProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().items_Unit_PriceProperty());

		showOrderDetails(null);

		orderTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showOrderDetails(newValue));

		orderTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.DELETE)
					handleDelete();
				else if (event.getCode() == KeyCode.ENTER)
					handleEditOrder();
			}
		});

		orderTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					handleEditOrder();
				}
			}
		});

	}

	/**
	 * Sets the main app.
	 *
	 * @param mainApp the new main app
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		orderTable.setItems(mainApp.getOrdersData());
	}

	/**
	 * Show order details.
	 *
	 * @param order the order
	 */
	private void showOrderDetails(Order order) {
		if (order != null) {
			orderTotalLabel.setText(order.getOrders_Total_Cost().toString());
			telephoneLabel.setText(order.getClient().getClients_Telephone());
			addressLabel.setText(order.getClient().getClients_Address());
			emailLabel.setText(order.getClient().getClients_Email());
			itemsData.clear();
			for (Item i : order.getItems()) {
				itemsData.add(i);
			}
			itemsTable.setItems(itemsData);

		} else {
			orderTotalLabel.setText("");
			telephoneLabel.setText("");
			addressLabel.setText("");
			emailLabel.setText("");
			itemsTable.getItems().clear();
		}
	}

	/**
	 * Handle new order.
	 */
	@FXML
	private void handleNewOrder() {
		Orders_Log newOrderLog = new Orders_Log();
		Order newOrder = new Order();
		newOrder.setItems(new ArrayList<Item>());
		boolean saveClicked = mainApp.showOrderEditorDialog(newOrder);
		for (Item i : newOrder.getItems()) {
			i.getId().setOrders_idOrders(newOrder.getIdOrders());
		}
		if (saveClicked) {

			try {
				MainApp.em.getTransaction().begin();
				MainApp.em.persist(newOrder.getClient());
				MainApp.em.persist(newOrder);

				for (Item i : newOrder.getItems()) {
					i.setOrder(newOrder);
					i.getId().setOrders_idOrders(newOrder.getIdOrders());
					i.getProduct().setProducts_Stock(i.getProduct().getProducts_Stock() + i.getQuantityVariation());
				}

				MainApp.em.merge(newOrder);
				newOrderLog.setIdOrders(newOrder.getIdOrders());
				newOrderLog.setIdWorkers(mainApp.getWorker().getIdWorkers());
				newOrderLog.setWorkers_Name(mainApp.getWorker().getWorkers_Name());
				Calendar today = Calendar.getInstance();
				newOrderLog.setOrders_Date(today.getTime());
				MainApp.em.merge(newOrderLog);
				MainApp.em.getTransaction().commit();

			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				handleRefresh();
				return;
			}

			mainApp.getOrdersData().add(newOrder);
			orderTable.getSelectionModel().select(newOrder);
			showOrderDetails(newOrder);

		}
	}

	/**
	 * Handle edit order.
	 */
	@FXML
	private void handleEditOrder() {
		Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
		if (selectedOrder != null) {
			boolean saveClicked = mainApp.showOrderEditorDialog(selectedOrder);
			if (saveClicked) {
				try {
					List<Item> tmpItemList = new ArrayList<Item>();
					MainApp.em.getTransaction().begin();
					for (Item i : selectedOrder.getItems()) {

						if (i.getItems_Quantity() <= 0) {
							tmpItemList.add(i);
						}
						if (i.getPreviousQuantity() >= 0) {
							i.getProduct()
									.setProducts_Stock(i.getProduct().getProducts_Stock() + i.getQuantityVariation());
						}
						i.setPreviousQuantity(-1);
					}

					for (Item i : tmpItemList) {
						selectedOrder.removeItem(i);
						MainApp.em.remove(i);
					}
					selectedOrder = MainApp.em.merge(selectedOrder);
					MainApp.em.getTransaction().commit();
				} catch (Exception e) {
					AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
					handleRefresh();
					return;
				}
				showOrderDetails(selectedOrder);
				orderTable.refresh();
			}

		} else {
			AlertUtil.alert("No selection", "Order not selected", "Please, select as order on the left table.");
		}
	}

	/**
	 * Handle toggle shipped.
	 */
	@FXML
	private void handleToggleShipped() {
		Shipping_Log newShipping_Log = new Shipping_Log();
		if (orderTable.getItems() != null && orderTable.getItems().size() > 0
				&& orderTable.getSelectionModel().getSelectedItem() != null) {
			Order o = orderTable.getSelectionModel().getSelectedItem();
			if (AlertUtil.askYesNoCancel((o.getOrders_Shipped() == 0 ? "Set the order status to SHIPPED?"
					: "Set the order status to NOT SHIPPED?")) == ButtonType.NO)
				return;
			o.setOrders_Shipped((byte) (o.getOrders_Shipped() == 0 ? 1 : 0));
			orderTable.refresh();

			try {
				MainApp.em.getTransaction().begin();

				if(o.getOrders_Shipped()==1){
					newShipping_Log.setIdOrders(o.getIdOrders());
					newShipping_Log.setWorkers_Name(mainApp.getWorker().getWorkers_Name());
					Calendar today = Calendar.getInstance();
					newShipping_Log.setShipping_Date(today.getTime());
					MainApp.em.merge(newShipping_Log);
				}



				MainApp.em.merge(o);
				MainApp.em.getTransaction().commit();
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				handleRefresh();
				return;
			}
			return;
		}
		AlertUtil.alert("No selection", "Order not selected", "Please, select as order on the left table.");
	}

	/**
	 * Handle delete.
	 */
	@FXML
	private void handleDelete() {

		if (orderTable.getItems() != null && orderTable.getItems().size() > 0
				&& orderTable.getSelectionModel().getSelectedItem() != null) {

			Order o = orderTable.getSelectionModel().getSelectedItem();
			if (o.getOrders_Shipped() == 1) {
				AlertUtil.alert("Order cannot be deleted", "Order cannot be deleted. It has already been shipped.",
						"To delete, mark order as not shipped.");
				return;
			}
			if (AlertUtil.askYesNoCancel("Delete Order?") == ButtonType.NO)
				return;
			mainApp.getOrdersData().remove(orderTable.getSelectionModel().getSelectedIndex());
			orderTable.refresh();

			try {
				MainApp.em.getTransaction().begin();
				for (Item i : o.getItems()) {
					Product p = i.getProduct();
					p.setProducts_Stock(p.getProducts_Stock() + i.getItems_Quantity());
					MainApp.em.merge(p);
				}
				MainApp.em.remove(o);
				MainApp.em.getTransaction().commit();
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				handleRefresh();
				
			}
			return;
		}
		AlertUtil.alert("No selection", "Order not selected", "Please, select as order on the left table.");
	}

	/**
	 * Handle refresh.
	 */
	@FXML
	private void handleRefresh() {
		try {
			MainApp.refreshEm();
			TypedQuery<Order> query = MainApp.em.createNamedQuery("Order.findAll", Order.class);
			mainApp.setOrdersData(query.getResultList());
			orderTable.setItems(mainApp.getOrdersData());
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
		}
		orderTable.refresh();
		return;
	}

}
