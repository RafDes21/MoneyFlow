package com.rafdev.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String? = null,
    val amount: Double? = null,
    val type: String? = null,
    val description: String? = null,
    val color: String? = null,
    val date: String? = null,
    val category: String? = null,
    val recurring: Boolean? = null,
    val period: String? = null,
    val paymentMethod: String? = null,
    val notes: String? = null,
    val isPaid: Boolean? = null,
)
