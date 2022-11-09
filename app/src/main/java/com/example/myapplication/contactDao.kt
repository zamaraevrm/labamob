package com.example.myapplication

import androidx.room.*

@Dao
interface contactDao {
    @get:Query("SELECT * FROM contacts")
    val allContacts: List<contactEntity>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getById(id: Long): contactEntity

    @Insert
    fun insert(todo: contactEntity): Long

    @Update
    fun update(todo: contactEntity)

    @Delete
    fun delete(todo: contactEntity)
}