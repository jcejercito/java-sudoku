package application.computationlogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import application.problemdomain.Coordinates;
import application.problemdomain.SudokuGame;
import application.computationlogic.SudokuUtilities;
import application.constants.GameType;

public class GameGenerator {
	public static int[][] getNewGameGrid(){
		return unsolveGame(getSolvedGame());
	}
	
	private static int[][] unsolveGame(int[][] solvedGame){
		Random random = new Random(System.currentTimeMillis());
		
		boolean solvable = false;
		
		int[][] solvableArray;
		
		final int gridBoundary;
		int removalLimit;
		if(SudokuGame.getGameType() == GameType.EASY) {
			solvableArray = new int[SudokuGame.GRID_BOUNDARY_EASY][SudokuGame.GRID_BOUNDARY_EASY];
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
			removalLimit = 11;
		}
		else {
			solvableArray = new int[SudokuGame.GRID_BOUNDARY_HARD][SudokuGame.GRID_BOUNDARY_HARD];
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
			removalLimit = 68;
		}
		
		while(solvable == false) {
			SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);
			
			int index = 0;
			
			while(index < removalLimit) {
				int xCoordinate = random.nextInt(gridBoundary);
				int yCoordinate = random.nextInt(gridBoundary);
				
				if(solvableArray[xCoordinate][yCoordinate] != 0) {
					solvableArray[xCoordinate][yCoordinate] = 0;
					index++;
				}
			}
			
			int[][] toBeSolved = new int[gridBoundary][gridBoundary];
			SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);
			
			solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
		}
		
		return solvableArray;
	}
	
	private static int[][] getSolvedGame(){
		Random random = new Random(System.currentTimeMillis());
		int gridBoundary;
		
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		int[][] newGrid = new int[gridBoundary][gridBoundary];
		
		for(int value = 1; value <= gridBoundary; value++) {
			int allocations = 0;
			int interrupt = 0;
			
			List<Coordinates> allocTracker = new ArrayList<>();
			
			int attempts = 0;
			
			while(allocations < gridBoundary) {
				if(interrupt > 200) {
					allocTracker.forEach(coord ->{
						newGrid[coord.getX()][coord.getY()] = 0;
					});
					
					interrupt = 0;
					allocations = 0;
					allocTracker.clear();
					attempts++;
					
					if(attempts > 500) {
						clearArray(newGrid);
						attempts = 0;
						value = 1;
					}
				}
				
				int xCoordinate = random.nextInt(gridBoundary);
				int yCoordinate = random.nextInt(gridBoundary);
				
				if(newGrid[xCoordinate][yCoordinate] == 0) {
					newGrid[xCoordinate][yCoordinate] = value;
					
					if(GameLogic.sudokuIsInvalid(newGrid)) {
						newGrid[xCoordinate][yCoordinate] = 0;
						interrupt++;
					}
					else {
						allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
						allocations++;
					}
				}
			}
		}
		
		return newGrid;
	}
	
	private static void clearArray(int[][] newGrid) {
		int gridBoundary;
		
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		for(int xIndex = 0; xIndex < gridBoundary; xIndex++) {
			for(int yIndex = 0; yIndex < gridBoundary; xIndex++) {
				newGrid[xIndex][yIndex] = 0;
			}
		}
	}
}
