# Clear Cell Game 🎮

A grid-based, interactive puzzle game where players aim to clear as many colored cells as possible. Built in Java using object-oriented principles, the Clear Cell Game emphasizes 2D array manipulation and board-based logic.

> ✅ **Goal**: Click on cells to clear contiguous cells of the same color. Rows collapse as they're emptied, and your score increases with every cleared cell.

---

## 💡 Features

- Grid-based cell matching and clearing
- Color-coded board cells using enums
- Fully object-oriented architecture (encapsulation, inheritance, abstraction)
- Collapsing mechanic that removes empty rows and shifts board upward
- Integrated with a Swing-based GUI (provided)

---

## 🧠 Core Concepts & Technical Breakdown

### 🧱 Data Structures

- **2D Array**  
  The entire game board is represented as a `BoardCell[][]` array.
  
- **Enum**  
  `BoardCell` defines possible cell states (`RED`, `BLUE`, `GREEN`, `YELLOW`, `EMPTY`) along with associated color and label properties.

### 🔄 Game Logic

- **Clearing Cells**  
  When a player clicks a cell, the game clears all **contiguous same-color cells** in 8 directions (up/down/left/right and diagonals).

- **Score Tracking**  
  Each cleared cell adds one point to the total score.

- **Animation Step**  
  The board shifts downward, inserting a new random row at the top each tick (if game is not over).

- **Row Collapse**  
  After a clearing move, completely empty rows are removed, and non-empty rows shift upward to fill space.

### 🧩 Object-Oriented Design

- **Abstract Base Class – `Game`**  
  Defines a general-purpose board game with essential methods:
  - `processCell(row, col)`
  - `nextAnimationStep()`
  - `isGameOver()`, etc.

- **Subclass – `ClearCellGame`**
  Implements rules specific to this game:
  - Handles cell-matching logic
  - Generates random rows using injected `Random`
  - Collapses cleared rows

- **Encapsulation**
  - Game board is a protected member, modified via `getBoardCell`, `setBoardCell`, etc.
  - Private helper methods like `moveRowDown()` and `collapseRows()` abstract internal operations.

- **GUI Integration (`GameGUI`)**
  Sourced separately, it uses Java Swing to display the board and handle user input.

