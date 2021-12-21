package com.example.android.kevkane87.matchedbetapp.findbets

import android.app.Application
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
import java.io.IOException

class FindBetsViewModel (application: Application) : ViewModel(){

    private val repository = Repository(BetDatabase.getDatabase(application))

     val betList = MutableLiveData<List<MatchedBetDataItem>>()

    fun loadBets() {
       // showLoading.value = true
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            //showLoading.postValue(false)
            //val list = repository.getBets()
            when (val result = repository.getFoundBets()) {
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
                            bet.id!!
                        )
                    })
                    betList.value = dataList
                    Log.d(TAG, (betList.value as ArrayList<MatchedBetDataItem>).size.toString())
                }
                is Result.Error ->
                    //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                Log.e(TAG,"Error getting data")
            }

            //check if no data has to be shown
            //invalidateShowNoData()
        }
    }


    fun refreshFoundbets(){
        viewModelScope.launch {
            try {
                repository.refreshFoundBets()
                loadBets()
            } catch (networkError: IOException) {
                Log.e(TAG,"Error getting data API")
                loadBets()
            }
        }
    }

    fun deleteFoundBets(){
        viewModelScope.launch {
            try {
                repository.deleteFoundBets()
            } catch (networkError: IOException) {
                Log.e(TAG, "Error")
            }
        }
    }





    fun displayBetDetails(bet: MatchedBetDataItem) {

    }


}
private const val TAG = "FindBetsViewModel"