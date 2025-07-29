package application.computationlogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.constants.GameState;
import application.constants.GameType;
import application.constants.Messages;
import application.persistence.LocalStorageImpl;
import application.problemdomain.Coordinates;
import application.problemdomain.IStorage;
import application.problemdomain.SudokuGame;
import application.userinterface.IUserInterfaceContract;
import application.problemdomain.IStorage;


public class SudokuSolver {
	private IStorage storage = new LocalStorageImpl();
	int[][] newGridState;
	
	public static boolean puzzleIsSolvable(int[][] puzzle) {
		Coordinates[] emptyCells = typeWriterEnumerate(puzzle);
		
		int index = 0;
		int input = 1;
		
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		while(index < 10) {
			Coordinates current = emptyCells[index];
			input = 1;
			
			while(input < 40) {
				puzzle[current.getX()][current.getY()] = input;
				
				if(GameLogic.sudokuIsInvalid(puzzle)) {
					if(index == 0 && input == gridBoundary) {
						return false;
					}
					else if(input == gridBoundary){
						index--;
					}
					input++;
				}
				else {
					index++;
					
					if(index == 39) return true;
					
					input = 10;
				}
			}
		}
		return false;
	}
	
	private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
		Coordinates[] emptyCells = new Coordinates[40];
		int iterator = 0;
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		for(int y = 0; y < gridBoundary; y++) {
			for(int x = 0; x < gridBoundary; x++) {
				if(puzzle[x][y] == 0) {
					emptyCells[iterator] = new Coordinates(x, y);
					
					if(iterator == 39) return emptyCells;
					iterator++;
				}
			}
		}
		
		return emptyCells;
	}
	
	private static boolean isNumberInRow(int[][] board, int number, int row, int gridBoundary) {		
		for(int i = 0; i < gridBoundary; i++) {
			if(board[row][i] == number) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isNumberInColumn(int[][] board, int number, int column, int gridBoundary) {
		for(int i = 0; i < gridBoundary; i++) {
			if(board[i][column] == number) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isNumberInBox(int[][] board, int number, int row, int column) {
		int localBoxRow;
		int localBoxColumn;
		if(SudokuGame.getGameType() == GameType.EASY) {
			localBoxRow = row - row % 2;
			localBoxColumn = column - column % 2;
		}
		else {
			localBoxRow = row - row % 3;
			localBoxColumn = column - column % 3;
		}
		
		
		for(int i = localBoxRow; i < localBoxRow+2; i++) {
			for(int j = localBoxColumn; j < localBoxColumn+2; j++) {
				if(board[i][j] == number) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		return !isNumberInRow(board, number, row, gridBoundary) &&
				!isNumberInColumn(board, number, column, gridBoundary) &&
				!isNumberInBox(board, number, row, column);
	}
	
	private static boolean solveBoardUtil(int[][] board, int gridBoundary) {
		/*Original Code
		for(int row = 0; row < gridBoundary; row++) {
			for(int column = 0; column < gridBoundary; column++) {
				if(board[row][column] == 0) {
					for(int numberToTry = 1; numberToTry <= 9; numberToTry++) {
						if(isValidPlacement(board, numberToTry, row, column)) {
							board[row][column] = numberToTry;
							
							if(solveBoardUtil(board, gridBoundary)) {
								return true;
							}
							else {
								board[row][column] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
		
		=-End of Original Code-=*/
		
		//Start of modified code
		//Get empty square
		int xCoord = -1;
		int yCoord = -1;
		for(int i = 0; i < gridBoundary; i++) {
			boolean end = false;
			for(int j = 0; j < gridBoundary; j++) {
				if(isEmpty(board[j][i])) {
					xCoord = j;
					yCoord = i;
					end = true;
					break;
				}
			}
			if(end == true) {
				break;
			}
		}
		
		if(xCoord != -1 && yCoord != -1) {
			System.out.printf("\n\n=======Empty Value at %d, %d======", xCoord, yCoord);
			System.out.println("\nTrying following value:");
			ArrayList<Integer> possibleValues = getPossibility(board, xCoord, yCoord);
			for(Integer possibleValue : possibleValues) {
				System.out.print(possibleValue);
				board[xCoord][yCoord] = possibleValue;
				
				if(solveBoardUtil(board, gridBoundary)) {
					return true;
				}
				else {
					board[xCoord][yCoord] = 0;
				}
			}
			return false;
		}
		else {
			return true;
		}
	}
	
	public void solveBoard(IUserInterfaceContract.View uiImpl) {
		try {
			int gridBoundary;
			if(SudokuGame.getGameType() == GameType.EASY) {
				gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
			}
			else {
				gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
			}
			
			SudokuGame gameData = storage.getGameData();
			newGridState = gameData.getCopyOfGridState();
			solveBoardUtil(newGridState, gridBoundary);
			
			gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState, SudokuGame.getGameType());
			
			storage.updateGameData(gameData);
			
			uiImpl.updateBoard(gameData);
			if(gameData.getGameState() == GameState.COMPLETE) {
				uiImpl.showDialog(Messages.GAME_COMPLETE);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			uiImpl.showError(Messages.ERROR);
		}
	}
	
	//Additional methods for the change in algorithm
	public static ArrayList<Integer> getPossibility(int[][] board, int col, int row) {
		ArrayList<Integer> possibleValues = new ArrayList<Integer>();
		
		for(int i = 1; i <= 9; i++) {
			if(isValidPlacement(board, i, col, row)) {
				possibleValues.add(i);
			}
		}
		
		return possibleValues;
	}
	
	public static boolean isEmpty(int tile) {
		return tile == 0;
	}
}
