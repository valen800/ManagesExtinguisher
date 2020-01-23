package org.ieselcaminas.valentin.managesextinguisher.database.Relations

import androidx.room.Embedded
import androidx.room.Relation
import org.ieselcaminas.valentin.managesextinguisher.database.Flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.Floor

data class FloorWithFlask(
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "flaskFloorId"
    )
    val flaskList: List<Flask>
)