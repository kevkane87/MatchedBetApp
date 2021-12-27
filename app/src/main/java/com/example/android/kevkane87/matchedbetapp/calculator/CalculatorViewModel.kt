package com.example.android.kevkane87.matchedbetapp.calculator

import android.app.Application
import android.provider.Settings.Global.getString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.R
import com.example.android.kevkane87.matchedbetapp.Repository
import com.example.android.kevkane87.matchedbetapp.database.BetDatabase.Companion.getDatabase
import com.example.android.kevkane87.matchedbetapp.database.MatchedBetDTO
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : ViewModel(){

    private val repository = Repository(getDatabase(application))

    //Live data properties
    private val _backBetStake = MutableLiveData<Double>()
    val backBetStake: MutableLiveData<Double>
    get()= _backBetStake

    private val _backBetOdds = MutableLiveData<Double>()
    val backBetOdds: MutableLiveData<Double>
        get()= _backBetOdds

    private val _layBetOdds = MutableLiveData<Double>()
    val layBetOdds: MutableLiveData<Double>
        get()= _layBetOdds

    private val _exchangeCommission = MutableLiveData<Double>()
    val exchangeCommission: MutableLiveData<Double>
        get()= _exchangeCommission

    private val _layStake = MutableLiveData<Double>()
    val layStake: MutableLiveData<Double>
        get()= _layStake

    private val _layLiability = MutableLiveData<Double>()
    val layLiability: MutableLiveData<Double>
        get()= _layLiability

    private val _profit = MutableLiveData<Double>()
    val profit: MutableLiveData<Double>
        get()= _profit

    private val _bookieName = MutableLiveData<String>()
    val bookieName: MutableLiveData<String>
        get()= _bookieName

    private val _exchangeName = MutableLiveData<String>()
    val exchangeName: MutableLiveData<String>
        get()= _exchangeName

    private val _event = MutableLiveData<String>()
    val event: MutableLiveData<String>
        get()= _event

    private val _eventTime = MutableLiveData<String>()
    val eventTime: MutableLiveData<String>
        get()= _eventTime

    private val _outcome = MutableLiveData<String>()
    val outcome: MutableLiveData<String>
        get()= _outcome

    private val _betType = MutableLiveData<String>()
    val betType: MutableLiveData<String>
        get()= _betType

    private val _radio_checked = MutableLiveData<Int>()
    val radio_checked: MutableLiveData<Int>
    get()=_radio_checked

    private val _id = MutableLiveData<String>()
    val id: MutableLiveData<String>
        get()=_id

    private var _bet = MutableLiveData<MatchedBetDataItem>()
    val bet: MutableLiveData<MatchedBetDataItem>
        get() = _bet

    init {
       setRadioButton()
    }

    fun clear(){
        _backBetStake.value = 0.0
        _backBetOdds.value = 0.0
        _layBetOdds.value = 0.0
        _exchangeCommission.value = 0.0
        _layStake.value = 0.0
        _layLiability.value = 0.0
        _profit.value = 0.0
        _bookieName.value = ""
        _exchangeName.value = ""
        _event.value = ""
        _outcome.value = ""
        _eventTime.value = ""
        _betType.value = ""
        _id.value = ""

    }

    //sets radio button for correct bet type
    fun setRadioButton(){
        when(_betType.value.toString()){
            "Qualifier" ->
                _radio_checked.postValue(R.id.qualifier)
            "" ->
                _radio_checked.postValue(R.id.qualifier)
            "SNR" ->
                _radio_checked.postValue(R.id.snr)
            "SR" ->
                _radio_checked.postValue(R.id.sr)
        }

    }

    //sets live data to bet details passed from fragment (bundles)
    fun setBetDetails(bet: MatchedBetDataItem){
        _backBetOdds.value = bet.bookiesOdds
        _layBetOdds.value = bet.exchangeOdds
        _exchangeCommission.value = bet.exchangeCommission
        _bookieName.value = bet.bookie
        _exchangeName.value = bet.exchange
        _event.value = bet.betEvent
        _outcome.value = bet.betOutcome
        _eventTime.value = bet.betStartTime
        _betType.value = bet.betType
        setRadioButton()
        calculate()
    }

    //funcion provides matched bet calculations
    fun calculate() {

        when (_betType.value) {

            "Qualifier" -> {

                if (_backBetOdds.value != null && _backBetStake.value != null && _layBetOdds.value != null) {
                    if (_backBetOdds.value != 0.0 && _backBetStake.value != 0.0 && _layBetOdds.value != 0.0) {

                        _layStake.value =
                            (backBetStake.value!! * _backBetOdds.value!!) / (_layBetOdds.value!! - _exchangeCommission.value!! / 100)
                        _layLiability.value =
                            _layBetOdds.value!! * _layStake.value!! - _layStake.value!!
                        _profit.value =
                            (backBetStake.value!! * _backBetOdds.value!! - backBetStake.value!!) - _layLiability.value!!

                        _layStake.value = Math.round(_layStake.value!! * 100) / 100.0
                        _layLiability.value = Math.round(_layLiability.value!! * 100) / 100.0
                        _profit.value = Math.round(_profit.value!! * 100) / 100.0


                    }
                }
            }

            "SNR" -> {
                _layStake.value =
                    (backBetStake.value!! * _backBetOdds.value!! - backBetStake.value!!) / (_layBetOdds.value!! - _exchangeCommission.value!! / 100)
                _layLiability.value = _layBetOdds.value!! * _layStake.value!! - _layStake.value!!
                _profit.value =
                    (backBetStake.value!! * _backBetOdds.value!! - backBetStake.value!!) - _layLiability.value!!
                _layStake.value = Math.round(_layStake.value!! * 100) / 100.0
                _layLiability.value = Math.round(_layLiability.value!! * 100) / 100.0
                _profit.value = Math.round(_profit.value!! * 100) / 100.0
            }

            "SR" -> {
                _layStake.value =
                    (backBetStake.value!! * _backBetOdds.value!!) / (_layBetOdds.value!! - _exchangeCommission.value!! / 100)
                _layLiability.value = _layBetOdds.value!! * _layStake.value!! - _layStake.value!!
                _profit.value =
                    (backBetStake.value!! * _backBetOdds.value!!) - _layLiability.value!!
                _layStake.value = Math.round(_layStake.value!! * 100) / 100.0
                _layLiability.value = Math.round(_layLiability.value!! * 100) / 100.0
                _profit.value = Math.round(_profit.value!! * 100) / 100.0
            }
        }
    }

    //sets bet info details from fragment
    fun setBetInfoDetails(event: String, outc: String, date: String, bookie: String, exchange: String){
        _event.value = event
        _outcome.value = outc
        _eventTime.value = date
        _bookieName.value = bookie
        _exchangeName.value = exchange
    }


    //sets bet type from radio group
    fun setBetType(){
        when (_radio_checked.value){
            R.id.qualifier -> _betType.value = "Qualifier"
            R.id.snr -> _betType.value = "SNR"
            R.id.sr -> _betType.value = "SR"
        }
    }

    //returns Matched bet data item
    fun getMatchedBetDataItem(): MatchedBetDataItem
    {
        saveBet()
         return MatchedBetDataItem(
            _backBetStake.value!!,
            _backBetOdds.value!!,
            _layBetOdds.value!!,
            _exchangeCommission.value!!,
            _betType.value!!,
            _bookieName.value!!,
            _exchangeName.value!!,
            _event.value!!,
            _eventTime.value!!,
            _outcome.value!!,
            _profit.value!!,
             true,
             _id.value!!
        )
    }

    //delete bet from database
    fun deleteBet(id: String){
        viewModelScope.launch {
            repository.deleteBet((id))
        }
    }


    //saves bet to database with coroutine
    fun saveBet() {
        val rating = _backBetOdds.value!! / _layBetOdds.value!!
        setBetType()
        viewModelScope.launch {
            repository.saveBet(
                MatchedBetDTO(
                    _backBetStake.value,
                    _backBetOdds.value,
                    _layBetOdds.value,
                    _exchangeCommission.value,
                    _betType.value,
                    _bookieName.value,
                    _exchangeName.value,
                    _event.value,
                    _eventTime.value,
                    _outcome.value,
                    _profit.value,
                    rating,
                    true
                ))
        }
    }


}