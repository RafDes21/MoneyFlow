package com.rafdev.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salary")
data class SalaryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val companyName: String,
    val amount: Double,
)