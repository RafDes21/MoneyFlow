package com.rafdev.data.di

import com.rafdev.data.dao.CreditCardDao
import com.rafdev.data.dao.ExpenseDao
import com.rafdev.data.dao.event.EventDao
import com.rafdev.data.dao.event.EventExpenseDao
import com.rafdev.data.repository.RepositoryCreditCardImpl
import com.rafdev.data.repository.RepositoryExpenseImpl
import com.rafdev.data.repository.event.EventRepositoryImpl
import com.rafdev.domain.repository.RepositoryCreditCard
import com.rafdev.domain.repository.RepositoryExpense
import com.rafdev.domain.repository.event.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideExpense(
        expenseDao: ExpenseDao,
        creditCardDao: CreditCardDao
    ): RepositoryExpense {
        return RepositoryExpenseImpl(expenseDao, creditCardDao)
    }

    @Provides
    fun provideCreditCard(
        creditCardDao: CreditCardDao,
        expenseDao: ExpenseDao
    ): RepositoryCreditCard {
        return RepositoryCreditCardImpl(creditCardDao, expenseDao)
    }

    @Provides
    fun provideEvent(
        evenDao: EventDao,
        eventExpenseDao: EventExpenseDao
    ): EventRepository {
        return EventRepositoryImpl(evenDao, eventExpenseDao)
    }
}
