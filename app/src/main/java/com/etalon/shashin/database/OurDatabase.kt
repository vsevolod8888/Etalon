package com.etalon.shashin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [SportPit::class], version = 4, exportSchema = false)
abstract class OurDatabase : RoomDatabase() {
    abstract fun sportpitDao(): SportPitDao
    companion object {
        var instance: OurDatabase? = null
        fun getInstance(context: Context): OurDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    OurDatabase::class.java, "songs.db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}