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

data class Month(
    val id: Long,
    val year: Int,
    val month: Int,
    val hasSalary: Boolean
)

data class CreateExpense(
    val monthId: Long,
    val amount: Double,
    val description: String
)

data class MonthlyExpense(
    val id: Long,
    val monthId: Long,
    val amount: Double,
    val description: String,
    val date: Long
)