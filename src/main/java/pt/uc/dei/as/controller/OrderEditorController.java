/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.controlsfx.control.textfield.TextFields;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.entity.*;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderEditorController.
 */
public class OrderEditorController {

	/** The name field. */
	@FXML
	private TextField nameField;
	
	/** The telephone field. */
	@FXML
	private TextField telephoneField;
	
	/** The address area. */
	@FXML
	private TextArea addressArea;
	
	/** The email field. */
	@FXML
	private TextField emailField;
	
	/** The total price label. */
	@FXML
	private Label totalPriceLabel;

	/** The items table. */
	@FXML
	private TableView<Item> itemsTable;
	
	/** The product name column. */
	@FXML
	private TableColumn<Item, String> productNameColumn;
	
	/** The unit price column. */
	@FXML
	private TableColumn<Item, String> unitPriceColumn;
	
	/** The quantity column. */
	@FXML
	private TableColumn<Item, String> quantityColumn;
	
	/** The products combo box. */
	@FXML
	private ComboBox<String> productsComboBox;
	
	/** The trees radio button. */
	@FXML
	private RadioButton treesRadioButton;
	
	/** The shrubs radio button. */
	@FXML
	private RadioButton shrubsRadioButton;
	
	/** The seeds radio button. */
	@FXML
	private RadioButton seedsRadioButton;
	
	/** The toggle group. */
	@FXML
	private ToggleGroup toggleGroup = new ToggleGroup();

	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The order. */
	private Order order;
	
	/** The save clicked. */
	private boolean saveClicked = false;

	/** The client. */
	private Client client;

	/** The items data. */
	private ObservableList<Item> itemsData = FXCollections.observableArrayList();
	
	/** The products data. */
	private ObservableList<String> productsData = FXCollections.observableArrayList();
	
