package com.rafdev.domain.repository.salary

import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.model.salary.UpdateSalary
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {

    suspend fun addSalary(data: CreateSalary): Result<Unit>
    suspend fun updateSalary(data: UpdateSalary): Result<Unit>
    fun getSalaries(): Flow<Result<List<Salary>>>

}