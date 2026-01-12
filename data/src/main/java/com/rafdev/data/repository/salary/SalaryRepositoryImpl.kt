package com.rafdev.data.repository.salary

import com.rafdev.data.dao.salary.SalaryDao
import com.rafdev.data.mapper.salary.toDomain
import com.rafdev.data.mapper.salary.toEntity
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.model.salary.UpdateSalary
import com.rafdev.domain.repository.salary.SalaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SalaryRepositoryImpl @Inject constructor(
    private val dao: SalaryDao
) : SalaryRepository {

    override suspend fun addSalary(data: CreateSalary): Result<Unit> {
        return runCatching {
            dao.insert(data.toEntity())
        }
    }

    override suspend fun updateSalary(data: UpdateSalary): Result<Unit> {
        return runCatching {
            dao.insert(data.toEntity())
        }
    }

    override fun getSalaries(): Flow<Result<List<Salary>>> {
        return dao.getAll()
            .map { salaryEntities ->
                Result.success(
                    salaryEntities.map { it.toDomain() }
                )
            }
            .catch { exception ->
                emit(Result.failure(exception))
            }
    }
}