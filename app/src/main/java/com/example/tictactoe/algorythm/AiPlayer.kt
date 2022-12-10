package com.example.tictactoe.algorythm

import com.example.tictactoe.SecondFragment.Companion.STEP
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.random.Random

/**
 * AI player
 */
class AiPlayer(isCurrent: Boolean) : Player(isCurrent) {

    override fun makeStep(
        _fieldState: Array<Array<String?>>,
        _fieldWeights: HashMap<String, Double>
    ): Pair<Int, Int>{
        val freeCells:ArrayList<Pair<Int, Int>> = ArrayList()
        for (i in 0..2){
            for (j in 0..2) {
                if (_fieldState[i][j] == null){
                    freeCells.add(Pair(i, j))
                }
            }
        }

        var maxWeight = 0.0

        val isGreedy = Random.nextDouble() < 0.1

        var coords: Pair<Int, Int>? = null

        for (freeCell in freeCells){
            val newState: Array<Array<String?>> = generateNewState(_fieldState)

            newState[freeCell.first][freeCell.second] = if (STEP) "O" else "X"
            val key = makeKey(newState, STEP)
            val weight: Double = if (_fieldWeights.containsKey(key)){
                _fieldWeights[key] as Double
            } else{
                0.5
            }
            if (!isGreedy){
                if (weight > maxWeight){
                    maxWeight = weight
                    coords = freeCell
                }
            }
            else{
                if (weight <= 0.5){
                    coords = freeCell
                }
            }
        }

        if (coords == null){
            coords = freeCells.random()
        }

        return coords
    }

    override fun loose(_fieldState: Array<Array<String?>>,
                       _fieldWeights: HashMap<String, Double>, switchSigns: Boolean) {
        correct(_fieldWeights, makeKey(_fieldState, switchSigns), 0.0)
    }

    override fun win(_fieldState: Array<Array<String?>>,
                     _fieldWeights: HashMap<String, Double>, switchSigns: Boolean) {
        correct(_fieldWeights, makeKey(_fieldState, switchSigns), 1.0)
    }

    override fun draw(_fieldState: Array<Array<String?>>,
                      _fieldWeights: HashMap<String, Double>, switchSigns: Boolean) {
        correct(_fieldWeights, makeKey(_fieldState, switchSigns), 0.5)
    }

    private fun generateNewState(_fieldState: Array<Array<String?>>): Array<Array<String?>>{
        val newState: Array<Array<String?>> = arrayOf(
            arrayOfNulls(3),
            arrayOfNulls(3),
            arrayOfNulls(3)
        )
        for (i in 0..2){
            for (j in 0..2){
                newState[i][j] = _fieldState[i][j]
            }
        }
        return newState
    }
}