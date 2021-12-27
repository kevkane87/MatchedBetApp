package com.example.android.kevkane87.matchedbetapp.help

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.kevkane87.matchedbetapp.R
import com.example.android.kevkane87.matchedbetapp.databinding.FragmentHelpBinding

class HelpFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHelpBinding>(
            inflater,
            R.layout.fragment_help, container, false
        )

        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.calculator -> findNavController().navigate(R.id.action_helpFragment_to_calculatorFragment)
            R.id.saved_bets -> findNavController().navigate(R.id.action_helpFragment_to_savedBetsFragment)
            R.id.find_bets -> findNavController().navigate(R.id.action_helpFragment_to_findBetsFragment)
        }
        return true
    }

}