package com.etalon.shashin.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "sportpit")
data class SportPit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "itemName")
    var itemName: String,

    @ColumnInfo(name = "amount")
    var amount: Double,

    @ColumnInfo(name = "priceone")
    var priceforone: Double,

    @ColumnInfo(name = "pricezakupka")
    var pricezakupka: Double,

    @ColumnInfo(name = "srokgodnosti")
    var srokgodnosti: String,

    @ColumnInfo(name = "date")
    var date: Long
) : Parcelable