package com.rafdev.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class BudgetEntity(

    @PrimaryKey var id:Int = 0,
    var totalBudget : Double = 0.0

)