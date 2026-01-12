package com.rafdev.domain.usecase.salary

import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.repository.salary.SalaryRepository
import javax.inject.Inject

class AddSalaryUseCase @Inject constructor(
    private val repository: SalaryRepository
) {
    suspend operator fun invoke(data: CreateSalary): Result<Unit> =
        repository.addSalary(data)
}
