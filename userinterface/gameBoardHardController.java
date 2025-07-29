package application.userinterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import application.Main;
import application.buildlogic.SudokuBuildLogic;
import application.computationlogic.SudokuSolver;
import application.constants.GameState;
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
import application.constants.GameType;

public class gameBoardHardController implements IUserInterfaceContract.View {
	
	@FXML
	private TextField tl_00;
	@FXML
	private TextField tl_01;
	@FXML
	private TextField tl_10;
	@FXML
	private TextField tl_11;
	@FXML
	private TextField tl_20;
	@FXML
	private TextField tl_21;
	@FXML
	private TextField tl_22;
	@FXML
	private TextField tl_12;
	@FXML
	private TextField tl_02;
	
	@FXML
	private TextField tm_00;
	@FXML
	private TextField tm_01;
	@FXML
	private TextField tm_10;
	@FXML
	private TextField tm_11;
	@FXML
	private TextField tm_20;
	@FXML
	private TextField tm_21;
	@FXML
	private TextField tm_22;
	@FXML
	private TextField tm_12;
	@FXML
	private TextField tm_02;
	
	@FXML
	private TextField tr_00;
	@FXML
	private TextField tr_01;
	@FXML
	private TextField tr_10;
	@FXML
	private TextField tr_11;
	@FXML
	private TextField tr_20;
	@FXML
	private TextField tr_21;
	@FXML
	private TextField tr_22;
	@FXML
	private TextField tr_12;
	@FXML
	private TextField tr_02;
	
	@FXML
	private TextField ml_00;
	@FXML
	private TextField ml_01;
	@FXML
	private TextField ml_10;
	@FXML
	private TextField ml_11;
	@FXML
	private TextField ml_20;
	@FXML
	private TextField ml_21;
	@FXML
	private TextField ml_22;
	@FXML
	private TextField ml_12;
	@FXML
	private TextField ml_02;
	
	@FXML
	private TextField mm_00;
	@FXML
	private TextField mm_01;
	@FXML
	private TextField mm_10;
	@FXML
	private TextField mm_11;
	@FXML
	private TextField mm_20;
	@FXML
	private TextField mm_21;
	@FXML
	private TextField mm_22;
	@FXML
	private TextField mm_12;
	@FXML
	private TextField mm_02;
	
	@FXML
	private TextField mr_00;
	@FXML
	private TextField mr_01;
	@FXML
	private TextField mr_10;
	@FXML
	private TextField mr_11;
	@FXML
	private TextField mr_20;
	@FXML
	private TextField mr_21;
	@FXML
	private TextField mr_22;
	@FXML
	private TextField mr_12;
	@FXML
	private TextField mr_02;
	
	@FXML
	private TextField bl_00;
	@FXML
	private TextField bl_01;
	@FXML
	private TextField bl_10;
	@FXML
	private TextField bl_11;
	@FXML
	private TextField bl_20;
	@FXML
	private TextField bl_21;
	@FXML
	private TextField bl_22;
	@FXML
	private TextField bl_12;
	@FXML
	private TextField bl_02;
	
	@FXML
	private TextField bm_00;
	@FXML
	private TextField bm_01;
	@FXML
	private TextField bm_10;
	@FXML
	private TextField bm_11;
	@FXML
	private TextField bm_20;
	@FXML
	private TextField bm_21;
	@FXML
	private TextField bm_22;
	@FXML
	private TextField bm_12;
	@FXML
	private TextField bm_02;
	
	@FXML
	private TextField br_00;
	@FXML
	private TextField br_01;
	@FXML
	private TextField br_10;
	@FXML
	private TextField br_11;
	@FXML
	private TextField br_20;
	@FXML
	private TextField br_21;
	@FXML
	private TextField br_22;
	@FXML
	private TextField br_12;
	@FXML
	private TextField br_02;
	
	private final Stage stage;
	private final Group root;
	
	private IUserInterfaceContract.EventListener listener;
	
	private HashMap<Coordinates, TextField> textFieldCoordinates;
	
	private IUserInterfaceContract.View uiImpl;
	
	public gameBoardHardController(Stage stage) {
		this.stage = stage;
		this.root = new Group();
		this.textFieldCoordinates = new HashMap<>();
	}
	
	public gameBoardHardController() {
		this.stage = Main.getStage();
		this.root = new Group();
		this.textFieldCoordinates = new HashMap<>();
	}
	
	@FXML
	private void initialize() throws IOException {
		naiveInitTextField();
		uiImpl = this;
		SudokuGame initialState = SudokuBuildLogic.build(uiImpl, GameType.HARD);
		updateBoard(initialState);
	}
	
