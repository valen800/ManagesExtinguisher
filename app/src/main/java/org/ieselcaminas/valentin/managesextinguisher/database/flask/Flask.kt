package org.ieselcaminas.valentin.managesextinguisher.database.flask

import androidx.room.*

@Entity(tableName = "Flask_table")
data class Flask(

    @PrimaryKey(autoGenerate = true) var flaskId: Long = 0L,
    var flaskFloorId: Long,

    @ColumnInfo(name = "n_flask") var nFlask: String = "",
    @ColumnInfo(name = "manufacturer") var manufacturer: String = "",
    @ColumnInfo(name = "fabrication_date") var fabricationDate: Long = 0L,
    @ColumnInfo(name = "empty_weight") var emptyWeight: Int = 0,
    @ColumnInfo(name = "total_weight") var totalWeight: Int = 0,
    @ColumnInfo(name = "situation") var situation: Int = 0,
    @ColumnInfo(name = "date_last_revision") var dateLastRevision: Long = 0L,
    @ColumnInfo(name = "date_next_revision") var dateNextRevision: Long = 0L
)