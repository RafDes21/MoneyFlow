package com.rafdev.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafdev.data.entities.CreditCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCardDao {

    @Query("SELECT * FROM creditCard")
    fun getAllCreditCard(): Flow<List<CreditCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditCard(creditCardEntity: CreditCardEntity)

    @Query("DELETE FROM creditCard WHERE id= :creditCardId")
    suspend fun deleteCreditCard(creditCardId: Int)

    @Query(
        """
    UPDATE creditCard 
    SET total = :total 
    WHERE id = :cardId
"""
    )
  suspend fun updateTotal(cardId: Int, total: Double)

}