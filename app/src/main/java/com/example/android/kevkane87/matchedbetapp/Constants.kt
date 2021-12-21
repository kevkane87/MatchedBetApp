package com.example.android.kevkane87.matchedbetapp

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.the-odds-api.com"



    const val API_KEY = ""
    const val REGIONS = "uk"
    const val MARKETS = "h2h"

    const val REMINDER_ID = "reminderID"


    const val MIN_RATING = 95

    const val LOGO_SKY = "https://upload.wikimedia.org/wikipedia/en/thumb/9/98/Sky_Bet.png/220px-Sky_Bet.png"
    const val LOGO_BETFRED = "https://upload.wikimedia.org/wikipedia/commons/0/01/New-betfred-logo.png"
    const val LOGO_MARATHON = "https://upload.wikimedia.org/wikipedia/en/8/8c/Marathonbet_logo_-_2019.png"
    const val LOGO_PADDY = "https://en.m.wikipedia.org/wiki/File:Paddy_Power_logo.png"
    const val LOGO_CORAL = "https://upload.wikimedia.org/wikipedia/en/9/92/Coral_logo.jpg"
    const val LOGO_WH = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/William_Hill_logo.png/220px-William_Hill_logo.png"
    //const val LOGO_BETFAIR = "https://odds-server.com/logos/Betfair.png"
    const val LOGO_BETFAIR = "https://logodownload.org/wp-content/uploads/2020/11/betfair-logo.png"
    const val LOGO_MATCHBOOK = "https://s19151.pcdn.co/intel/wp-content/uploads/sites/2/2017/07/matchbook_1.png"
    const val LOGO_UNIBET = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Unibet-Logo-white.jpg/250px-Unibet-Logo-white.jpg"
    const val LOGO_LIVESCORE = "https://www.thesun.co.uk/wp-content/uploads/2021/12/JW-LIVESCORE-BET-logo.jpg"
    const val LOGO_VIRGIN= "https://cdn.checkd.media/images/hsdaqe0cti-lg.jpg"
    const val LOGO_LADBROKES= "https://www.timeform.com/racing/content/libraryImages/affiliate/ladbrokes.png"
    const val LOGO_BETVICTOR= "https://upload.wikimedia.org/wikipedia/commons/b/b1/BetVictor_Logotype.jpg"
    const val LOGO_888_SPORT="https://upload.wikimedia.org/wikipedia/en/thumb/e/e2/888sport_logo.svg/220px-888sport_logo.svg.png"
    const val LOGO_BET_CLICK="https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Logo_Betclic_2019.svg/220px-Logo_Betclic_2019.svg.png"

    /*const val LOGO_SKY = "https://odds-server.com/logos/Sky%20Bet.png"
    const val LOGO_BETFRED = "https://odds-server.com/logos/Betfred.png"
    const val LOGO_MARATHON = "https://odds-server.com/logos/Marathonbet.png"
    const val LOGO_PADDY = "https://odds-server.com/logos/Paddy%20Power.png"
    const val LOGO_CORAL = "https://odds-server.com/logos/Coral.png"
    const val LOGO_WH = "https://odds-server.com/logos/William%20Hill.png"
    //const val LOGO_BETFAIR = "https://odds-server.com/logos/Betfair.png"
    const val LOGO_BETFAIR = "https://logodownload.org/wp-content/uploads/2020/11/betfair-logo.png"
    const val LOGO_MATCHBOOK = "https://odds-server.com/logos/Matchbook.png"
    const val LOGO_UNIBET = "https://odds-server.com/logos/Unibet.png"
    const val LOGO_LIVESCORE = "https://odds-server.com/logos/LivescoreBet.png"
    const val LOGO_VIRGIN= "https://odds-server.com/logos/VirginBet.png"
    const val LOGO_LADBROKES= "https://odds-server.com/logos/Ladbrokes.png"
    const val LOGO_BETVICTOR= "https://odds-server.com/logos/Bet%20Victor.png"
    const val LOGO_888_SPORT="https://odds-server.com/logos/888%20Sport.png"
    const val LOGO_BET_CLICK="https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Logo_Betclic_2019.svg/220px-Logo_Betclic_2019.svg.png"
*/


    const val SKY = "Sky Bet"
    const val BETFRED = "Betfred"
    const val MARATHON = "Marathon Bet"
    const val PADDY = "Paddy Power"
    const val CORAL = "Coral"
    const val WH = "William Hill"
    const val BETFAIR = "Betfair"
    const val MATCHBOOK = "Matchbook"
    const val UNIBET = "Unibet"
    const val LIVESCORE = "LiveScore Bet"
    const val VIRGIN= "Virgin Bet"
    const val LADBROKES= "Ladbrokes"
    const val BETVICTOR= "Bet Victor"
    const val BOOKIE_888_SPORT="888sport"
    const val BET_CLICK="Betclick"


    fun getDate(): String {
        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatDate.format(date)
    }
}