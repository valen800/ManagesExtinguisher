package org.ieselcaminas.valentin.managesextinguisher.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import org.ieselcaminas.valentin.managesextinguisher.database.Building.Building
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor

data class BuildingWithFloors(
    @Embedded val building: Building,
    @Relation(
        parentColumn = "buildingId",
        entityColumn = "buildingFloorID"
    )
    val floorList: List<Floor>
)