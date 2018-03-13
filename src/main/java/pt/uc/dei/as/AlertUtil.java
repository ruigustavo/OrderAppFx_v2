/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * The Class AlertUtil.
 */
public class AlertUtil {
	
	/** The Constant styleSheet. */
	private static final String styleSheet = "/stylesheets/DarkTheme.css";
	
	/**
	 * Alert.
	 *
	 * @param msg1 the msg 1
	 * @param msg2 the msg 2
	 * @param msg3 the msg 3
	 * @return the button type
	 */
	public static ButtonType alert(String msg1, String msg2, String msg3) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.getDialogPane().getStylesheets().add(styleSheet);
		alert.setTitle(msg1);
		alert.setHeaderText(msg2);
		alert.setContentText(msg3);
		return alert.showAndWait().get();
	}

	/**
	 * Ask yes no cancel.
	 *
	 * @param question the question
	 * @return the button type
	 */
	public static ButtonType askYesNoCancel(String question) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getDialogPane().getStylesheets().add(styleSheet);
		alert.setContentText(question);
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		return alert.showAndWait().get();
	}
}
