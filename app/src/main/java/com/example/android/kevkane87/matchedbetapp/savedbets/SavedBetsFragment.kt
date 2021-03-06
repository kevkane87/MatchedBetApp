package com.example.android.kevkane87.matchedbetapp.savedbets

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.kevkane87.matchedbetapp.Constants
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.R
import com.example.android.kevkane87.matchedbetapp.databinding.FragmentSavedBetsBinding
import java.text.SimpleDateFormat
import java.util.*

class SavedBetsFragment : Fragment(){

    //create view model
    private val viewModel: SavedBetsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, SavedBetsViewModelFactory(activity.application))
            .get(SavedBetsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentSavedBetsBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.savedBetsViewModel = viewModel

        viewModel.loadBets()

        //set the recycler view adapter
        binding.savedBetsRecycler.adapter = SavedBetsAdapter(SavedBetsAdapter.OnClickListener {
            openBetInCalculator(it) })


        setHasOptionsMenu(true)

        return binding.root

    }

    fun openBetInCalculator(bet:MatchedBetDataItem){

        var bundle = Bundle()
        bundle.putSerializable(Constants.REMINDER_ID, bet)
        findNavController().navigate(R.id.action_savedBetsFragment_to_calculatorFragment,bundle)

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.calculator -> findNavController().navigate(R.id.action_savedBetsFragment_to_calculatorFragment)
            R.id.find_bets -> findNavController().navigate(R.id.action_savedBetsFragment_to_findBetsFragment)
            R.id.help -> findNavController().navigate(R.id.action_savedBetsFragment_to_helpFragment)

        }
        return true
    }

}

private const val TAG = "SavedBetsFragment"

