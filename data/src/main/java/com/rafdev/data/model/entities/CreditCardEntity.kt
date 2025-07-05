package com.rafdev.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creditCard")
data class CreditCardEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var type: Int,
    var title: String,
    var number: String,
    var total: Double,
    var color: Int
)