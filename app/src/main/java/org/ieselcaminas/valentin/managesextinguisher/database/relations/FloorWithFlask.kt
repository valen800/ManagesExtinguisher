package org.ieselcaminas.valentin.managesextinguisher.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor

data class FloorWithFlask(
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "flaskFloorId"
    )
    val flaskList: List<Flask>
)