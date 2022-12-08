package com.example.tictactoe.algorythm

import android.R.attr.data
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Button
import com.example.tictactoe.SecondFragment.Companion.STEP
import com.example.tictactoe.common.GAME_MODE
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader


/**
 * The whole process of game
 */
class Game {
    private val _delimeter = ":"

    private val _weightsFileName: String = "weights.txt"

    private lateinit var _context: Context

    private val _grid: Array<Array<Button>>

    private lateinit var _fieldState: Array<Array<String?>>

    private var _fieldWeights: HashMap<String, Double>

    private var _generations: Int = 1

    private var _players: List<Player>

    private var _mode: GAME_MODE

    private var _done: Boolean = false

    fun Boolean.toInt() = if (this) 1 else 0

    constructor(grid: Array<Array<Button>>, generations: Int, context: Context?) {
        _generations = generations
        _players = listOf(AiPlayer(true), AiPlayer(false))
        _mode = GAME_MODE.EVE_MODE
        _grid = grid
        _fieldState = arrayOf(
            arrayOfNulls(3),
            arrayOfNulls(3),
            arrayOfNulls(3)
        )

        if (context != null){
            _context = context
            _fieldWeights = readOrCreateWeightsFile(context)
        }
        else {
            throw IllegalArgumentException("Context is empty!")
        }
    }

    constructor(grid: Array<Array<Button>>, context: Context?) {
        _players = listOf(HumanPlayer(true), AiPlayer(false))
        _mode = GAME_MODE.PVE_MODE
        _grid = grid
        if (context != null){
            _context = context
            _fieldWeights = readOrCreateWeightsFile(context)
        }
        else {
            throw IllegalArgumentException("Context is empty!")
        }
    }

    private fun readOrCreateWeightsFile(context: Context): HashMap<String, Double> {
        val file = File(context.filesDir, _weightsFileName)
        return if (file.exists() && file.isFile) {
            val result: HashMap<String, Double> = HashMap()
            FileInputStream(file).bufferedReader().use {
                var line: String?
                while (it.readLine().also { line = it } != null) {
                    val pair = makePair(line)
                    if (pair != null)
                        result[pair.first] = pair.second
                }
            }
            result
        } else {
            file.createNewFile()
            HashMap()
        }
    }

    private fun makePair(line: String?): Pair<String, Double>? {
        val pair = line?.split(_delimeter)?.take(2)
        return if (pair?.get(0) != null)
            Pair(pair[0], pair[1].toDouble())
        else null
    }

    fun StartEve() {
        for (i in 0.._generations) {
            while (true) {
                val player = _players[STEP.toInt()]
                val coords = player.makeStep(_fieldState, _fieldWeights)
                _fieldState[coords.first][coords.second] = if (STEP) "O" else "X"
                _grid[coords.first][coords.second].performClick()
                if (_done){
                    _done = false
                    resetGame()
                    break
                }
            }
        }
        writeWeightsToFile()
    }

    private fun writeWeightsToFile() {
        val file = File(_context.filesDir, _weightsFileName)
        FileOutputStream(file).use {
            for (situation in _fieldWeights)
                file.appendText("${situation.key}:${situation.value}${System.lineSeparator()}")
        }
    }

    private fun resetGame() {
        _players = listOf(AiPlayer(true), AiPlayer(false))
        _fieldState = arrayOf(
            arrayOfNulls(3),
            arrayOfNulls(3),
            arrayOfNulls(3)
        )
        _grid.forEach { it.forEach { button -> button.text = "" }}
    }

    private fun isDraw(): Boolean {
        if (_fieldState.all { states -> states.all {state -> state != null}}){
            return true;
        }
        return false
    }

    private fun isWin(player: Player): Boolean {
        val curPlayer = if (STEP) "O" else "X"
        var isWin = false

        // горизонтали
        for (row in _fieldState){
            if (row[0] == curPlayer && row[1] == curPlayer && row[2] == curPlayer){
                isWin = true;
            }
        }

        // вертикали
        for (i in 0..2){
            if (_fieldState[0][i] == curPlayer && _fieldState[1][i] == curPlayer && _fieldState[2][i] == curPlayer){
                isWin = true;
            }
        }

        // диагонали
        if (_fieldState[2][0] == curPlayer && _fieldState[1][1] == curPlayer && _fieldState[0][2] == curPlayer) {
            isWin = true;
        }
        if (_fieldState[0][0] == curPlayer && _fieldState[1][1] == curPlayer && _fieldState[2][2] == curPlayer) {
            isWin = true;
        }

        return isWin;
    }

    fun checkStep(step: Boolean, x: Int, y: Int): Boolean {
        val player = _players[STEP.toInt()]
        val playerO = _players[(!STEP).toInt()]
        player.switchIsCurrent()
        playerO.switchIsCurrent()
        return if (isWin(player)) {
            player.win(_fieldState, _fieldWeights, STEP)
            playerO.loose(_fieldState, _fieldWeights, !STEP)
            _done = true
            true
        } else if (isDraw()) {
            player.draw(_fieldState, _fieldWeights, STEP)
            _done = true
            true
        } else {
            false
        }
    }
}