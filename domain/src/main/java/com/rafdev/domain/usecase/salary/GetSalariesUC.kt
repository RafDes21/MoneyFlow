package com.rafdev.domain.usecase.salary

import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.repository.salary.SalaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSalariesUseCase @Inject constructor(
    private val repository: SalaryRepository
) {
    operator fun invoke(): Flow<Result<List<Salary>>> =
        repository.getSalaries()
}