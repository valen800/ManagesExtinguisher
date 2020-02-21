package org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase

import androidx.room.*

@Entity(tableName = "Building")
data class Building(

    @PrimaryKey (autoGenerate = true)
    var buildingId: Long = 0L,

    @ColumnInfo(name = "name_buildings")
    var nameBuildings: String = ""
)

