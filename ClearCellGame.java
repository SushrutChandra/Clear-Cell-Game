package model;

import java.util.Random;

// This class extends GameModel and implements the logic of the clear cell game.
// We define an empty cell as BoardCell.EMPTY.
public class ClearCellGame extends Game {
	private int score;
	private Random random;
	private int strategy;

	// constructor which defines a board with empty cells.
	public ClearCellGame(int maxRows, int maxCols, Random random,
			int strategy) {
		super(maxRows, maxCols);
		this.score = 0;
		this.random = random;
		this.strategy = strategy;
	}

	// The game is over when
	// the last board row (row with index board.length -1) is different from
	// empty row.
	public boolean isGameOver() {
		// Check if valid number of row & last row is non-empty
		if (super.getMaxRows() <= 0 || !isRowEmpty(super.getMaxRows() - 1)) {
			return true;
		} else {
			return false;
		}
	}

	// returns the users score
	public int getScore() {
		return score;
	}

	/**
	 * Advances the animation one step.
	 */
	// This method will attempt to insert a row of random BoardCell
	// objects if the last board row (row with index board.length -1)
	// corresponds to the empty row;
	// otherwise no operation will take place.
	public void nextAnimationStep() {
		if (!isGameOver()) {
			// move all existing rows down 1
			for (int row = super.getMaxRows() - 2; row >= 0; row--) {
				moveRowDown(row);
			}
			// insert random row at the top
			for (int col = 0; col < super.getMaxCols(); col++) {
				super.setBoardCell(0, col,
						BoardCell.getNonEmptyRandomBoardCell(random));
			}
		}
	}

	/**
	 * Adjust the board state according to the current board state and the
	 * selected cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public void processCell(int rowIndex, int colIndex) {
		// Validate rowIndex and colIndex
		if (rowIndex < 0 || rowIndex >= super.getMaxRows() || colIndex < 0
				|| colIndex >= super.getMaxCols()) {
			return;
		}

		// get clicked cell
		BoardCell clickedCell = super.getBoardCell(rowIndex, colIndex);
		BoardCell currentCell;

		// Do nothing if cell is EMPTY
		if (clickedCell == BoardCell.EMPTY) {
			return;
		}

		// Do nothing if game is over
		if (isGameOver()) {
			return;
		}

		// Process cells. If color matches in a direction set to empty
		// else stop processing in that direction
		
		// Process current cell
		super.setBoardCell(rowIndex, colIndex, BoardCell.EMPTY);
		score++;

		// Process cells to the left
		for (int col = colIndex - 1; col >= 0; col--) {
			currentCell = super.getBoardCell(rowIndex, col);
			if (currentCell == clickedCell) {
				super.setBoardCell(rowIndex, col, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
		}

		// Process cells to the right
		for (int col = colIndex + 1; col < super.getMaxCols(); col++) {
			currentCell = super.getBoardCell(rowIndex, col);
			if (currentCell == clickedCell) {
				super.setBoardCell(rowIndex, col, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
		}

		// Process cells above
		for (int row = rowIndex - 1; row >= 0; row--) {
			currentCell = super.getBoardCell(row, colIndex);
			if (currentCell == clickedCell) {
				super.setBoardCell(row, colIndex, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
		}

		// Process cells below
		for (int row = rowIndex + 1; row < super.getMaxRows(); row++) {
			currentCell = super.getBoardCell(row, colIndex);
			if (currentCell == clickedCell) {
				super.setBoardCell(row, colIndex, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
		}

		// Process cells diagonally above & right
		int row = rowIndex - 1;
		int col = colIndex + 1;

		while (row >= 0 && col < super.getMaxCols()) {
			currentCell = super.getBoardCell(row, col);
			if (currentCell == clickedCell) {
				super.setBoardCell(row, col, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
			row--;
			col++;
		}

		// Process cells diagonally above & left
		row = rowIndex - 1;
		col = colIndex - 1;

		while (row >= 0 && col >= 0) {
			currentCell = super.getBoardCell(row, col);
			if (currentCell == clickedCell) {
				super.setBoardCell(row, col, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
			row--;
			col--;
		}
		
		// Process cells diagonally below & right
		row = rowIndex + 1;
		col = colIndex + 1;

		while (row < super.getMaxRows() && col < super.getMaxCols()) {
			currentCell = super.getBoardCell(row, col);
			if (currentCell == clickedCell) {
				super.setBoardCell(row, col, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
			row++;
			col++;
		}

		// Process cells diagonally below & left
		row = rowIndex + 1;
		col = colIndex - 1;

		while (row < super.getMaxRows() && col >= 0) {
			currentCell = super.getBoardCell(row, col);
			if (currentCell == clickedCell) {
				super.setBoardCell(row, col, BoardCell.EMPTY);
				score++;
			} else {
				break;
			}
			row++;
			col--;
		}

		// Collapse empty rows
		collapseRows();
	}

	// isRowEmpty(): returns true if all cells in the row are BoardCell.EMPTY
	private boolean isRowEmpty(int rowIndex) {
		for (int col = 0; col < super.getMaxCols(); col++) {
			if (super.getBoardCell(rowIndex, col) != BoardCell.EMPTY) {
				return false;
			}
		}
		return true;
	}

	// moves row down one
	private void moveRowDown(int rowIndex) {
		for (int col = 0; col < super.getMaxCols(); col++) {
			super.setBoardCell(rowIndex + 1, col,
					super.getBoardCell(rowIndex, col));
		}
	}

	// collapseRow method: creates new board and collapses empty rows
	private void collapseRows() {
		// validate board size
		if (super.getMaxRows() == 0 || super.getMaxCols() == 0) {
			return;
		}

		// Create a new board
		BoardCell newBoard[][] = new BoardCell[super.getMaxRows()][super.getMaxCols()];

		// initialize newBoard to empty
		for (int row = 0; row < super.getMaxRows(); row++) {
			for (int col = 0; col < super.getMaxCols(); col++) {
				newBoard[row][col] = BoardCell.EMPTY;
			}
		}

		int newBoardRow = 0; // next empty row index of the new board

		// Copy non-empty rows from board to newBoard
		for (int row = 0; row < super.getMaxRows(); row++) {
			if (!isRowEmpty(row)) {
				// Copy row to the new board
				for (int col = 0; col < super.getMaxCols(); col++) {
					newBoard[newBoardRow][col] = super.getBoardCell(row, col);
				}
				newBoardRow++;
			}
		}
		super.board = newBoard;
	}
}
