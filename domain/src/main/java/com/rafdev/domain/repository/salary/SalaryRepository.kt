package com.rafdev.domain.repository.salary

import com.rafdev.domain.model.salary.CreateExpense
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Month
import com.rafdev.domain.model.salary.MonthlyExpense
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.model.salary.UpdateSalary
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {

    suspend fun addSalary(data: CreateSalary): Result<Unit>
    suspend fun updateSalary(data: UpdateSalary): Result<Unit>
    fun getSalaries(): Flow<Result<List<Salary>>>

    fun getMonths(): Flow<Result<List<Month>>>

    suspend fun addExpense(data: CreateExpense): Result<Unit>
    fun getExpenses(monthId: Long): Flow<Result<List<MonthlyExpense>>>

    suspend fun getAvailableBalance(monthId: Long): Result<Double>

}