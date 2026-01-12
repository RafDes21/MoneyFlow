package com.rafdev.domain.model.salary

data class Salary(
    val id : Int,
    val companyName: String,
    val amount: Double
)

data class CreateSalary(
    val companyName: String,
    val amount: Double
)

data class UpdateSalary(
    val id: Int,
    val companyName: String,
    val amount: Double
)