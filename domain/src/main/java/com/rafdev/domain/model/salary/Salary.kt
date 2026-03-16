package com.rafdev.domain.model.salary

data class Salary(
    val id: Int,
    val companyName: String,
    val amount: Double,
    val date: String,
    val isHidden : Boolean
)

data class CreateSalary(
    val id: Int,
    val companyName: String,
    val amount: Double,
    val date: String,
    val isHidden: Boolean = false
)

data class UpdateSalary(
    val id: Int,
    val companyName: String,
    val amount: Double,
    val date: String
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