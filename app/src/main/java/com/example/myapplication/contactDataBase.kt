package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [contactEntity::class], version = 1)
abstract class contactDataBase: RoomDatabase() {
    abstract fun contactDao(): contactDao
}