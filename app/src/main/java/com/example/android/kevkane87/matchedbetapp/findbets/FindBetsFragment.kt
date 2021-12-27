package com.example.android.kevkane87.matchedbetapp.findbets

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.kevkane87.matchedbetapp.Constants
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.R
import com.example.android.kevkane87.matchedbetapp.databinding.FragmentFindBetsBinding
import com.example.android.kevkane87.matchedbetapp.databinding.FragmentSavedBetsBinding
import com.example.android.kevkane87.matchedbetapp.savedbets.SavedBetsAdapter
import com.example.android.kevkane87.matchedbetapp.savedbets.SavedBetsViewModel
import com.example.android.kevkane87.matchedbetapp.savedbets.SavedBetsViewModelFactory

class FindBetsFragment : Fragment() {

    //create view model
    private val viewModel: FindBetsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, FindBetsViewModelFactory(activity.application))
            .get(FindBetsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFindBetsBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.findBetsViewModel = viewModel

        viewModel.refreshFoundbets()

        //set the recycler view adapter
        binding.findBetsRecycler.adapter = FindBetsAdapter(FindBetsAdapter.OnClickListener {
            openBetInCalculator(it)
        })



        setHasOptionsMenu(true)

        return binding.root


    }

    //opens bet in calculator by passing matched bet data item in bundle
    private fun openBetInCalculator(bet: MatchedBetDataItem) {

        var bundle = Bundle()
        bundle.putSerializable(Constants.REMINDER_ID, bet)
        findNavController().navigate(R.id.action_findBetsFragment_to_calculatorFragment, bundle)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.calculator -> findNavController().navigate(R.id.action_findBetsFragment_to_calculatorFragment)
            R.id.saved_bets -> findNavController().navigate(R.id.action_findBetsFragment_to_savedBetsFragment)
            R.id.help -> findNavController().navigate(R.id.action_findBetsFragment_to_helpFragment)
        }
        return true
    }
}
