package com.rafdev.domain.repository

import com.rafdev.domain.model.Expense
import kotlinx.coroutines.flow.Flow
interface RepositoryExpense {

    fun getExpense() : Flow<List<Expense>>

}