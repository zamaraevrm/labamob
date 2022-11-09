package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "contacts")
class contactEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var first_name: String = ""
    var second_name: String = ""
    var birthday: Date? = Date()
    var phone: String = ""
}