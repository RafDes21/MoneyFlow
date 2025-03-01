package com.rafdev.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(
    @PrimaryKey val id:Int = 1,
    val totalBudget : Double = 0.0
)