package com.rafdev.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "salary")
data class SalaryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val companyName: String,
    val amount: Double,
)

@Entity(
    tableName = "month_period",
    indices = [
        Index(value = ["year", "month"], unique = true)
    ]
)
data class MonthPeriodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val year: Int,
    val month: Int
)