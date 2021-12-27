package com.example.android.kevkane87.matchedbetapp.api

import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import com.example.android.kevkane87.matchedbetapp.Constants
import com.example.android.kevkane87.matchedbetapp.database.MatchedBetDTO
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

//Converter functions for parsing JSON object to database matched bet models
fun parseBetsJsonResult(jsonResultArray: JSONArray): ArrayList<MatchedBetDTO> {

    val betList = ArrayList<MatchedBetDTO>()

    for (i in 0 until jsonResultArray.length()) {

        val gameJson = jsonResultArray.getJSONObject(i)
        val homeTeam = gameJson.getString("home_team")
        val awayTeam = gameJson.getString("away_team")
        val event = "${homeTeam} v ${awayTeam}"
        val eventTime = gameJson.getString("commence_time")
        val eventTimeFormatted: String

        eventTimeFormatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val dateInput = formatterInput.parse(eventTime)
            val formatterOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            formatterOutput.format(dateInput)
        } else{
            eventTime
        }

        var homeLayOdds = 0.0
        var awayLayOdds = 0.0
        var drawLayOdds = 0.0


        val bookieArray = gameJson.getJSONArray("bookmakers")

        for (j in 0 until bookieArray.length()) {
            val bookieJson = bookieArray.getJSONObject(j)
            val bookie = bookieJson.getString("title")
            if (bookie == "Betfair") {
                val marketsArray = bookieJson.getJSONArray("markets")

                for(k in 0 until marketsArray.length()) {
                    val marketJson = marketsArray.getJSONObject(k)

                    if (marketJson.getString("key") == "h2h_lay" && bookie == "Betfair") {
                        val outcomeArray = marketJson.getJSONArray("outcomes")
                        for (l in 0 until outcomeArray.length()) {
                            val outcomeJson = outcomeArray.getJSONObject(l)
                            val outcome = outcomeJson.getString("name")
                            val odds = outcomeJson.getDouble("price")
                            when (outcome) {
                                homeTeam -> {
                                    homeLayOdds = odds
                                }
                                awayTeam -> {
                                    awayLayOdds = odds
                                }
                                "Draw" -> {
                                    drawLayOdds = odds
                                }
                            }
                        }
                    }
                }
            }
        }


        for (j in 0 until bookieArray.length()) {

            val bookieJson = bookieArray.getJSONObject(j)
            val bookie = bookieJson.getString("title")

            val marketsArray = bookieJson.getJSONArray("markets")

            for (k in 0 until marketsArray.length()) {
                val marketJson = marketsArray.getJSONObject(k)

                val outcomeArray = marketJson.getJSONArray("outcomes")

                if (marketJson.getString("key") == "h2h") {

                    for (l in 0 until outcomeArray.length()) {

                        val outcomeJson = outcomeArray.getJSONObject(l)

                        val outcome = outcomeJson.getString("name")
                        val odds = outcomeJson.getDouble("price")

                        var layOdds = 0.0

                        when (outcome) {
                            homeTeam -> {
                                layOdds = homeLayOdds
                            }
                            awayTeam -> {
                                layOdds = awayLayOdds
                            }
                            "Draw" -> {
                                layOdds = drawLayOdds
                            }
                        }

                        var rating = odds/layOdds * 100
                        rating = Math.round(rating * 100) / 100.0

                        if (layOdds != 0.0 && rating >= Constants.MIN_RATING ) {
                            val bet = MatchedBetDTO(
                                0.0,
                                odds,
                                layOdds,
                                0.0,
                                "Qualifier",
                                bookie,
                                """Betfair""",
                                event,
                                eventTimeFormatted,
                                outcome,
                                0.0,
                                rating,
                                false
                            )
                            betList.add(bet)
                        }
                    }
                }
            }
        }
    }
    return betList
}



