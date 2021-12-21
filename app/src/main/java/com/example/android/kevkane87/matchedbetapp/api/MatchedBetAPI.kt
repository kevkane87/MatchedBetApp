package com.example.android.kevkane87.matchedbetapp.api

import com.example.android.kevkane87.matchedbetapp.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


    //sets Retrofit timeout values
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    //builds the Moshi object that Retrofit will be using
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Build Retrofit object and configure to use timeout, scalar converter and Moshi
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()

    //interface to connect to NASA API Service
    interface OddsApiService {
        //coroutine function to retrieve data from API
        @GET("v4/sports/soccer_epl/odds")
        suspend fun getOdds(
            @Query("api_key") apiKey: String,
            @Query("regions") regions: String,
            @Query("markets") markets: String): String
    }

    //A public Api object that exposes the Retrofit service
    object OddsApi {
        val retrofitService : OddsApiService by lazy { retrofit.create(OddsApiService::class.java) }
    }
