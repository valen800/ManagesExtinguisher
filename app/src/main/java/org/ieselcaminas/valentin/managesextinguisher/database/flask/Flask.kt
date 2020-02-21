package org.ieselcaminas.valentin.managesextinguisher.database.flask

import androidx.room.*

@Entity(tableName = "Flask")
data class Flask(

    @PrimaryKey (autoGenerate = true)
    var flaskId: Long = 0L,
    var flaskFloorId: Long = 0L,

    @ColumnInfo(name = "n_flask") var nFlask: String = "",
    @ColumnInfo(name = "situation") var situation: String = "",
    @ColumnInfo(name = "trademark") var trademark: String = "",
    @ColumnInfo(name = "model") var model: String = "",
    @ColumnInfo(name = "description_location") var descriptionLocation: String = "",
    @ColumnInfo(name = "empty_weight") var emptyWeight: Int = 0,
    @ColumnInfo(name = "content_weight") var contentWeight: Int = 0,
    @ColumnInfo(name = "factory_date") var factoryDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "date_last_revision") var dateLastRevision: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "date_next_revision") var dateNextRevision: Long = System.currentTimeMillis()
)
