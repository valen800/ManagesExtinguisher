package org.ieselcaminas.valentin.managesextinguisher.database.Relations

import androidx.room.Embedded
import androidx.room.Relation
import org.ieselcaminas.valentin.managesextinguisher.database.Building.Building
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.Floor

data class BuildingWithFloors(
    @Embedded val building: Building,
    @Relation(
        parentColumn = "buildingId",
        entityColumn = "buildingFloorID"
    )
    val floorList: List<Floor>
)