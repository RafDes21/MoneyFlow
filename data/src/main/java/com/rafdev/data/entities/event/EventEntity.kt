package com.rafdev.data.entities.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val budget: Double,
    val totalSpent: Double = 0.0,
    val createdAt: String

)