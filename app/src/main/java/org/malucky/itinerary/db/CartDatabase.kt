package org.malucky.itinerary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CartLocation::class], version = 3, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract val cartLocationDatabaseDAO : CartLocationDAO

    companion object {

        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CartDatabase::class.java,
                        "location_history_database"
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