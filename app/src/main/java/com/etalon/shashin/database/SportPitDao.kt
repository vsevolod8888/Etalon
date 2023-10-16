package com.etalon.shashin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface SportPitDao {
    @Query("SELECT *FROM sportpit WHERE id = :id")
    fun getSportPitById(id:  Int): SportPit?



    @Query("SELECT * FROM sportpit WHERE category =:category")
    fun getSportPitListByCategoryLiveData(category: String): Flow<List<SportPit>>


    @Insert//(onConflict = OnConflictStrategy.)
    fun insertSportPit(sportPit: SportPit)

    @Query("DELETE FROM sportpit WHERE itemName = :name")
    fun deleteSportpitByName(name: String)


    @Query("UPDATE sportpit SET amount = :amount WHERE itemName =:itemName")
    fun updateAmountByItemName(amount: Double?, itemName:String)

    @Query("UPDATE sportpit SET priceone = :priceforone WHERE itemName =:itemName")
    fun updatePriceByItemName(priceforone: Double?,itemName:String)



}