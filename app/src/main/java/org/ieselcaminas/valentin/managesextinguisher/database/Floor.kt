package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.room.*
import java.util.*
import java.time.LocalDateTime

@Entity(tableName = "Floor")
data class Floor(

    @PrimaryKey(autoGenerate = true)
    var floorId: Long = 0L,

    @Embedded var extinguisher: Extinguisher?,

    @Embedded var flask: Flask?,

    @ColumnInfo(name = "name_floor")
    var nameFloor: String = "",

    @ColumnInfo(name = "n_floor")
    var nFloor: Long = 0L,

    @ColumnInfo(name = "amount_extinguisher")
    var amountExtinguisher: Int = 0
)

