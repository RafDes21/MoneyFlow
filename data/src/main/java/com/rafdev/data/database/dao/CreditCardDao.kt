package com.rafdev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafdev.data.model.entities.CreditCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCardDao {

    @Query("SELECT * FROM creditCard")
    fun getAllCreditCard(): Flow<List<CreditCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditCard(creditCardEntity: CreditCardEntity)

    @Query("DELETE FROM creditCard WHERE id= :creditCardId")
    suspend fun deleteCreditCard(creditCardId: Int)

}