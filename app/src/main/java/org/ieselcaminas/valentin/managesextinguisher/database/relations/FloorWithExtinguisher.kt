package org.ieselcaminas.valentin.managesextinguisher.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor

data class FloorWithExtinguisher(
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "extinguisherFloorId"
    )
    val extinguisherList: List<Extinguisher>
)