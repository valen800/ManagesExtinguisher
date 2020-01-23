package org.ieselcaminas.valentin.managesextinguisher.database.Building

import androidx.room.*
import java.util.*
import java.time.LocalDateTime

@Entity(tableName = "Building_table")
data class Building(

    @PrimaryKey(autoGenerate = true) var buildingId: Long = 0L,
    @ColumnInfo(name = "name_buildings") var nameBuildings: String = "",
    @ColumnInfo(name = "amount_floor") var amountFloor: Int = 0
)
