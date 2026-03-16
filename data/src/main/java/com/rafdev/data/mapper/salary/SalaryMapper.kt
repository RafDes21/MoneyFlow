package com.rafdev.data.mapper.salary

import com.rafdev.data.entities.SalaryEntity
import com.rafdev.domain.model.salary.CreateSalary
import com.rafdev.domain.model.salary.Salary
import com.rafdev.domain.model.salary.UpdateSalary

fun CreateSalary.toEntity(): SalaryEntity =
    SalaryEntity(
        id = id,
        companyName = companyName,
        amount = amount,
        date = date,
        isHidden = isHidden
    )

fun UpdateSalary.toEntity(): SalaryEntity =
    SalaryEntity(
        id = id,
        companyName = companyName,
        amount = amount,
        date = date
    )

fun SalaryEntity.toDomain(): Salary =
    Salary(
        id = id,
        companyName = companyName,
        amount = amount,
        date = date.orEmpty(),
        isHidden = isHidden
    )

