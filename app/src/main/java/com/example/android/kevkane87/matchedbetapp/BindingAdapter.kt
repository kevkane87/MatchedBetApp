package com.example.android.kevkane87.matchedbetapp.savedbets

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.kevkane87.matchedbetapp.Constants
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.R
import com.example.android.kevkane87.matchedbetapp.findbets.FindBetsAdapter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("doubleToString")
fun doubleToString(textView: TextView, number: Double) {
    val df = DecimalFormat("#.######")
    textView.text = df.format(number)
}

@BindingAdapter("doubleToCurrencyString")
fun doubleToCurrencyString(textView: TextView, number: Double) {
    val format = NumberFormat.getCurrencyInstance(Locale.UK)
    textView.text = format.format(number)
}


@BindingAdapter("calculateRating")
fun calculateRating(textView: TextView, bet: MatchedBetDataItem) {
    var rating = bet.bookiesOdds / bet.exchangeOdds * 100
    rating = Math.round(rating * 100) / 100.0
    textView.text = rating.toString() + "%"
}

@BindingAdapter("listDataSaved")
fun bindRecyclerViewSaved(recyclerView: RecyclerView, data: List<MatchedBetDataItem>?) {
    if (!data.isNullOrEmpty()){
        val adapter = recyclerView.adapter as SavedBetsAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("listDataFound")
fun bindRecyclerViewFound(recyclerView: RecyclerView, data: List<MatchedBetDataItem>?) {
    if (!data.isNullOrEmpty()){
        val adapter = recyclerView.adapter as FindBetsAdapter
        adapter.submitList(data)
    }
}

//check if url is an image and use Glide to bind image to view
@BindingAdapter("bindBookieLogo")
fun bindBookieLogo(imgView: ImageView, bet: MatchedBetDataItem) {
    var imageUrl = ""
    when (bet.bookie){
        Constants.SKY -> imageUrl = Constants.LOGO_SKY
        Constants.BETFRED -> imageUrl = Constants.LOGO_BETFRED
        Constants.MARATHON -> imageUrl = Constants.LOGO_MARATHON
        Constants.PADDY-> imageUrl = Constants.LOGO_PADDY
        Constants.CORAL -> imageUrl = Constants.LOGO_CORAL
        Constants.WH -> imageUrl = Constants.LOGO_WH
        Constants.BETFAIR -> imageUrl = Constants.LOGO_BETFAIR_SPORTSBOOK
        Constants.MATCHBOOK -> imageUrl = Constants.LOGO_MATCHBOOK
        Constants.UNIBET -> imageUrl = Constants.LOGO_UNIBET
        Constants.LIVESCORE -> imageUrl = Constants.LOGO_LIVESCORE
        Constants.VIRGIN -> imageUrl = Constants.LOGO_VIRGIN
        Constants.LADBROKES -> imageUrl = Constants.LOGO_LADBROKES
        Constants.BETVICTOR -> imageUrl = Constants.LOGO_BETVICTOR
        Constants.BOOKIE_888_SPORT -> imageUrl = Constants.LOGO_888_SPORT
        Constants.BET_CLICK -> imageUrl = Constants.LOGO_BET_CLICK
    }
    if (imageUrl!="") {
        imageUrl.let {
            val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.logo_notfound)
                        .dontAnimate()
                        .error(R.drawable.logo_notfound))
                .into(imgView)
        }
    }
}

@BindingAdapter("bindExchangeLogo")
fun bindExchangeLogo(imgView: ImageView, bet: MatchedBetDataItem){
    val imageUrl = Constants.LOGO_BETFAIR
    imageUrl.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.logo_notfound)
                    .dontAnimate()
                .error(R.drawable.logo_notfound))
            .into(imgView)
    }
}



