package application.computationlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.constants.GameState;
import application.problemdomain.SudokuGame;
import application.constants.Rows;
import application.constants.GameType;

public class GameLogic {
	public static SudokuGame getNewGame(GameType gameType) {
		return new SudokuGame(GameState.NEW, GameGenerator.getNewGameGrid(), gameType);
	}
	
	public static GameState checkForCompletion(int[][] grid) {
		if (sudokuIsInvalid(grid))
			return GameState.ACTIVE;
		if (tilesAreNotFilled(grid))
			return GameState.ACTIVE;
		return GameState.COMPLETE;
	}
	
	public static boolean tilesAreNotFilled(int[][] grid) {
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
        for (int xIndex = 0; xIndex < gridBoundary; xIndex++) {
            for (int yIndex = 0; yIndex < gridBoundary; yIndex++) {
                if (grid[xIndex][yIndex] == 0) return true;
            }
        }
        return false;
    }
	
	public static boolean sudokuIsInvalid(int[][] grid) {
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
        if (rowsAreInvalid(grid, gridBoundary)) return true;
        if (columnsAreInvalid(grid, gridBoundary)) return true;
        if (squaresAreInvalid(grid, gridBoundary)) return true;
        else return false;
    }
	
	public static boolean columnsAreInvalid(int[][] grid, int gridBoundary) {
		for (int xIndex = 0; xIndex < gridBoundary; xIndex++) {
			List<Integer> row = new ArrayList<>();
			for (int yIndex = 0; yIndex < gridBoundary; yIndex++) {
				row.add(grid[xIndex][yIndex]);
			}

			if (collectionHasRepeats(row))
				return true;
		}

		return false;
	}
	
	public static boolean rowsAreInvalid(int[][] grid, int gridBoundary) {
		for (int yIndex = 0; yIndex < gridBoundary; yIndex++) {
			List<Integer> row = new ArrayList<>();
			for (int xIndex = 0; xIndex < gridBoundary; xIndex++) {
				row.add(grid[xIndex][yIndex]);
			}

			if (collectionHasRepeats(row))
				return true;
		}

		return false;
	}

	public static boolean squaresAreInvalid(int[][] grid, int gridBoundary) {
        //top three squares
        if (rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        //middle three
        if (gridBoundary == 9 && rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;

        //bottom three
        if (rowOfSquaresIsInvalid(Rows.BOTTOM, grid)) return true;

        return false;
    }
	
	private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
		switch (value) {
		case TOP:
			if(SudokuGame.getGameType() == GameType.EASY) {
				if (squareIsInvalid(0, 0, grid))
					return true;
				if (squareIsInvalid(0, 2, grid))
					return true;
			}
			else {
				//x FIRST = 0
                if (squareIsInvalid(0, 0, grid)) return true;
                //x SECOND = 3
                if (squareIsInvalid(0, 3, grid)) return true;
                //x THIRD = 6
                if (squareIsInvalid(0, 6, grid)) return true;
			}

			// Otherwise squares appear to be valid
			return false;

		case MIDDLE:
			if (squareIsInvalid(3, 0, grid))
				return true;
			if (squareIsInvalid(3, 3, grid))
				return true;
			if (squareIsInvalid(3, 6, grid))
				return true;
			return false;

		case BOTTOM:
			if(SudokuGame.getGameType() == GameType.EASY) {
				if (squareIsInvalid(2, 0, grid))
				return true;
				if (squareIsInvalid(2, 2, grid))
					return true;
			}
			else {
				if (squareIsInvalid(6, 0, grid)) return true;
                if (squareIsInvalid(6, 3, grid)) return true;
                if (squareIsInvalid(6, 6, grid)) return true;
			}
			
			return false;

		default:
			return false;
		}
	}
	
	public static boolean squareIsInvalid(int yIndex, int xIndex, int[][] grid) {
		//TODO add a case that would make it also applicable for 3x3
		//Modified for 2x2
		int yIndexEnd = yIndex + 1;
		int xIndexEnd = xIndex + 1;

		List<Integer> square = new ArrayList<>();

		while (yIndex < yIndexEnd) {

			while (xIndex < xIndexEnd) {
				square.add(grid[xIndex][yIndex]);
				xIndex++;
			}

			// reset x to original value by subtracting by 1
			xIndex -= 1;

			yIndex++;
		}

		// if square has repeats, return true
		if (collectionHasRepeats(square))
			return true;
		return false;
	}
	
	private static boolean collectionHasRepeats(List<Integer> collection) {
		// count occurrences of integers from 1-GRID_BOUNDARY. If Collections.frequency
		// returns a value greater than 1,
		// then the square is invalid (i.e. a non-zero number has been repeated in a
		// square)
		int gridBoundary;
		if(SudokuGame.getGameType() == GameType.EASY) {
			gridBoundary = SudokuGame.GRID_BOUNDARY_EASY;
		}
		else {
			gridBoundary = SudokuGame.GRID_BOUNDARY_HARD;
		}
		
		for (int index = 1; index <= gridBoundary; index++) {
			if (Collections.frequency(collection, index) > 1)
				return true;
		}

		return false;
	}
}
