package application.userinterface.logic;

import java.io.IOException;

import application.Main;
import application.computationlogic.GameLogic;
import application.constants.GameState;
import application.constants.GameType;
import application.constants.Messages;
import application.problemdomain.IStorage;
import application.problemdomain.SudokuGame;
import application.userinterface.IUserInterfaceContract;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControlLogic implements IUserInterfaceContract.EventListener{

	private IStorage storage;
	
	private IUserInterfaceContract.View view;
	
	public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
		this.storage = storage;
		this.view = view;
	}
	
	@Override
	public void onSudokuInput(int x, int y, int input) {
		try {
			SudokuGame gameData = storage.getGameData();
			int[][] newGridState = gameData.getCopyOfGridState();
			newGridState[x][y] = input;
			
			gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState, SudokuGame.getGameType());
			
			storage.updateGameData(gameData);
			
			view.updateSquare(x, y, input);
			
			if(gameData.getGameState() == GameState.COMPLETE) {
				view.showDialog(Messages.GAME_COMPLETE);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			view.showError(Messages.ERROR);
		}
	}

	@Override
	public void onDialogClick() {
		System.exit(0);
	}

	
}
