package com.rafdev.data.di

import com.rafdev.data.database.dao.BudgetDao
import com.rafdev.data.database.dao.ExpenseDao
import com.rafdev.data.repository.RepositoryBudgetImpl
import com.rafdev.data.repository.RepositoryExpenseImpl
import com.rafdev.domain.repository.RepositoryBudget
import com.rafdev.domain.repository.RepositoryExpense
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryBudget(budgetDao: BudgetDao): RepositoryBudget {
        return RepositoryBudgetImpl(budgetDao)
    }

    @Provides
    fun provideExpense(expenseDao: ExpenseDao): RepositoryExpense {
        return RepositoryExpenseImpl(expenseDao)
    }
}
