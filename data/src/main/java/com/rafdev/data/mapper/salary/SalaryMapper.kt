package com.rafdev.data.mapper.salary

import com.rafdev.data.entities.MonthPeriodEntity
import com.rafdev.data.entities.SalaryEntity
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Month
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.model.salary.UpdateSalary

fun CreateSalary.toEntity(): SalaryEntity =
    SalaryEntity(
        companyName = companyName,
        amount = amount,
    )

fun UpdateSalary.toEntity(): SalaryEntity =
    SalaryEntity(
        id = id,
        companyName = companyName,
        amount = amount,
    )

fun SalaryEntity.toDomain(): Salary =
    Salary(
        id = id,
        companyName = companyName,
        amount = amount
    )

fun MonthPeriodEntity.toDomain(hasSalary: Boolean) = Month(
    id = id,
    year = year,
    month = month,
    hasSalary = hasSalary
)