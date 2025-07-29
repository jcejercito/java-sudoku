package application.userinterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import application.Main;
import application.buildlogic.SudokuBuildLogic;
import application.computationlogic.SudokuSolver;
import application.constants.GameState;
import application.constants.GameType;
import application.problemdomain.Coordinates;
import application.problemdomain.SudokuGame;
import application.userinterface.IUserInterfaceContract.EventListener;
import application.userinterface.logic.ControlLogic;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class gameBoardEasyController implements IUserInterfaceContract.View {
	@FXML
	private TextField tl_00;
	@FXML
	private TextField tl_11;
	@FXML
	private TextField tl_10;
	@FXML
	private TextField tl_01;
	
	@FXML
	private TextField bl_00;
	@FXML
	private TextField bl_11;
	@FXML
	private TextField bl_10;
	@FXML
	private TextField bl_01;
	
	@FXML
	private TextField br_00;
	@FXML
	private TextField br_11;
	@FXML
	private TextField br_10;
	@FXML
	private TextField br_01;
	
	@FXML
	private TextField tr_00;
	@FXML
	private TextField tr_11;
	@FXML
	private TextField tr_10;
	@FXML
	private TextField tr_01;
	
	private final Stage stage;
	private final Group root;
	
	private IUserInterfaceContract.EventListener listener;
	
	private HashMap<Coordinates, TextField> textFieldCoordinates;
	
	private IUserInterfaceContract.View uiImpl;
	
	public gameBoardEasyController(Stage stage) {
		this.stage = stage;
		this.root = new Group();
		this.textFieldCoordinates = new HashMap<>();
	}
	
	public gameBoardEasyController() {
		this.stage = Main.getStage();
		this.root = new Group();
		this.textFieldCoordinates = new HashMap<>();
	}
	
	@FXML
	private void initialize() throws IOException {
		naiveInitTextField();
		uiImpl = this;
		SudokuGame initialState = SudokuBuildLogic.build(uiImpl, GameType.EASY);
		updateBoard(initialState);
	}
	
	public void naiveInitTextField() {
		//Initializing the TextAreas manually
		//Modified the original algorithm

		textFieldCoordinates.put(new Coordinates(0, 0), tl_00);
		textFieldCoordinates.put(new Coordinates(1, 1), tl_11);
		textFieldCoordinates.put(new Coordinates(1, 0), tl_10);
		textFieldCoordinates.put(new Coordinates(0, 1), tl_01);
	
		textFieldCoordinates.put(new Coordinates(0, 2), bl_00);
		textFieldCoordinates.put(new Coordinates(1, 3), bl_11);
		textFieldCoordinates.put(new Coordinates(1, 2), bl_10);
		textFieldCoordinates.put(new Coordinates(0, 3), bl_01);
	
		textFieldCoordinates.put(new Coordinates(2, 0), tr_00);
		textFieldCoordinates.put(new Coordinates(3, 1), tr_11);
		textFieldCoordinates.put(new Coordinates(3, 0), tr_10);
		textFieldCoordinates.put(new Coordinates(2, 1), tr_01);
		
		textFieldCoordinates.put(new Coordinates(2, 2), br_00);
		textFieldCoordinates.put(new Coordinates(3, 3), br_11);
		textFieldCoordinates.put(new Coordinates(3, 2), br_10);
		textFieldCoordinates.put(new Coordinates(2, 3), br_01);
	}
	
	@Override
	public void setListener(EventListener listener) {
		this.listener = listener;
	}

	@Override
	public void updateSquare(int x, int y, int input) {
		TextField tile = textFieldCoordinates.get(new Coordinates(x, y));
		
		String value = Integer.toString(input);
		
		if(value.equals("0")) value = "";
		
		tile.textProperty().setValue(value);
	}

	@Override
	public void updateBoard(SudokuGame game) {
		for(int xIndex = 0; xIndex < 4; xIndex++) {
			for(int yIndex = 0; yIndex < 4; yIndex++) {
				TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));
				
				String value = Integer.toString(game.getCopyOfGridState()[xIndex][yIndex]);
				
				if (value.equals("0"))
					value = "";
				tile.setText(value);
				
				//If a given tile has a non-zero value and the state of the game is GameState.NEW, then mark
                //the tile as read only. Otherwise, ensure that it is NOT read only.
				if (game.getGameState() == GameState.NEW) {
					if (value.equals("")) {
						tile.setStyle("-fx-opacity: 1;");
						tile.setDisable(false);
					} else {
						tile.setStyle("-fx-opacity: 0.8; -fx-background-color: blanchedalmond;");
						tile.setDisable(true);
					}
				}
			}
		}
	}

	@FXML
	public void giveUp(ActionEvent event) {
		SudokuSolver solve = new SudokuSolver();
		solve.solveBoard(uiImpl);
	}
	
	@FXML
	public void onUserInput(KeyEvent event) {
		//Getting coordinates since we are not using SudokuTextField
		Coordinates coords = getCoordinates((TextField) event.getSource());
		int x = coords.getX();
		int y = coords.getY();
		
		String input = ((TextField) event.getSource()).getText();
		int intInput;
		if(!input.equals("") && isNumeric(input)) {
			intInput = Integer.parseInt(input);
		}
		else {
			intInput = 0;
		}
		
		listener.onSudokuInput(x, y, intInput);
	}
	
	private boolean isNumeric(String input) {
		try {
	        int intValue = Integer.parseInt(input);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	private Coordinates getCoordinates(TextField textField) {
		// iterate each entry of hashmap
		for (Entry<Coordinates, TextField> entry : textFieldCoordinates.entrySet()) {

			// if give value is equal to value from entry
			// print the corresponding key
			if (entry.getValue() == textField) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	@Override
	public void showDialog(String message) {
		System.out.println("Game complete");
		Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
	}

	@Override
	public void showError(String message) {
		// TODO - Create a popup dialog that shows the error message and close the program.
		
	}
	
}
