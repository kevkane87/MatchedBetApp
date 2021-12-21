package com.example.android.kevkane87.matchedbetapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BetDAO {

    @Query("SELECT * FROM bets where isSaved = 1")
    suspend fun getSavedBets(): List<MatchedBetDTO>

    @Query("SELECT * FROM bets where id = :betId")
    suspend fun getSavedBet(betId: String): MatchedBetDTO

    @Query("SELECT * FROM bets where isSaved = 0 ORDER BY rating DESC")
    suspend fun getFoundBets(): List<MatchedBetDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBet(bet: MatchedBetDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFoundBets(bets: List<MatchedBetDTO>)

    @Query("DELETE from bets WHERE isSaved = 0")
    suspend fun deleteFoundBets()

    @Query("DELETE FROM bets where id = :betId")
    suspend fun deleteBetById(betId: String)
}