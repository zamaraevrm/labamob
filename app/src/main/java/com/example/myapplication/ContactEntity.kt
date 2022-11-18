package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "contacts")
data class ContactEntity (
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "first_name") val first_name: String,
    @ColumnInfo(name = "second_name") var second_name: String? ,
    @ColumnInfo(name = "birthday") var birthday: Date?,
    @ColumnInfo(name = "phone") var phone: String
)