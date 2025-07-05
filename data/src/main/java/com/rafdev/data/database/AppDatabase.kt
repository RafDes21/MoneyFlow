package com.rafdev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafdev.data.database.dao.BudgetDao
import com.rafdev.data.database.dao.CreditCardDao
import com.rafdev.data.database.dao.ExpenseDao
import com.rafdev.data.model.entities.BudgetEntity
import com.rafdev.data.model.entities.CreditCardEntity
import com.rafdev.data.model.entities.ExpenseEntity

@Database(
    entities = [BudgetEntity::class, ExpenseEntity::class, CreditCardEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun creditCardDao(): CreditCardDao


}