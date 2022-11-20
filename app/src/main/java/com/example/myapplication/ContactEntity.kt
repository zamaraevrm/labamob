package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "contacts")
class ContactEntity {
    @PrimaryKey(autoGenerate = true) var id: Long? = null
    @ColumnInfo(name = "firstname") var firstname: String = ""
    @ColumnInfo(name = "surname") var surname: String? = ""
    @ColumnInfo(name = "birthday") var birthday: Date? = Date()
    @ColumnInfo(name = "phone") var phone: String = ""
}