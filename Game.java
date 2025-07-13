package model;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 * 
 */

public abstract class Game {
	protected BoardCell[][] board;
	private int maxRows, maxCols;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * 
	 * @param maxRows
	 * @param maxCols
	 */
	public Game(int maxRows, int maxCols) {
		this.maxRows = maxRows;
		this.maxCols = maxCols;
		board = new BoardCell[maxRows][maxCols];

		// set board to empty
		this.setBoardWithColor(BoardCell.EMPTY);
	}

	// returns maxRows
	public int getMaxRows() {
		return maxRows;
	}

	// returns maxCols
	public int getMaxCols() {
		return maxCols;
	}

	// sets given board position
	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		board[rowIndex][colIndex] = boardCell;
	}

	// gets board cell at given position
	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}

	/**
	 * Initializes row with the specified color.
	 * 
	 * @param rowIndex
	 * @param cell
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) {
		for (int col = 0; col < maxCols; col++) {
			setBoardCell(rowIndex, col, cell);
		}
	}

	/**
	 * Initializes column with the specified color.
	 * 
	 * @param colIndex
	 * @param cell
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		for (int row = 0; row < maxRows; row++) {
			setBoardCell(row, colIndex, cell);
		}
	}

	/**
	 * Initializes the board with the specified color.
	 * 
	 * @param cell
	 */
	public void setBoardWithColor(BoardCell cell) {
		for (int row = 0; row < maxRows; row++) {
			setRowWithColor(row, cell);
		}
	}

	public abstract boolean isGameOver();

	public abstract int getScore();

	/**
	 * Advances the animation one step.
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board state and the
	 * selected cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}