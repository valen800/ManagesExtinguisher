package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.room.*
import java.util.*
import java.time.LocalDateTime

@Entity(tableName = "Extinguisher")
data class Floor(

    @PrimaryKey(autoGenerate = true)
    var floorId: Long = 0L,

    @Embedded var building: Building?,

    @ColumnInfo(name = "name_floor")
    var nameFloor: String = "",

    @ColumnInfo(name = "n_floor")
    var nFloor: Long = 0L,

    @ColumnInfo(name = "amount_extinguisher")
    var amountExtinguisher: Int = 0
)

