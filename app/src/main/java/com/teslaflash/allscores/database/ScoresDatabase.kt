package com.teslaflash.allscores.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ScoresPlayer::class, ScoresTransaction::class, ScoresMatch::class, ScoresRoster::class),
    version = 1, exportSchema = true)

open abstract class ScoresDatabase:RoomDatabase() {

    abstract fun dao():ScoresDao
    
    companion object{

        @Volatile
        private var INSTANCE: ScoresDatabase? = null
        
        fun getDatabase(context: Context): ScoresDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoresDatabase::class.java,
                    "ScoresDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
        
    }
    
}