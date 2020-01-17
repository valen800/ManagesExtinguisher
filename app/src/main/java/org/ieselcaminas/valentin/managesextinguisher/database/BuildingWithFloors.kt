package org.ieselcaminas.valentin.managesextinguisher.database

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingWithFloors(
    @Embedded val building: Building,
    @Relation(
        parentColumn = "buildingId",
        entityColumn = "buildingFloorID"
    )
    val floorList: List<Floor>
)