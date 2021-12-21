package com.example.android.kevkane87.matchedbetapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "bets")
data class MatchedBetDTO(

    @ColumnInfo(name = "bookiesStake") var bookiesStake: Double?,
    @ColumnInfo(name = "bookiesOdds") var bookiesOdds: Double?,
    @ColumnInfo(name = "exchangeOdds") var exchangeOdds: Double?,
    @ColumnInfo(name = "exchangeCommission") var exchangeCommission: Double?,
    @ColumnInfo(name = "betType") var betType: String?,
    @ColumnInfo(name = "bookie") var bookie: String?,
    @ColumnInfo(name = "exchange") var exchange: String?,
    @ColumnInfo(name = "betEvent") var betEvent: String?,
    @ColumnInfo(name = "betStartTime") var betStartTime: String?,
    @ColumnInfo(name = "betOutcome") var betOutcome: String?,
    @ColumnInfo(name = "profit") var profit: Double?,
    @ColumnInfo(name = "rating") var rating: Double?,
    @ColumnInfo(name = "isSaved") var isSaved: Boolean?,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()

)




