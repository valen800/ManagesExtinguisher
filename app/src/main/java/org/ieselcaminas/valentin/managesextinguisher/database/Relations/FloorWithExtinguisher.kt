package org.ieselcaminas.valentin.managesextinguisher.database.Relations

import androidx.room.Embedded
import androidx.room.Relation
import org.ieselcaminas.valentin.managesextinguisher.database.Extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.Floor

data class FloorWithExtinguisher(
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "extinguisherFloorId"
    )
    val extinguisherList: List<Extinguisher>
)