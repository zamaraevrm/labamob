package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [contactEntity::class], version = 1)
abstract class contactDatabase: RoomDatabase() {
    abstract fun contactDao(): contactDao

    companion object {

        @Volatile
        private var INSTANCE: contactDatabase? = null

        fun getDatabase(context: Context): contactDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): contactDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                contactDatabase::class.java,
                "contactdb"
            )
                .build()
        }
    }
}