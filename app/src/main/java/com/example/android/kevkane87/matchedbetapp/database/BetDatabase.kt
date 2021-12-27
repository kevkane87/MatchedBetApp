package com.example.android.kevkane87.matchedbetapp.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MatchedBetDTO::class], version = 1, exportSchema = false)
abstract class BetDatabase: RoomDatabase() {

    abstract val betDao: BetDAO

    companion object {

        @Volatile
        private var INSTANCE: BetDatabase? = null

        fun getDatabase(context: Context): BetDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BetDatabase::class.java,
                        "bets"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}