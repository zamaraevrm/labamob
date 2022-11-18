package com.example.myapplication

import androidx.room.*

@Dao
interface ContactDao {
    @get:Query("SELECT * FROM contacts")
    val getAllContacts: List<ContactEntity>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getById(id: Long): ContactEntity

    @Insert
    fun insert(contact: ContactEntity): Long

    @Update
    fun update(contact: ContactEntity)

    @Delete
    fun delete(contact: ContactEntity)
}