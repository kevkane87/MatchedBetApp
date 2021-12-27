package com.example.android.kevkane87.matchedbetapp.savedbets

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.Repository
import com.example.android.kevkane87.matchedbetapp.database.BetDatabase
import com.example.android.kevkane87.matchedbetapp.database.MatchedBetDTO
import com.example.android.kevkane87.matchedbetapp.Result
import kotlinx.coroutines.launch

class SavedBetsViewModel (application: Application) : ViewModel(){

    private val repository = Repository(BetDatabase.getDatabase(application))

     val betList = MutableLiveData<List<MatchedBetDataItem>>()

    //loads saved bets using coroutine
    fun loadBets() {
        viewModelScope.launch {
            when (val result = repository.getSavedBets()) {
                is Result.Success<*> -> {
                    val dataList = ArrayList<MatchedBetDataItem>()
                    dataList.addAll((result.data as List<MatchedBetDTO>).map { bet ->
                        //map the bet data from the DB to the be ready to be displayed on the UI
                        MatchedBetDataItem(
                            bet.bookiesStake!!,
                            bet.bookiesOdds!!,
                            bet.exchangeOdds!!,
                            bet.exchangeCommission!!,
                            bet.betType!!,
                            bet.bookie!!,
                            bet.exchange!!,
                            bet.betEvent!!,
                            bet.betStartTime!!,
                            bet.betOutcome!!,
                            bet.profit!!,
                            bet.isSaved!!,
                            bet.id
                        )
                    })
                    betList.value = dataList
                }
                is Result.Error ->
                    //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                Log.e(TAG,"Error getting data")
            }
        }
    }
}
private const val TAG = "SavedBetsViewModel"