/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.Calendar;
// TODO: Auto-generated Javadoc

/**
 * The Class OrderOverviewController.
 */

public class LoginScreenController {
	/**
	 * Instantiates a new login controller.
	 */
	
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;


	private MainApp mainApp;

	public LoginScreenController() {

	}

	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the main app.
	 *
	 * @param mainApp the new main app
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleLogin() {
		Workers_Log newLogin = new Workers_Log();
		if(this.usernameField.getText().isEmpty() || this.passwordField.getText().isEmpty()) {
			AlertUtil.alert("Empty data", "Please, fill every field.", "Try Again");
		} else {
			try{
				TypedQuery<Worker> queryW = MainApp.em.createNamedQuery("Worker.findWorker", Worker.class);
				Worker w;
				queryW.setParameter("workers_name", usernameField.getText());
				w = queryW.getSingleResult();
				if(w.getWorkers_Password().equals(this.passwordField.getText())) {
						mainApp.setWorker(w);
						MainApp.em.getTransaction().begin();
						newLogin.setIdWorkers(w.getIdWorkers());
						newLogin.setWorkers_Name(w.getWorkers_Name());
						newLogin.setCheck_point(0); //0 - Login / 1 - Log out
						Calendar today = Calendar.getInstance();
						newLogin.setIn_out_Date(today.getTime());
						MainApp.em.merge(newLogin);
						MainApp.em.getTransaction().commit();
						showOrderOverview();
				}
				else{
					AlertUtil.alert("Login Error","Wrong Username or Password","Try Again");			}
			}catch (Exception e) {
				AlertUtil.alert("Login Error","Wrong Username or Password","Try Again");
				MainApp.refreshEm();
			}
				
		}
		MainApp.refreshEm();
	}

	public void showOrderOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/OrderOverview.fxml"));
			AnchorPane orderOverview = (AnchorPane) loader.load();
			orderOverview.getStylesheets().add("/stylesheets/DarkTheme.css");

			mainApp.getRootLayout().setCenter(orderOverview);
			OrderOverviewController controller = loader.getController();
			controller.setMainApp(this.mainApp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
