package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.room.*
import java.util.*
import java.time.LocalDateTime

@Entity(tableName = "Extinguisher_table")
data class Extinguisher(

    @PrimaryKey var extinguisherId: Long = 0L,
    var extinguisherFloorId: Long,

    @ColumnInfo(name = "n_extinguisher") var nExtinguisher: String = "",
    @ColumnInfo(name = "n_flask") var nSmallBottle: String = "",
    @ColumnInfo(name = "situation") var situation: String = "",
    @ColumnInfo(name = "powder") var powder: String = "",
    @ColumnInfo(name = "trademark") var trademark: String = "",
    @ColumnInfo(name = "model") var model: String = "",
    @ColumnInfo(name = "description_location") var descriptionLocation: String = "",
    @ColumnInfo(name = "weight") var weight: Int = 0,
    @ColumnInfo(name = "factory_date") var factoryDate: Long = 0L,
    @ColumnInfo(name = "date_last_revision") var dateLastRevision: Long = 0L,
    @ColumnInfo(name = "date_next_revision") var dateNextRevision: Long = 0L
)

