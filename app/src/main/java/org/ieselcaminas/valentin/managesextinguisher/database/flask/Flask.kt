package org.ieselcaminas.valentin.managesextinguisher.database.flask

import androidx.room.*

@Entity(tableName = "Flask")
data class Flask(

    @PrimaryKey (autoGenerate = true)
    var flaskId: Long,
    var flaskFloorId: Long,

    @ColumnInfo(name = "n_flask") var nFlask: String,
    @ColumnInfo(name = "manufacturer") var manufacturer: String,
    @ColumnInfo(name = "fabrication_date") var fabricationDate: Long,
    @ColumnInfo(name = "empty_weight") var emptyWeight: Int,
    @ColumnInfo(name = "total_weight") var totalWeight: Int,
    @ColumnInfo(name = "situation") var situation: Int,
    @ColumnInfo(name = "date_last_revision") var dateLastRevision: Long,
    @ColumnInfo(name = "date_next_revision") var dateNextRevision: Long
)