	/** The clients data. */
	private ObservableList<Client> clientsData = FXCollections.observableArrayList();

	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {
		productNameColumn
				.setCellValueFactory(cellData -> cellData.getValue().getProduct().products_DescriptionProperty());
		unitPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().products_PriceProperty());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().items_QuantityProperty());

		treesRadioButton.setToggleGroup(toggleGroup);
		treesRadioButton.setUserData("Tree");
		shrubsRadioButton.setToggleGroup(toggleGroup);
		shrubsRadioButton.setUserData("Shrub");
		seedsRadioButton.setToggleGroup(toggleGroup);
		seedsRadioButton.setUserData("Seed");
		toggleGroup.selectToggle(treesRadioButton);
		List<String> list = new ArrayList<String>();

		try {
			TypedQuery<Product> queryP = MainApp.em.createNamedQuery("Product.findProductsByType", Product.class);
			queryP.setParameter("product_type", toggleGroup.getSelectedToggle().getUserData().toString());
			for (Product p : queryP.getResultList()) {
				productsData.add(p.getProducts_Description());
			}

			TypedQuery<Client> queryC = MainApp.em.createNamedQuery("Client.findAll", Client.class);
			for (Client c : queryC.getResultList()) {
				clientsData.add(c);
				list.add(c.getClients_Name());
			}
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
		}

		TextFields.bindAutoCompletion(nameField, list);

		nameField.textProperty().addListener((observable, oldValue, newValue) -> {
			handleNameFiedlChanged();
		});

		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> obs, Toggle wasPreviouslySelected,
					Toggle isNowSelected) {
				try {
					List<String> list = new ArrayList<String>();
					TypedQuery<Product> queryP = MainApp.em.createNamedQuery("Product.findProductsByType",
							Product.class);
					queryP.setParameter("product_type", isNowSelected.getUserData().toString());
					for (Product p : queryP.getResultList()) {
						list.add(p.getProducts_Description());
					}
					productsData.setAll(list);
				} catch (Exception e) {
					AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
					MainApp.refreshEm();
					return;
				}
				if (productsData.get(0) != null)
					productsComboBox.setValue(productsData.get(0));
				else
					productsComboBox.setValue(null);
			}
		});

		productsComboBox.setItems(productsData);
		if (productsData.get(0) != null)
			productsComboBox.setValue(productsData.get(0));

	}

	/**
	 * Sets the dialog stage.
	 *
	 * @param dialogStage the new dialog stage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(Order order) {
		this.order = order;
		if (order.getOrders_Date() != null) {

			nameField.setText(order.getClient().getClients_Name());
			telephoneField.setText(order.getClient().getClients_Telephone());
			addressArea.setText(order.getClient().getClients_Address());
			emailField.setText(order.getClient().getClients_Email());
			totalPriceLabel.setText(order.getOrders_Total_Cost().toString());
			itemsData.clear();
			for (Item i : order.getItems()) {
				itemsData.add(i);
			}
			itemsTable.setItems(itemsData);
			updateTotalPriceLabel();
		}
	}

	/**
	 * Checks if is save clicked.
	 *
	 * @return true, if is save clicked
	 */
	public boolean isSaveClicked() {
		return saveClicked;
	}

	/**
	 * Handle add.
	 */
	@FXML
	private void handleAdd() {
		int quantity = 1;
		Product p = new Product();

		if (productsComboBox.getSelectionModel().getSelectedItem() != null) {
			try {
				TypedQuery<Product> queryP = MainApp.em.createNamedQuery("Product.findProductByName", Product.class);
				queryP.setParameter("pname", productsComboBox.getSelectionModel().getSelectedItem());
				try {
					p = queryP.getSingleResult();
				} catch (NoResultException nre) {
					return;
				} catch (NonUniqueResultException nure) {
					p = queryP.getResultList().get(0);
					return;
				}
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				MainApp.refreshEm();
				return;
			}

		} else {
			AlertUtil.alert("Product not selected", "No product selected", "Please, select a product from the list.");
			return;
		}

		ItemPK iPk = new ItemPK();
		iPk.setOrders_idOrders(order.getIdOrders());
		iPk.setProducts_idProducts(p.getIdProducts());

		boolean exists = false;

		for (Item iTmp : itemsData.toArray(new Item[0])) {
			if (iTmp.getId().getOrders_idOrders() == iPk.getOrders_idOrders()
					&& iTmp.getId().getProducts_idProducts() == iPk.getProducts_idProducts()) {
				//iTmp.setItems_Quantity(quantity);
				exists = true;
				break;
			}
		}
		if (!exists) {
			exists = false;
			for (Item iTmp : order.getItems()) {
				if (iTmp.getId().getOrders_idOrders() == iPk.getOrders_idOrders()
						&& iTmp.getId().getProducts_idProducts() == iPk.getProducts_idProducts()) {
					iTmp.setItems_Quantity(quantity);
					exists = true;
					itemsData.add(iTmp);
					break;
				}
			}
			if (!exists) {
				Item i = new Item();
				i.setId(iPk);
				i.setItems_Quantity(quantity);
				i.setPreviousQuantity(0);
				i.setItems_Unit_Price(p.getProducts_Price());
				i.setOrder(order);
				i.setProduct(p);
				itemsData.add(i);
			}
			itemsTable.setItems(itemsData);
			itemsTable.refresh();
			updateTotalPriceLabel();
		} else
			AlertUtil.alert("Product already on the list", "Orders cannot contain duplicate entries",
					"Please, update the quantity of the product or select a diferent product.");

	}

	/**
	 * Handle plus.
	 */
	@FXML
	private void handlePlus() {
		updateItemQuantity(1);
	}

	/**
	 * Handle minus.
	 */
	@FXML
	private void handleMinus() {
		updateItemQuantity(-1);
	}

	/**
	 * Update item quantity.
	 *
	 * @param value the value
	 */
	private void updateItemQuantity(int value) {
		if (itemsTable.getSelectionModel().getSelectedItem() != null) {
			Item i = itemsTable.getSelectionModel().getSelectedItem();
			if (i.getPreviousQuantity() < 0)
				i.setPreviousQuantity(i.getItems_Quantity());
			i.setItems_Quantity(i.getItems_Quantity() + value);
			if (i.getItems_Quantity() <= 0) {
				i.setItems_Quantity(0);
				itemsData.remove(i);
			}
			itemsTable.setItems(itemsData);
			itemsTable.refresh();
			updateTotalPriceLabel();
		} else {
			AlertUtil.alert("Item not selected", "No Item selected", "Please, select an item from the list.");
			return;
		}
	}

	/**
	 * Update total price label.
	 */
	private void updateTotalPriceLabel() {
		if (itemsData != null) {
			double sum = 0;
			for (Item i : itemsData.toArray(new Item[0])) {
				sum += i.getItems_Quantity() * i.getItems_Unit_Price().doubleValue();
			}
			totalPriceLabel.setText(sum + "");
			order.setOrders_Total_Cost(new BigDecimal(sum));
			return;
		}
		totalPriceLabel.setText("0");
	}

	/**
	 * Handle name fiedl changed.
	 */
	private void handleNameFiedlChanged() {
		try {
			TypedQuery<Client> queryC = MainApp.em.createNamedQuery("Client.findClient", Client.class);
			Client c;
			queryC.setParameter("clients_name", nameField.getText());
			try {
				c = queryC.getSingleResult();
				telephoneField.setText(c.getClients_Telephone());
				addressArea.setText(c.getClients_Address());
				emailField.setText(c.getClients_Email());
				this.client = c;
			} catch (NoResultException nre) {
				telephoneField.setText("");
				addressArea.setText("");
				emailField.setText("");
				this.client = null;
			}
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
			return;
		}

	}

	/**
	 * Handle save.
	 */
	@FXML
	private void handleSave() {
		if (AlertUtil.askYesNoCancel("Save order?") == ButtonType.NO)
			return;
		if (isInputValid()) {
			try {
				TypedQuery<Client> queryC = MainApp.em.createNamedQuery("Client.findClient", Client.class);
				Client c;
				queryC.setParameter("clients_name", nameField.getText());
				try {
					c = queryC.getSingleResult();
					c.setClients_Address(this.addressArea.getText());
					c.setClients_Email(this.emailField.getText());
					c.setClients_Telephone(this.telephoneField.getText());
					order.setClient(c);
				} catch (NoResultException nre) {
					if (order.getClient() == null && this.client == null)
						this.client = new Client();
					else if (order.getClient() != null)
						this.client = order.getClient();
					this.client.setClients_Address(this.addressArea.getText());
					this.client.setClients_Email(this.emailField.getText());
					this.client.setClients_Name(this.nameField.getText());
					this.client.setClients_Telephone(this.telephoneField.getText());
					order.setClient(this.client);
				}
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				MainApp.refreshEm();
				return;
			}

			if (order.getOrders_Date() == null) {
				Calendar today = Calendar.getInstance();
				order.setOrders_Date(today.getTime());
			}
			
			List <Item> newItems = new ArrayList<Item>();
			for (Item i: itemsData) 
				if (!hasProduct(i.getProduct(), order.getItems()))
						newItems.add(i);
			for (Item i:newItems)
				order.addItem(i);
			
			saveClicked = true;
			dialogStage.close();
		}
	}
	
	
	/**
	 * Checks for product.
	 *
	 * @param p the p
	 * @param orderItemList the order item list
	 * @return true, if successful
	 */
	private boolean hasProduct(Product p, List<Item> orderItemList) {
		for (Item i:orderItemList) 
			if (i.getProduct().getIdProducts() == p.getIdProducts()) 
				return true;
		return false;
	}

	/**
	 * Handle cancel.
	 */
	@FXML
	private void handleCancel() {
		
		if (AlertUtil.askYesNoCancel("Changes were not saved. Continue?") == ButtonType.NO)
			return;
		dialogStage.close();
	}

	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (nameField.getText() == null || nameField.getText().length() == 0 || nameField.getText().length() > 99) {
			errorMessage += "Invalid name!\n";
		}

		if (telephoneField.getText() == null || telephoneField.getText().length() == 0
				|| telephoneField.getText().length() > 14) {
			errorMessage += "Invalid telephone number!\n";
		}
		if (addressArea.getText() == null || addressArea.getText().length() == 0
				|| addressArea.getText().length() > 249) {
			errorMessage += "Invalid address!\n";
		}
		if (emailField.getText() == null || emailField.getText().length() == 0 || emailField.getText().length() > 99) {
			errorMessage += "Invalid email address!\n";
		}

		if (itemsData.size() == 0) {
			errorMessage += "No products ordered!\n";
		}

		if (errorMessage.length() == 0)
			return true;

		AlertUtil.alert("Invalid inputs", "Please, correct the following problems.", errorMessage);
		return false;

	}
}
