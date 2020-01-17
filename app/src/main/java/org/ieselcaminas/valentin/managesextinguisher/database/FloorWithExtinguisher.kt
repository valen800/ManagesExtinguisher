package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.room.Embedded
import androidx.room.Relation

data class FloorWithExtinguisher(
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "extinguisherFloorId"
    )
    val extinguisherList: List<Extinguisher>
)