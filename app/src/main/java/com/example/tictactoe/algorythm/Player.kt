package com.example.tictactoe.algorythm

/**
 * Abstract class of player
 */
public abstract class Player (isCurrent: Boolean){

    protected var _isCurrent : Boolean = isCurrent
    /**
     * Make a step
     */
    public abstract fun makeStep(
        _fieldState: Array<Array<String?>>,
        _fieldWeights: HashMap<String, Double>
    ): Pair<Int, Int>;

    public abstract fun loose(_fieldState: Array<Array<String?>>,
                       _fieldWeights: HashMap<String, Double>, switchSigns: Boolean)
    public abstract fun win(_fieldState: Array<Array<String?>>,
                     _fieldWeights: HashMap<String, Double>, switchSigns: Boolean)
    public abstract fun draw(_fieldState: Array<Array<String?>>,
                      _fieldWeights: HashMap<String, Double>, switchSigns: Boolean)

    fun switchIsCurrent(){
        _isCurrent = !_isCurrent
    }

    fun getIsCurrent(): Boolean{
        return _isCurrent;
    }

    protected fun makeKey(fieldState: Array<Array<String?>>, switchSigns: Boolean): String{
        val fieldStr = (fieldState[0].joinToString(separator = "") { getEmptyCellSign(it)}
                + fieldState[1].joinToString(separator = "") {  getEmptyCellSign(it) }
                + fieldState[2].joinToString(separator = "") {  getEmptyCellSign(it) });
        return if (switchSigns)
            fieldStr.replace("X", "*")
            .replace("O", "X")
            .replace("*", "O")
        else
            fieldStr;
    }

    protected fun correct(fieldWeights: HashMap<String, Double>, key: String, reward: Double) {
        val oldReward: Double = if (fieldWeights.containsKey(key)) {
            fieldWeights[key] as Double
        } else 0.5
        val newReward = oldReward + 0.1 * (reward - oldReward)
        fieldWeights[key] = newReward
    }

    fun getEmptyCellSign(string: String?) :CharSequence{
        return string ?: " "
    }
}