	public void naiveInitTextField() {
		//Initializing the TextAreas manually
		//Modified the original algorithm

		textFieldCoordinates.put(new Coordinates(0, 0), tl_00);
		textFieldCoordinates.put(new Coordinates(1, 0), tl_10);
		textFieldCoordinates.put(new Coordinates(2, 0), tl_20);
		textFieldCoordinates.put(new Coordinates(0, 1), tl_01);
		textFieldCoordinates.put(new Coordinates(1, 1), tl_11);
		textFieldCoordinates.put(new Coordinates(2, 1), tl_21);
		textFieldCoordinates.put(new Coordinates(0, 2), tl_02);
		textFieldCoordinates.put(new Coordinates(1, 2), tl_12);
		textFieldCoordinates.put(new Coordinates(2, 2), tl_22);
	
		textFieldCoordinates.put(new Coordinates(3, 0), tm_00);
		textFieldCoordinates.put(new Coordinates(4, 0), tm_10);
		textFieldCoordinates.put(new Coordinates(5, 0), tm_20);
		textFieldCoordinates.put(new Coordinates(3, 1), tm_01);
		textFieldCoordinates.put(new Coordinates(4, 1), tm_11);
		textFieldCoordinates.put(new Coordinates(5, 1), tm_21);
		textFieldCoordinates.put(new Coordinates(3, 2), tm_02);
		textFieldCoordinates.put(new Coordinates(4, 2), tm_12);
		textFieldCoordinates.put(new Coordinates(5, 2), tm_22);
	
		textFieldCoordinates.put(new Coordinates(6, 0), tr_00);
		textFieldCoordinates.put(new Coordinates(7, 0), tr_10);
		textFieldCoordinates.put(new Coordinates(8, 0), tr_20);
		textFieldCoordinates.put(new Coordinates(6, 1), tr_01);
		textFieldCoordinates.put(new Coordinates(7, 1), tr_11);
		textFieldCoordinates.put(new Coordinates(8, 1), tr_21);
		textFieldCoordinates.put(new Coordinates(6, 2), tr_02);
		textFieldCoordinates.put(new Coordinates(7, 2), tr_12);
		textFieldCoordinates.put(new Coordinates(8, 2), tr_22);
		
		textFieldCoordinates.put(new Coordinates(0, 3), ml_00);
		textFieldCoordinates.put(new Coordinates(1, 3), ml_10);
		textFieldCoordinates.put(new Coordinates(2, 3), ml_20);
		textFieldCoordinates.put(new Coordinates(0, 4), ml_01);
		textFieldCoordinates.put(new Coordinates(1, 4), ml_11);
		textFieldCoordinates.put(new Coordinates(2, 4), ml_21);
		textFieldCoordinates.put(new Coordinates(0, 5), ml_02);
		textFieldCoordinates.put(new Coordinates(1, 5), ml_12);
		textFieldCoordinates.put(new Coordinates(2, 5), ml_22);
		
		textFieldCoordinates.put(new Coordinates(3, 3), mm_00);
		textFieldCoordinates.put(new Coordinates(4, 3), mm_10);
		textFieldCoordinates.put(new Coordinates(5, 3), mm_20);
		textFieldCoordinates.put(new Coordinates(3, 4), mm_01);
		textFieldCoordinates.put(new Coordinates(4, 4), mm_11);
		textFieldCoordinates.put(new Coordinates(5, 4), mm_21);
		textFieldCoordinates.put(new Coordinates(3, 5), mm_02);
		textFieldCoordinates.put(new Coordinates(4, 5), mm_12);
		textFieldCoordinates.put(new Coordinates(5, 5), mm_22);
		
		textFieldCoordinates.put(new Coordinates(6, 3), mr_00);
		textFieldCoordinates.put(new Coordinates(7, 3), mr_10);
		textFieldCoordinates.put(new Coordinates(8, 3), mr_20);
		textFieldCoordinates.put(new Coordinates(6, 4), mr_01);
		textFieldCoordinates.put(new Coordinates(7, 4), mr_11);
		textFieldCoordinates.put(new Coordinates(8, 4), mr_21);
		textFieldCoordinates.put(new Coordinates(6, 5), mr_02);
		textFieldCoordinates.put(new Coordinates(7, 5), mr_12);
		textFieldCoordinates.put(new Coordinates(8, 5), mr_22);
		
		textFieldCoordinates.put(new Coordinates(0, 6), bl_00);
		textFieldCoordinates.put(new Coordinates(1, 6), bl_10);
		textFieldCoordinates.put(new Coordinates(2, 6), bl_20);
		textFieldCoordinates.put(new Coordinates(0, 7), bl_01);
		textFieldCoordinates.put(new Coordinates(1, 7), bl_11);
		textFieldCoordinates.put(new Coordinates(2, 7), bl_21);
		textFieldCoordinates.put(new Coordinates(0, 8), bl_02);
		textFieldCoordinates.put(new Coordinates(1, 8), bl_12);
		textFieldCoordinates.put(new Coordinates(2, 8), bl_22);
		
		textFieldCoordinates.put(new Coordinates(3, 6), bm_00);
		textFieldCoordinates.put(new Coordinates(4, 6), bm_10);
		textFieldCoordinates.put(new Coordinates(5, 6), bm_20);
		textFieldCoordinates.put(new Coordinates(3, 7), bm_01);
		textFieldCoordinates.put(new Coordinates(4, 7), bm_11);
		textFieldCoordinates.put(new Coordinates(5, 7), bm_21);
		textFieldCoordinates.put(new Coordinates(3, 8), bm_02);
		textFieldCoordinates.put(new Coordinates(4, 8), bm_12);
		textFieldCoordinates.put(new Coordinates(5, 8), bm_22);
		
		textFieldCoordinates.put(new Coordinates(6, 6), br_00);
		textFieldCoordinates.put(new Coordinates(7, 6), br_10);
		textFieldCoordinates.put(new Coordinates(8, 6), br_20);
		textFieldCoordinates.put(new Coordinates(6, 7), br_01);
		textFieldCoordinates.put(new Coordinates(7, 7), br_11);
		textFieldCoordinates.put(new Coordinates(8, 7), br_21);
		textFieldCoordinates.put(new Coordinates(6, 8), br_02);
		textFieldCoordinates.put(new Coordinates(7, 8), br_12);
		textFieldCoordinates.put(new Coordinates(8, 8), br_22);
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
		for(int xIndex = 0; xIndex < 9; xIndex++) {
			for(int yIndex = 0; yIndex < 9; yIndex++) {
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
		Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
	}

	@Override
	public void showError(String message) {
		// TODO - Create a popup dialog that shows the error message and close the program.
		
	}
	
}
