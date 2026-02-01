package com.rafdev.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "salary",
    indices = [
        Index(value = ["companyName"])
    ]
)
data class SalaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val companyName: String,
    val amount: Double,
    val date: String?
)