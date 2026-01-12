package com.rafdev.domain.usecase.salary

import com.rafdev.domain.model.salary.UpdateSalary
import com.rafdev.domain.repository.salary.SalaryRepository
import javax.inject.Inject

class UpdateSalaryUseCase @Inject constructor(
    private val repository: SalaryRepository
) {
    suspend operator fun invoke(data: UpdateSalary): Result<Unit> =
        repository.updateSalary(data)
}