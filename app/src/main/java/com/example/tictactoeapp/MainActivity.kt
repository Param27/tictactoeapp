package com.example.tictactoeapp

// MainActivity.kt

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var statusTextView: TextView
    private lateinit var resetButton: Button

    // Game state variables
    var board = Array(3) { arrayOfNulls<String>(3) }
    var currentPlayer = "X"
    private var gameActive = true

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)

        resetButton.setOnClickListener { resetGame() }
        initializeBoard()
    }

    private fun initializeBoard() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.setOnClickListener { onCellClick(button, i) }
        }
        statusTextView.text = "$currentPlayer's Turn"
    }

    fun onCellClick(button: Button, index: Int) {
        if (!gameActive) return

        val row = index / 3
        val col = index % 3

        // Check if the cell is already played
        if (board[row][col] != null) return

        // Place the current player's mark
        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (checkWin()) {
            gameActive = false
            statusTextView.text = "$currentPlayer Wins!"
        } else if (isBoardFull()) {
            gameActive = false
            statusTextView.text = "It's a Draw!"
        } else {
            // Switch players
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusTextView.text = "$currentPlayer's Turn"
        }
    }

    fun checkWin(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.contains(null)) return false
        }
        return true
    }

    fun resetGame() {
        board = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer = "X"
        gameActive = true
        initializeBoard()
    }
}
