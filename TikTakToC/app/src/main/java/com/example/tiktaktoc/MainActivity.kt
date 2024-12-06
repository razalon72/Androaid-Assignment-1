package com.example.tiktaktoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var statusText: TextView
    private lateinit var playAgainButton: Button
    private var currentPlayer = "X"
    private var gameBoard = Array(9) { "" }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        playAgainButton = findViewById(R.id.playAgainButton)

        buttons = arrayOf(
                findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
                findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
                findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onCellClicked(index)
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(index: Int) {
        if (gameBoard[index].isEmpty() && gameActive) {
            gameBoard[index] = currentPlayer
            buttons[index].text = currentPlayer

            if (checkWin()) {
                statusText.text = "Player $currentPlayer Wins!"
                gameActive = false
                playAgainButton.visibility = View.VISIBLE
            } else if (checkDraw()) {
                statusText.text = "It's a Draw!"
                gameActive = false
                playAgainButton.visibility = View.VISIBLE
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Player $currentPlayer's Turn"
            }
        }
    }

    private fun checkWin(): Boolean {
        val winningPositions = arrayOf(
                arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),  // Rows
                arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),  // Columns
                arrayOf(0, 4, 8), arrayOf(2, 4, 6)                      // Diagonals
        )

        for (positions in winningPositions) {
            if (gameBoard[positions[0]] == currentPlayer &&
                    gameBoard[positions[1]] == currentPlayer &&
                    gameBoard[positions[2]] == currentPlayer) {
                return true
            }
        }
        return false
    }

    private fun checkDraw(): Boolean {
        return gameBoard.none { it.isEmpty() }
    }

    private fun resetGame() {
        gameBoard = Array(9) { "" }
        currentPlayer = "X"
        gameActive = true
        statusText.text = "Player X's Turn"
        playAgainButton.visibility = View.GONE

        buttons.forEach { button ->
            button.text = ""
        }
    }
}