package application.buildlogic;

import java.io.IOException;

import application.computationlogic.GameLogic;
import application.persistence.LocalStorageImpl;
import application.problemdomain.IStorage;
import application.problemdomain.SudokuGame;
import application.userinterface.IUserInterfaceContract;
import application.userinterface.gameBoardEasyController;
import application.userinterface.logic.ControlLogic;
import application.constants.GameType;

public class SudokuBuildLogic {
	public static SudokuGame build(IUserInterfaceContract.View userInterface, GameType gameType) throws IOException{
		SudokuGame initialState;
		IStorage storage = new LocalStorageImpl();
		
		try {
			storage.resetGameData(); //delete in the future future for game saving functionality
			initialState = storage.getGameData();
		} catch(IOException e) {
			if(gameType == GameType.EASY) {
				initialState = GameLogic.getNewGame(GameType.EASY);
			}
			else {
				initialState = GameLogic.getNewGame(GameType.HARD);
			}
			storage.updateGameData(initialState);
		}
		IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
		
		userInterface.setListener(uiLogic);
		//userInterface.updateBoard(initialState);
		return initialState;
	}
}
