package application.problemdomain;

import java.io.Serializable;

import application.computationlogic.SudokuUtilities;
import application.constants.GameState;
import application.constants.GameType;

public class SudokuGame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final GameState gameState;
	private final int[][] gridState;
	private static GameType gameType;
	
	public static final int GRID_BOUNDARY_EASY = 4;
	public static final int GRID_BOUNDARY_HARD = 9;
	
	public SudokuGame(GameState gameState, int[][] gridState, GameType gameType) {
		this.gameState = gameState;
		this.gridState = gridState;
		SudokuGame.gameType = gameType;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public static GameType getGameType() {
		return gameType;
	}
	
	public int[][] getCopyOfGridState(){
		return SudokuUtilities.copyToNewArray(gridState);
	}
}
