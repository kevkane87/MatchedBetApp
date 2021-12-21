package com.example.android.kevkane87.matchedbetapp

import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*


data class MatchedBetDataItem(

    var bookiesStake: Double,
    var bookiesOdds: Double,
    var exchangeOdds: Double,
    var exchangeCommission: Double,
    var betType: String,
    var bookie: String,
    var exchange: String,
    var betEvent: String,
    var betStartTime: String,
    var betOutcome: String,
    var profit: Double,
    var isSaved: Boolean,
    @PrimaryKey
    val id: String

): Serializable