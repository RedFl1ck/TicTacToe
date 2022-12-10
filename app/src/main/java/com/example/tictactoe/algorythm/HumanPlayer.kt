package com.example.tictactoe.algorythm

/**
 * Human player
 */
public class HumanPlayer(isCurrent: Boolean) : Player(isCurrent) {
    override fun makeStep(
        _fieldState: Array<Array<String?>>,
        _fieldWeights: HashMap<String, Double>
    ): Pair<Int, Int> {
        TODO("Not yet implemented")
    }

    override fun loose(_fieldState: Array<Array<String?>>, _fieldWeights: HashMap<String, Double>, switchSigns: Boolean) {

    }

    override fun win(_fieldState: Array<Array<String?>>, _fieldWeights: HashMap<String, Double>, switchSigns: Boolean) {

    }

    override fun draw(_fieldState: Array<Array<String?>>, _fieldWeights: HashMap<String, Double>, switchSigns: Boolean) {

    }
}