package com.rafdev.data.mapper.salary

import com.rafdev.data.entities.SalaryEntity
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.model.salary.UpdateSalary

fun CreateSalary.toEntity(): SalaryEntity =
    SalaryEntity(
        companyName = companyName,
        amount = amount,
        createdAt = createdAt,
        year = year,
        month = month
    )

fun UpdateSalary.toEntity(): SalaryEntity =
    SalaryEntity(
        id = id,
        companyName = companyName,
        amount = amount,
        createdAt = null,
        year = null,
        month = null
    )

fun SalaryEntity.toDomain(): Salary =
    Salary(
        id = id,
        companyName = companyName,
        amount = amount,
        createdAt = createdAt ?: 0,
        year = year ?: 0,
        month = month ?: 0
    )

