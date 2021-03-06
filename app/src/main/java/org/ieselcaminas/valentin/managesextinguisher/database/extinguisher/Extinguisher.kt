package org.ieselcaminas.valentin.managesextinguisher.database.extinguisher

import androidx.room.*

@Entity(tableName = "Extinguisher")
data class Extinguisher(

    @PrimaryKey (autoGenerate = true)
    var extinguisherId: Long = 0L,
    var extinguisherFloorId: Long = 0L,

    @ColumnInfo(name = "n_extinguisher") var nExtinguisher: String = "",
    @ColumnInfo(name = "situation") var situation: String = "",
    @ColumnInfo(name = "trademark") var trademark: String = "",
    @ColumnInfo(name = "model") var model: String = "",
    @ColumnInfo(name = "description_location") var descriptionLocation: String = "",
    @ColumnInfo(name = "weight") var weight: Int = 0,
    @ColumnInfo(name = "factory_date") var factoryDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "date_last_revision") var dateLastRevision: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "date_next_revision") var dateNextRevision: Long = System.currentTimeMillis()
)

