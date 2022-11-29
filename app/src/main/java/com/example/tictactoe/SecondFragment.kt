package com.example.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tictactoe.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    companion object {
        const val GRID_SIZE = 3
        const val PVE_MODE = "pve"
        const val EVE_MODE = "eve"
    }

    private var _binding: FragmentSecondBinding? = null

    /**
     * @param step
     * false = X
     * true = O
     */
    private var step = false

    private val gridArray = Array(GRID_SIZE) { arrayOfNulls<Byte?>(GRID_SIZE) }

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

        if (arguments?.getInt("generations") != null && arguments?.getString("mode") == EVE_MODE) {
            binding.field.visibility = View.GONE
            val generations = arguments?.getInt("generations")!!
            // init eve
        } else if (arguments?.getString("mode") == PVE_MODE) {
            setGridOnClickListeners()
            // init pve
        }

    }

    private fun setGridOnClickListeners() {
        binding.button00.setOnClickListener {
            gridArray[0][0] = printText(binding.button00)
            step = !step
        }

        binding.button01.setOnClickListener {
            gridArray[0][1] = printText(binding.button01)
            step = !step
        }

        binding.button02.setOnClickListener {
            gridArray[0][2] = printText(binding.button02)
            step = !step
        }

        binding.button10.setOnClickListener {
            gridArray[1][0] = printText(binding.button10)
            step = !step
        }

        binding.button11.setOnClickListener {
            gridArray[1][1] = printText(binding.button11)
            step = !step
        }

        binding.button12.setOnClickListener {
            gridArray[1][2] = printText(binding.button12)
            step = !step
        }

        binding.button20.setOnClickListener {
            gridArray[2][0] = printText(binding.button20)
            step = !step
        }

        binding.button21.setOnClickListener {
            gridArray[2][1] = printText(binding.button21)
            step = !step
        }

        binding.button22.setOnClickListener {
            gridArray[2][2] = printText(binding.button22)
            step = !step
        }
    }

    private fun printText(button: Button): Byte? {
        return if (button.text.isEmpty()) {
            button.text = if (step) "O" else "X"
            if (step) 0 else 1
        } else {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}