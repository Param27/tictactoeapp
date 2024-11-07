package com.example.tictactoeapp

import android.widget.Button
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        // Launch the MainActivity in a test scenario
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it
        }
    }

    @Test
    fun checkWin_returnsTrue_whenPlayerWinsHorizontally() {
        // Simulate a winning board for "X" in the first row
        activity.board[0][0] = "X"
        activity.board[0][1] = "X"
        activity.board[0][2] = "X"
        activity.currentPlayer = "X"

        assertTrue("checkWin should return true for a winning row configuration", activity.checkWin())
    }

    @Test
    fun checkWin_returnsTrue_whenPlayerWinsVertically() {
        // Simulate a winning board for "O" in the first column
        activity.board[0][0] = "O"
        activity.board[1][0] = "O"
        activity.board[2][0] = "O"
        activity.currentPlayer = "O"

        assertTrue("checkWin should return true for a winning column configuration", activity.checkWin())
    }

    @Test
    fun checkWin_returnsTrue_whenPlayerWinsDiagonally() {
        // Simulate a winning board for "X" on the diagonal
        activity.board[0][0] = "X"
        activity.board[1][1] = "X"
        activity.board[2][2] = "X"
        activity.currentPlayer = "X"

        assertTrue("checkWin should return true for a winning diagonal configuration", activity.checkWin())
    }

    @Test
    fun checkWin_returnsFalse_whenNoWinExists() {
        // Simulate a board with no winner
        activity.board[0][0] = "X"
        activity.board[0][1] = "O"
        activity.board[0][2] = "X"
        activity.board[1][0] = "O"
        activity.board[1][1] = "X"
        activity.board[1][2] = "O"
        activity.board[2][0] = "O"
        activity.board[2][1] = "X"
        activity.board[2][2] = "O"

        assertFalse("checkWin should return false for a non-winning board configuration", activity.checkWin())
    }

    @Test
    fun isBoardFull_returnsTrue_whenBoardIsCompletelyFilled() {
        // Fill up the board to simulate a draw
        activity.board = arrayOf(
            arrayOf("X", "O", "X"),
            arrayOf("O", "X", "O"),
            arrayOf("O", "X", "O")
        )

        assertTrue("isBoardFull should return true for a fully filled board", activity.isBoardFull())
    }

    @Test
    fun isBoardFull_returnsFalse_whenBoardHasEmptySpaces() {
        // Set up a partially filled board
        activity.board = arrayOf(
            arrayOf("X", "O", "X"),
            arrayOf("O", "X", null),
            arrayOf("O", "X", "O")
        )

        assertFalse("isBoardFull should return false for partially filled board", activity.isBoardFull())
    }

    @Test
    fun onCellClick_doesNotAlterCell_whenCellIsAlreadyPlayed() {
        // Set up a cell with "X" and simulate clicking on it again
        val button = Button(activity)
        activity.board[0][0] = "X"
        button.text = "X"

        activity.onCellClick(button, 0)
        // Ensure the cell's value did not change
        assertEquals("Cell should retain 'X' as it was already played", "X", activity.board[0][0])
    }

    @Test
    fun onCellClick_setsPlayerMark_whenCellIsEmpty() {
        // Simulate clicking on an empty cell
        val button = Button(activity)
        activity.currentPlayer = "X"

        activity.onCellClick(button, 0)
        // Check that the cell has been set to "X"
        assertEquals("Expected cell to be set to 'X' for current player", "X", activity.board[0][0])
    }

    @Test
    fun onCellClick_switchesPlayerAfterValidMove() {
        val button = Button(activity)
        activity.currentPlayer = "X"

        // Simulate a valid move
        activity.onCellClick(button, 0)

        // Check that the player switches after a valid move
        assertEquals("Expected current player to switch to 'O' after move", "O", activity.currentPlayer)
    }

    @Test
    fun resetGame_clearsBoardAndResetsPlayerToX() {
        // Set some game state
        activity.board[0][0] = "X"
        activity.board[1][1] = "O"
        activity.currentPlayer = "O"

        // Call resetGame and check board and game state
        activity.resetGame()

        // Ensure all cells are reset
        for (row in activity.board) {
            for (cell in row) {
                assertNull("Expected all cells to be cleared after resetGame", cell)
            }
        }

        // Ensure the first player is "X" again
        assertEquals("Expected currentPlayer to be reset to 'X' after resetGame", "X", activity.currentPlayer)
    }
}
