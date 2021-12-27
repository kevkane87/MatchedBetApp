package com.example.android.kevkane87.matchedbetapp

import android.util.Log
import com.example.android.kevkane87.matchedbetapp.Result
import com.example.android.kevkane87.matchedbetapp.api.OddsApi
import com.example.android.kevkane87.matchedbetapp.api.parseBetsJsonResult
import com.example.android.kevkane87.matchedbetapp.database.BetDatabase
import com.example.android.kevkane87.matchedbetapp.database.MatchedBetDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

//repository for accessing database and API service
class Repository(private val database: BetDatabase) {

    suspend fun saveBet(bet: MatchedBetDTO) =
            withContext(Dispatchers.IO) {
                try {
                    database.betDao.saveBet(bet)
                }
                catch (ex: Exception) {
                    Result.Error(ex.localizedMessage)
                }
            }


     suspend fun getSavedBets(): Result<List<MatchedBetDTO>> = withContext(Dispatchers.IO){
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(database.betDao.getSavedBets())
            } catch (ex: Exception) {
                Result.Error(ex.localizedMessage)
            }
        }

    }

    suspend fun getFoundBets(): Result<List<MatchedBetDTO>> = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(database.betDao.getFoundBets())
            } catch (ex: Exception) {
                Result.Error(ex.localizedMessage)
            }
        }
    }

    //connects to API service in coroutine
    suspend fun refreshFoundBets() {
        withContext(Dispatchers.IO) {
            try {
                val astResp = OddsApi.retrofitService.getOdds(
                    Constants.API_KEY,
                    Constants.REGIONS,
                    Constants.MARKETS
                )
                val betList = parseBetsJsonResult(JSONArray(astResp))
                database.betDao.deleteFoundBets()
                database.betDao.saveFoundBets(betList)
            }
            catch (ex: Exception) {
                Log.e(TAG, "Error connecting to API")
            }
        }
    }


    suspend fun deleteBet(id: String) =
        withContext(Dispatchers.IO) {
            try {
                database.betDao.deleteBetById(id)
            }
            catch (ex: Exception) {
                Log.e(TAG, "Error connecting to API")
            }
        }

}

private const val TAG = "Repository"
