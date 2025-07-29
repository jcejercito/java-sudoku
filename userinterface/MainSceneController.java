package application.userinterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import application.Main;
import application.buildlogic.SudokuBuildLogic;
import javafx.event.ActionEvent;

public class MainSceneController {
	@FXML
	private Button easyBtn;
	@FXML
	private Button hardBtn;

	// Event Listener on Button[#easyBtn].onAction
	@FXML
	public void loadEasy(ActionEvent event) throws IOException {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/gameBoardEasy.fxml"));
			Scene scene = new Scene(root);
			
			Stage stage = Main.getStage();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	// Event Listener on Button[#hardBtn].onAction
	@FXML
	public void loadHard(ActionEvent event) throws IOException {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/gameBoardHard.fxml"));
			Scene scene = new Scene(root);
			
			Stage stage = Main.getStage();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
