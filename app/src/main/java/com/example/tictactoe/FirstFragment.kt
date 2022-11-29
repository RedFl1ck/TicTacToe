package com.example.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.SecondFragment.Companion.EVE_MODE
import com.example.tictactoe.SecondFragment.Companion.PVE_MODE
import com.example.tictactoe.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.visibility = View.INVISIBLE
        binding.textCount.visibility = View.INVISIBLE
        binding.seekBar.visibility = View.INVISIBLE

        binding.buttonRun.setOnClickListener {
            val generations = binding.seekBar.progress
            val mode = getMode(binding.toggle.checkedButtonId)
            val bundle = bundleOf("generations" to generations, "mode" to mode)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.textCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
        })

        binding.toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) {
                when (checkedId) {
                    binding.pve.id -> {
                        binding.textView.visibility = View.VISIBLE
                        binding.textCount.visibility = View.VISIBLE
                        binding.seekBar.visibility = View.VISIBLE
                    }
                    binding.eve.id -> {
                        binding.textView.visibility = View.INVISIBLE
                        binding.textCount.visibility = View.INVISIBLE
                        binding.seekBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun getMode(checkedId: Int): String {
        return when (checkedId) {
            binding.pve.id -> {
                PVE_MODE
            }
            binding.eve.id -> {
                EVE_MODE
            }
            else -> {
                ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}