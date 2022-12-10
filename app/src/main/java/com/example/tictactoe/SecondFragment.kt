package com.example.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tictactoe.algorythm.Game
import com.example.tictactoe.common.GAME_MODE
import com.example.tictactoe.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    companion object {
        //const val GRID_SIZE = 3
        const val PVE_MODE = "pve"
        const val EVE_MODE = "eve"

        /**
         * @param STEP
         * false = X
         * true = O
         */
        var STEP = false
    }

    private lateinit var _game: Game

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val grid = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )

        if (arguments?.getInt("generations") != null && arguments?.getString("mode") == GAME_MODE.EVE_MODE.value) {
            binding.field.visibility = View.GONE
            setGridOnClickListeners()
            val generations = arguments?.getInt("generations")!!
            // init eve
            _game = Game(grid, generations, context)
            _game.startEve()
        } else if (arguments?.getString("mode") == GAME_MODE.PVE_MODE.value) {
            setGridOnClickListeners()
            // init pve
            _game = Game(grid, context)
            //game.StartEve()
        }

    }

    private fun setGridOnClickListeners() {
        binding.button00.setOnClickListener {
            updateButton(binding.button00, 0 ,0)
        }

        binding.button01.setOnClickListener {
            updateButton(binding.button01, 0, 1)
        }

        binding.button02.setOnClickListener {
            updateButton(binding.button02, 0, 2)
        }

        binding.button10.setOnClickListener {
            updateButton(binding.button10, 1, 0)
        }

        binding.button11.setOnClickListener {
            updateButton(binding.button11, 1, 1)
        }

        binding.button12.setOnClickListener {
            updateButton(binding.button12, 1, 2)
        }

        binding.button20.setOnClickListener {
            updateButton(binding.button20, 2, 0)
        }

        binding.button21.setOnClickListener {
            updateButton(binding.button21, 2, 1)
        }

        binding.button22.setOnClickListener {
            updateButton(binding.button22, 2, 2)
        }
    }

    private fun updateButton(button: Button, x: Int, y: Int) {

        if (button.text.isEmpty()) {
            button.text = if (STEP) "O" else "X"
            button.invalidate()
            _game.updateFieldState(x, y, if (STEP) "O" else "X")
            if (_game.checkStep()){
                // ToDo сделать всплывашку/надпись/etc
                binding.field.visibility = View.GONE
            }
            STEP = !STEP
            // Note: _isCurrent у игрока обновляется внутри checkStep. Да, все плохо
            val isBotTurn = _game.isBotTurn() && _game.gameMode == GAME_MODE.PVE_MODE
            if (isBotTurn){
                _game.botTurn()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}