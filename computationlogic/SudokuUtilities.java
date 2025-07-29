package application.computationlogic;

import application.constants.GameType;
import application.problemdomain.SudokuGame;

public class SudokuUtilities {
	public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		for(int xIndex = 0; xIndex < gridBoundary; xIndex++) {
			for(int yIndex = 0; yIndex < gridBoundary; yIndex++) {
				newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
			}
		}
	}
	
	public static int[][] copyToNewArray(int[][] oldArray) {
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		int[][] newArray = new int[gridBoundary][gridBoundary];
		
		for(int xIndex = 0; xIndex < gridBoundary; xIndex++) {
			for(int yIndex = 0; yIndex < gridBoundary; yIndex++) {
				newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
			}
		}
		
		return newArray;
	}
	
}
