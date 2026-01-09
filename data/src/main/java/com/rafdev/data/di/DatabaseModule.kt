package com.rafdev.data.di

import android.content.Context
import androidx.room.Room
import com.rafdev.data.database.AppDatabase
import com.rafdev.data.database.MIGRATION_1_2
import com.rafdev.data.dao.BudgetDao
import com.rafdev.data.dao.CreditCardDao
import com.rafdev.data.dao.ExpenseDao
import com.rafdev.data.database.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "money_flow_database"
        ).addMigrations(
            MIGRATION_1_2, MIGRATION_2_3
        ).build()
    }


    @Singleton
    @Provides
    fun provideBudgetDao(db: AppDatabase): BudgetDao {
        return db.budgetDao()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(db: AppDatabase): ExpenseDao {
        return db.expenseDao()
    }

    @Singleton
    @Provides
    fun provideCreditCard(db: AppDatabase): CreditCardDao {
        return db.creditCardDao()
    }

}