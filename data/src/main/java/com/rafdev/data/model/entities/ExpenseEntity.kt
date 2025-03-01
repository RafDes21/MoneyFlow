package com.rafdev.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = null,
    var amount: Double? = null,
    var type: String? = null,
    var description: String? = null,
    var image:String? = null,
    var color: String? = null,
    var date: String? = null,
    var category: String? = null,
    var recurring: Boolean? = null,
    var period: String? = null,
    var paymentMethod: String? = null,
    var notes: String? = null,
    var isPaid: Boolean? = null,
)
