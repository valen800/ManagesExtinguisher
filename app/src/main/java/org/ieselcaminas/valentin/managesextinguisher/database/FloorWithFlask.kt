package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.room.Embedded
import androidx.room.Relation

data class FloorWithFlask(
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "flaskFloorId"
    )
    val flaskList: List<Flask>
)